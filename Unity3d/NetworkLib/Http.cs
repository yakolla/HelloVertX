using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Text;
using System.Net;
using System.IO;

namespace NetworkLib
{
	public class Http
	{        

        static public IEnumerator SendPostRequest(string url, byte[] data, Dictionary<string, string> headers, System.Action<WWW> callback, System.Action<string> errorCallback)
        {
            System.Collections.Generic.Dictionary<string, string> defaultHeaders = new System.Collections.Generic.Dictionary<string, string>(); ;
            if (data != null)
            {
                defaultHeaders.Add("Content-Type", "application/octet-stream");
                defaultHeaders.Add("Content-Length", data.Length.ToString());
            }

            if (headers != null)
            {
                foreach(KeyValuePair<string, string> pair in headers)
                {
                    defaultHeaders.Add(pair.Key, pair.Value);
                }                
            }

            WWW www = new WWW(url, data, defaultHeaders);
            yield return www;

            if (!System.String.IsNullOrEmpty(www.error))
            {
                if (errorCallback != null)
                {
                    errorCallback(www.error);
                }
            }
            else
            {
                callback(www);
            }
        }

        static public IEnumerator SendPostRequest(string url, byte[] data, System.Action<WWW> callback, System.Action<string> errorCallback)
        {
            return SendPostRequest(url, data, null, callback, errorCallback);
        }

        static public IEnumerator SendPutRequest(string url, byte[] data, System.Action<string> callback, System.Action<HttpStatusCode> errorCallback)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.Method = "PUT";
            request.ContentType = "application/octet-stream";
            request.ContentLength = data != null ? data.Length : 0;
            
            request.BeginGetRequestStream(new AsyncCallback((IAsyncResult iar) =>
            {
                Stream stream = request.EndGetRequestStream(iar);
               
                if (data != null)
                {
                    stream.Write(data, 0, data.Length);
                }
                    
                stream.Close();

            }), null);           

            request.BeginGetResponse(new AsyncCallback((IAsyncResult iar) =>
            {
                HttpWebResponse response = (HttpWebResponse)request.EndGetResponse(iar);

                if (response.StatusCode == HttpStatusCode.OK)
                {   
                    Stream dataStream = response.GetResponseStream();

                    StreamReader reader = new StreamReader(dataStream, Encoding.UTF8, true);
                    callback(reader.ReadToEnd());

                    reader.Close();
                    dataStream.Close();
                }
                else
                {
                    if (errorCallback != null)
                    {
                        errorCallback(response.StatusCode);
                    }
                }

                response.Close();
            }), null);
            
            yield break;
        }

        static public IEnumerator SendGetRequest(string url, Dictionary<string, string> headers, System.Action<WWW> callback, System.Action<string> errorCallback)
		{
			WWW www = new WWW(url, null, headers); 
			yield return www;

            if (!System.String.IsNullOrEmpty(www.error))
			{
                if (errorCallback != null)
                {
                    errorCallback(www.error);
                }
			}
            else
            {
                callback(www);
            }
		}

        static public IEnumerator SendGetRequest(string url, System.Action<WWW> callback, System.Action<string> errorCallback)
        {
            return SendGetRequest(url, null, callback, errorCallback);

        }
	}
}

