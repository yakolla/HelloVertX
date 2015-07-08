/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;

namespace idl
{

  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class Header : TBase
  {
    private Protocol _key;
    private sbyte _ok;

    /// <summary>
    /// 
    /// <seealso cref="Protocol"/>
    /// </summary>
    public Protocol Key
    {
      get
      {
        return _key;
      }
      set
      {
        __isset.key = true;
        this._key = value;
      }
    }

    public sbyte Ok
    {
      get
      {
        return _ok;
      }
      set
      {
        __isset.ok = true;
        this._ok = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool key;
      public bool ok;
    }

    public Header() {
    }

    public void Read (TProtocol iprot)
    {
      TField field;
      iprot.ReadStructBegin();
      while (true)
      {
        field = iprot.ReadFieldBegin();
        if (field.Type == TType.Stop) { 
          break;
        }
        switch (field.ID)
        {
          case 1:
            if (field.Type == TType.I32) {
              Key = (Protocol)iprot.ReadI32();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 2:
            if (field.Type == TType.Byte) {
              Ok = iprot.ReadByte();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          default: 
            TProtocolUtil.Skip(iprot, field.Type);
            break;
        }
        iprot.ReadFieldEnd();
      }
      iprot.ReadStructEnd();
    }

    public void Write(TProtocol oprot) {
      TStruct struc = new TStruct("Header");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (__isset.key) {
        field.Name = "key";
        field.Type = TType.I32;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteI32((int)Key);
        oprot.WriteFieldEnd();
      }
      if (__isset.ok) {
        field.Name = "ok";
        field.Type = TType.Byte;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteByte(Ok);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("Header(");
      bool __first = true;
      if (__isset.key) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Key: ");
        __sb.Append(Key);
      }
      if (__isset.ok) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Ok: ");
        __sb.Append(Ok);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
