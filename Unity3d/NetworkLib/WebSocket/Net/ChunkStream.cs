#region License
/*
 * ChunkStream.cs
 *
 * This code is derived from ChunkStream.cs (System.Net) of Mono
 * (http://www.mono-project.com).
 *
 * The MIT License
 *
 * Copyright (c) 2003 Ximian, Inc (http://www.ximian.com)
 * Copyright (c) 2012-2015 sta.blockhead
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
#endregion

#region Authors
/*
 * Authors:
 * - Gonzalo Paniagua Javier <gonzalo@ximian.com>
 */
#endregion

using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Net;
using System.Text;

namespace NetworkLib.WebSocketSharp.Net
{
  internal class ChunkStream
  {
    #region Private Fields

    private int                 _chunkRead;
    private int                 _chunkSize;
    private List<Chunk>         _chunks;
    private bool                _gotIt;
    private WebHeaderCollection _headers;
    private StringBuilder       _saved;
    private bool                _sawCr;
    private InputChunkState     _state;
    private int                 _trailerState;

    #endregion

    #region Public Constructors

    public ChunkStream (WebHeaderCollection headers)
    {
      _headers = headers;
      _chunkSize = -1;
      _chunks = new List<Chunk> ();
      _saved = new StringBuilder ();
    }

    public ChunkStream (byte[] buffer, int offset, int count, WebHeaderCollection headers)
      : this (headers)
    {
      Write (buffer, offset, count);
    }

    #endregion

    #region Internal Properties

    internal WebHeaderCollection Headers {
      get {
        return _headers;
      }
    }

    #endregion

    #region Public Properties

    public int ChunkLeft {
      get {
        return _chunkSize - _chunkRead;
      }
    }

    public bool WantMore {
      get {
        return _chunkRead != _chunkSize || _chunkSize != 0 || _state != InputChunkState.None;
      }
    }

    #endregion

    #region Private Methods

    private int readFromChunks (byte[] buffer, int offset, int count)
    {
      var cnt = _chunks.Count;
      var nread = 0;
      for (var i = 0; i < cnt; i++) {
        var chunk = _chunks[i];
        if (chunk == null)
          continue;

        if (chunk.ReadLeft == 0) {
          _chunks[i] = null;
          continue;
        }

        nread += chunk.Read (buffer, offset + nread, count - nread);
        if (nread == count)
          break;
      }

      return nread;
    }

    private static string removeChunkExtension (string value)
    {
      var idx = value.IndexOf (';');
      return idx > -1 ? value.Substring (0, idx) : value;
    }

    private InputChunkState seekCrLf (byte[] buffer, ref int offset, int length)
    {
      if (!_sawCr) {
        if (buffer[offset++] != 13)
          throwProtocolViolation ("CR is expected.");

        _sawCr = true;
        if (offset == length)
          return InputChunkState.BodyFinished;
      }

      if (buffer[offset++] != 10)
        throwProtocolViolation ("LF is expected.");

      return InputChunkState.None;
    }

    private InputChunkState setChunkSize (byte[] buffer, ref int offset, int length)
    {
      byte b = 0;
      while (offset < length) {
        b = buffer[offset++];
        if (_sawCr) {
          if (b != 10)
            throwProtocolViolation ("LF is expected.");

          break;
        }

        if (b == 13) {
          _sawCr = true;
          continue;
        }

        if (b == 10)
          throwProtocolViolation ("LF is unexpected.");

        if (b == 32) // SP
          _gotIt = true;

        if (!_gotIt)
          _saved.Append (b);

        if (_saved.Length > 20)
          throwProtocolViolation ("The chunk size is too long.");
      }

      if (!_sawCr || b != 10)
        return InputChunkState.None;

      _chunkRead = 0;
      try {
        _chunkSize = Int32.Parse (
          removeChunkExtension (_saved.ToString ()), NumberStyles.HexNumber);
      }
      catch {
        throwProtocolViolation ("The chunk size cannot be parsed.");
      }

      if (_chunkSize == 0) {
        _trailerState = 2;
        return InputChunkState.Trailer;
      }

      return InputChunkState.Body;
    }

