using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;

using Newtonsoft.Json;

using System.Threading;
using NetworkLib;
using NetworkLib.WebSocketSharp;
using Thrift;


public class ButtonHandler : MonoBehaviour {
	
	class MyResponse {
		public byte[] publicKeyModulus;
		public byte[] publicKeyExponent;
	}

	public void OnCipherTestClick()
	{
		NetworkLib.Cryptography.RSA.KeyPair keyPair = new NetworkLib.Cryptography.RSA.KeyPair ();
		byte[] encrypted = NetworkLib.Cryptography.RSA.Encryption ("haha호호!!", keyPair.publicKey, false);
		string decryptedText = NetworkLib.Cryptography.RSA.DecryptionToString (encrypted, keyPair.privateKey, false);
		Debug.Log (decryptedText);
	}

	string GetSessionId(WWW www)
	{
		if (www.responseHeaders.ContainsKey("SET-COOKIE"))
		{
			// extract the session identifier cookie and save it
			// the cookie will be named, "auth" (this could be something else in your case)
			char[] splitter = { ';' };
			string[] v = www.responseHeaders["SET-COOKIE"].Split(splitter);
			string SessionIdCookieName = "sessionId";
			foreach (string s in v)
			{
				if (string.IsNullOrEmpty(s)) continue;
				if (s.Substring(0, SessionIdCookieName.Length).Equals(SessionIdCookieName))
				{   // found it
					return s;
				}
			}
		}

		return null;
	}

	public void OnCipherOverWebServerTestClick()
	{
		float startTime = Time.time;
		for (int i = 0; i < 100; ++i) {
			StartCoroutine (Http.SendPostRequest ("http://127.0.0.1:9090/login", Encoding.UTF8.GetBytes ("name=asdfsdf&gender=female&username=asdfsadfs&password=sdfasdfsadfasfdsa&terms=on"), null, (WWW www) => {
				
				Debug.Log (www.text);
				Debug.Log ("duration:" + (Time.time-startTime));

			},
			(string err) => {
				Debug.Log (err);
			}
			));
		}
	}

	public void OnCipherOverWebServerTestClick1()
	{

		StartCoroutine (Http.SendGetRequest("http://127.0.0.1:808/login", (WWW www)=>{
			
			Debug.Log(www.text);

			MyResponse jData = Newtonsoft.Json.JsonConvert.DeserializeObject<MyResponse>(www.text);			
			NetworkLib.Cryptography.RSA.PublicKey publicKey = new NetworkLib.Cryptography.RSA.PublicKey(jData.publicKeyModulus, jData.publicKeyExponent);			
			byte[] encText = NetworkLib.Cryptography.RSA.Encryption("haha하하!", publicKey, false);

			string sessionId = GetSessionId(www);
			Debug.Log(sessionId);
			Dictionary<string, string> cookie = new Dictionary<string, string>();
			cookie.Add("COOKIE", sessionId);
			
			StartCoroutine(Http.SendPostRequest("http://127.0.0.1:808/hello", encText, cookie, (WWW www1)=>{
				Debug.Log(www1.text);
			},
			(string err)=>{
				Debug.Log(err);
			}
			));
		},
		(string err)=>{
			Debug.Log(err);
		}
		));
	}

	public void OnPostMethodTestClick()
	{
		StartCoroutine(Http.SendPostRequest("http://127.0.0.1:808/name/aaa", Encoding.UTF8.GetBytes("name=aaa&age=14"), (WWW www)=>{
			Debug.Log(www.text);
		},
		(string err)=>{
			Debug.Log(err);
		}
		));
		Debug.Log ("post");
	}

	public void OnPutMethodTestClick()
	{
		StartCoroutine(SendPutRequest("http://127.0.0.1:8078/age/122", Encoding.UTF8.GetBytes("name=aaa&age=14"), (string text)=>{
		//StartCoroutine(Http.SendPutRequest("http://127.0.0.1:808/age/122", null, (string text)=>{
			Debug.Log(text);
		},
		(HttpStatusCode err)=>{
			Debug.Log(err);
		}
		));
		Debug.Log ("put");
	}