    private InputChunkState setHeaders (byte[] buffer, ref int offset, int length)
    {
      // 0\r\n\r\n
      if (_trailerState == 2 && buffer[offset] == 13 && _saved.Length == 0) {
        offset++;
        if (offset < length && buffer[offset] == 10) {
          offset++;
          return InputChunkState.None;
        }

        offset--;
      }

      while (offset < length && _trailerState < 4) {
        var b = buffer[offset++];
        _saved.Append (b);
        if (_saved.Length > 4196)
          throwProtocolViolation ("The trailer is too long.");

        if (_trailerState == 1 || _trailerState == 3) {
          if (b != 10)
            throwProtocolViolation ("LF is expected.");

          _trailerState++;
          continue;
        }

        if (b == 13) {
          _trailerState++;
          continue;
        }

        if (b == 10)
          throwProtocolViolation ("LF is unexpected.");

        _trailerState = 0;
      }

      if (_trailerState < 4)
        return InputChunkState.Trailer;

      _saved.Length -= 2;
      var reader = new StringReader (_saved.ToString ());

      string line;
      while ((line = reader.ReadLine ()) != null && line.Length > 0)
        _headers.Add (line);

      return InputChunkState.None;
    }

    private static void throwProtocolViolation (string message)
    {
      throw new WebException (message, null, WebExceptionStatus.ServerProtocolViolation, null);
    }

    private void write (byte[] buffer, ref int offset, int length)
    {
      if (_state == InputChunkState.None) {
        _state = setChunkSize (buffer, ref offset, length);
        if (_state == InputChunkState.None)
          return;

        _saved.Length = 0;
        _sawCr = false;
        _gotIt = false;
      }

      if (_state == InputChunkState.Body && offset < length) {
        _state = writeBody (buffer, ref offset, length);
        if (_state == InputChunkState.Body)
          return;
      }

      if (_state == InputChunkState.BodyFinished && offset < length) {
        _state = seekCrLf (buffer, ref offset, length);
        if (_state == InputChunkState.BodyFinished)
          return;

        _sawCr = false;
      }

      if (_state == InputChunkState.Trailer && offset < length) {
        _state = setHeaders (buffer, ref offset, length);
        if (_state == InputChunkState.Trailer)
          return;

        _saved.Length = 0;
      }

      if (offset < length)
        write (buffer, ref offset, length);
    }

    private InputChunkState writeBody (byte[] buffer, ref int offset, int length)
    {
      if (_chunkSize == 0)
        return InputChunkState.BodyFinished;

      var cnt = length - offset;
      var left = _chunkSize - _chunkRead;
      if (cnt > left)
        cnt = left;

      var body = new byte[cnt];
      Buffer.BlockCopy (buffer, offset, body, 0, cnt);
      _chunks.Add (new Chunk (body));

      offset += cnt;
      _chunkRead += cnt;

      return _chunkRead == _chunkSize ? InputChunkState.BodyFinished : InputChunkState.Body;
    }

    #endregion

    #region Public Methods

    public int Read (byte[] buffer, int offset, int count)
    {
      return readFromChunks (buffer, offset, count);
    }

    public void ResetBuffer ()
    {
      _chunkSize = -1;
      _chunkRead = 0;
      _chunks.Clear ();
    }

    public void Write (byte[] buffer, int offset, int count)
    {
      if (count <= 0)
        return;

      write (buffer, ref offset, offset + count);
    }

    public void WriteAndReadBack (byte[] buffer, int offset, int count, ref int read)
    {
      Write (buffer, offset, read);
      read = readFromChunks (buffer, offset, count);
    }

    #endregion
  }
}