	class MyJson {
		public string name;
		public int age;
	}
	public void OnWebSocketTestClick()
	{
		WebSocket ws = new WebSocket ("ws://127.0.0.1:8090/chat/1");
		ws.OnMessage += (sender, e) =>{
			Debug.Log("Laputa says: " + e.Data);
		};

		ws.OnOpen += (object sender, System.EventArgs e) => {
			MyJson json = new MyJson ();
			json.age = 11;
			json.name = "aaa";
			
			ws.SendAsync (JsonConvert.SerializeObject(json), (bool s) =>{
				Debug.Log("sent :" + s);
			});

		};

		ws.OnError += (object sender, NetworkLib.WebSocketSharp.ErrorEventArgs e) => {
			Debug.Log("error :" + e.Exception.Message);
		};

		ws.OnClose += (object sender, CloseEventArgs e) => {
			Debug.Log("close");
		};

		ws.ConnectAsync();
	}

	public void OnWebSocketWithThriftTestClick()
	{
		ThriftHandler<idl.Protocol> thandler = new ThriftHandler<idl.Protocol> ();

		thandler.AddHandler(idl.Protocol.Test1, (idl.TestAck ack) => {
			Debug.Log("Laputa says: " + ack.ToString());
		});

		thandler.AddHandler(idl.Protocol.Test2, (idl.Test2Ack ack) => {
			Debug.Log("Laputa says: " + ack.ToString());
		});

		WebSocket ws = new WebSocket ("ws://127.0.0.1:8091/chat/1");
		ws.OnMessage += (sender, e) =>{

			MemoryStream stream = new MemoryStream(e.RawData);
			Thrift.Protocol.TProtocol tProtocol = new Thrift.Protocol.TBinaryProtocol(new Thrift.Transport.TStreamTransport(stream, null));
			tProtocol.ReadStructBegin();
			tProtocol.ReadFieldBegin();
			idl.Header header = new idl.Header();
			header.Read(tProtocol);
			stream.Position = 0;

			thandler.DoHandle(header.Key, tProtocol);

		};
		
		ws.OnOpen += (object sender, System.EventArgs e) => {
			idl.TestReq shared = new idl.TestReq();
			shared.Header = new idl.Header();
			shared.Header.Key = idl.Protocol.Test1;
			shared.Key = 11;
			shared.Value = "aaa";

			ws.SendAsync (shared, (bool s) =>{
				Debug.Log("sent :" + s);
			});
		};
		
		ws.OnError += (object sender, NetworkLib.WebSocketSharp.ErrorEventArgs e) => {
			Debug.Log("error :" + e.Exception.Message);
		};
		
		ws.OnClose += (object sender, CloseEventArgs e) => {
			Debug.Log("close");
		};
		
		ws.ConnectAsync();
	}

	public void OnWebSocketWithThriftServiceTestClick()
	{
		WebSocket ws = new WebSocket ("ws://127.0.0.1:8092/chat/1");
		ws.OnMessage += (sender, e) =>{
			
			MemoryStream stream = new MemoryStream(e.RawData);
			Thrift.Protocol.TProtocol tProtocol = new Thrift.Protocol.TBinaryProtocol(new Thrift.Transport.TStreamTransport(stream, null));
			idl.MultiplicationService.Client client = new idl.MultiplicationService.Client(tProtocol);
			int aa = client.recv_multiply();

			Debug.Log("recv :" + aa);
		};
		
		ws.OnOpen += (object sender, System.EventArgs e) => {

			MemoryStream stream = new MemoryStream(256);
			Thrift.Protocol.TProtocol tProtocol = new Thrift.Protocol.TBinaryProtocol(new Thrift.Transport.TStreamTransport(null, stream));
			idl.MultiplicationService.Client client = new idl.MultiplicationService.Client(tProtocol);
			client.send_multiply(11, 22);
			ws.SendAsync (stream.ToArray(), (bool s) =>{
				Debug.Log("sent :" + s);
			});
		};
		
		ws.OnError += (object sender, NetworkLib.WebSocketSharp.ErrorEventArgs e) => {
			Debug.Log("error :" + e.Exception.Message);
		};
		
		ws.OnClose += (object sender, CloseEventArgs e) => {
			Debug.Log("close");
		};
		
		ws.ConnectAsync();
	}

	SecuredType.XInt cheatValue = 0;
	public void OnClickTranslate(Text t)
	{
		++cheatValue.Value;
		bool b = cheatValue == 1;
		t.text = cheatValue.ToString ();

		PositionAsUV1 pos;

	}

	private static void TimeoutCallback(object state, bool timedOut) { 
		if (timedOut) {
			HttpWebRequest request = state as HttpWebRequest;
			if (request != null) {
				request.Abort();
			}
		}
	}

	static public IEnumerator SendPutRequest(string url, byte[] data, System.Action<string> callback, System.Action<HttpStatusCode> errorCallback)
	{
		try 
		{

			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
			request.Method = "PUT";
			request.ContentType = "application/octet-stream";
			request.ContentLength = data != null ? data.Length : 0;
			request.Timeout = 1000;
			request.ReadWriteTimeout = 1000;

			request.BeginGetRequestStream(new System.AsyncCallback((System.IAsyncResult iar) =>
			{
				Stream stream = request.EndGetRequestStream(iar);
				
				if (data != null)
				{
					stream.Write(data, 0, data.Length);
				}

				stream.Close();

				request.BeginGetResponse(new System.AsyncCallback((System.IAsyncResult iar1) =>
				                                                  {
					HttpWebResponse response = (HttpWebResponse)request.EndGetResponse(iar1);
					
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
			}), null);           
			
			


		}
		catch(System.Exception e) {
			if (errorCallback != null)
			{
				System.Console.WriteLine(e.Message);
				errorCallback(HttpStatusCode.NotFound);
			}
		}
		
		yield return null;
	}

	static public IEnumerator SendPostRequest(string url, byte[] data, Dictionary<string, string> headers, System.Action<WWW> callback, System.Action<string> errorCallback)
	{
		try 
		{
			
			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
			request.Method = "POST";
			request.ContentType = "application/octet-stream";
			request.ContentLength = data != null ? data.Length : 0;
			request.Timeout = 1000;
			request.ReadWriteTimeout = 1000;
			
			request.BeginGetRequestStream(new System.AsyncCallback((System.IAsyncResult iar) =>
			                                                       {
				Stream stream = request.EndGetRequestStream(iar);
				
				if (data != null)
				{
					stream.Write(data, 0, data.Length);
				}
				
				stream.Close();


				
				request.BeginGetResponse(new System.AsyncCallback((System.IAsyncResult iar1) =>
				                                                  {

					HttpWebResponse response = (HttpWebResponse)request.EndGetResponse(iar1);


					if (response.StatusCode == HttpStatusCode.OK)
					{   
						Stream dataStream = response.GetResponseStream();
						
						StreamReader reader = new StreamReader(dataStream, Encoding.UTF8, true);
						//callback(reader.ReadToEnd());
						
						reader.Close();
						dataStream.Close();
					}
					else
					{
						if (errorCallback != null)
						{
							//errorCallback(response.StatusCode);
						}
					}
					
					response.Close();
				}), null);
			}), null);           
			
			
			
			
		}
		catch(System.Exception e) {
			if (errorCallback != null)
			{
				System.Console.WriteLine(e.Message);
				//errorCallback(HttpStatusCode.NotFound);
			}
		}
		
		yield return null;
	}
}
