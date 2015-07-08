package com.sample.HelloWorldWithUnity3d;
/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.shareddata.Shareable;

import com.sample.idl.*;
import com.pv.networklib.ThriftHandler;
import com.pv.networklib.cryptography.RSA;


/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class PingVerticle extends Verticle {

	class UserSession implements Shareable 
	{
		public PrivateKey privateKey;
	}
	
	public void start() {
		
		System.out.println("PingVerticle");
		
		
		container.deployVerticle("com.sample.HelloWorldWithJavaScript.PingVerticle", 1);
		container.deployVerticle("com.sample.HelloGame.PingVerticle", 1);
		
		
		startHttpServer();
        startWebsocketServerWithJson();
        startWebsocketServerWithThrift();
        startWebsocketServerWithThriftService();
        
	  }
	
	String GetSessionId(HttpServerRequest req)
	{		
		String value = req.headers().get("Cookie");
		if (value != null) {
			Set<Cookie> cookies = CookieDecoder.decode(value);        			
			for (final Cookie cookie : cookies) {
				if ("sessionId".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		
		return null;
	}
	
	String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
	
	void startHttpServer()
	{
		HttpServer server = vertx.createHttpServer();
        RouteMatcher routeMatcher = new RouteMatcher();

        routeMatcher.get("/", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
            	req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
    			System.out.println("Hello World!");
    			
    			TestReq fooBarMessage = new TestReq();
    			fooBarMessage.key = 123456789;
    			fooBarMessage.value = "abc";
    			ByteArrayOutputStream outStream = new ByteArrayOutputStream(256);
    			TProtocol tProtocol = new TBinaryProtocol(new TIOStreamTransport(null,  outStream));
    			
    			try {
					fooBarMessage.write(tProtocol);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			 
    			byte[] content = outStream.toByteArray();
    			
    			tProtocol = new TBinaryProtocol(new TIOStreamTransport(new ByteArrayInputStream(content), null));
    			TestReq fooBarMessage2 = new TestReq();
    			try {
					fooBarMessage2.read(tProtocol);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    	        try {
    	            // 세션 열기
    	        	Session session = HibernateUtil.getCurrentSession(); 
    	            MyTestTable entity = (MyTestTable)session.get(MyTestTable.class, 1);
    	            entity.setName("bbb");
    	            Transaction tx = session.beginTransaction();
    	            session.update(entity);
    	            tx.commit();
    	            req.response().end("Hello World " + entity.getName());	            
    	        } 
    	        catch (HibernateException e) {
    	            e.printStackTrace();
    	        } 
    	        finally {
    	            // 세션 닫기
    	            HibernateUtil.closeSession();
    	        }
            }
        });

        routeMatcher.get("/login", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
            	
            	
				try {
					
					String sid = GetSessionId(req);
	            	if (sid == null)
	            	{
	            		req.response().headers().set("Set-Cookie", ServerCookieEncoder.encode(new DefaultCookie("sessionId", generateSessionId())));
	            	}
	            	
	            	KeyPair keyPair = RSA.CreateKeyPair();
	            	
	            	PublicKey publicKey = keyPair.getPublic();
	            	PrivateKey privateKey = keyPair.getPrivate();
	            	
	            	RSAPublicKeySpec publicSpec = RSA.GetPublicKeySpec(keyPair);
	            	
	            	byte[] publicKeyModulus = publicSpec.getModulus().toByteArray();
	            	byte[] publicKeyExponent = publicSpec.getPublicExponent().toByteArray();	            	
	            	
	            	// test
	            	byte[] encryptedData = RSA.Encrypt(publicKey, "haha!");
	            	String decryptedText = RSA.DecryptToString(privateKey, encryptedData);
	                
	                JsonObject json = new JsonObject();
	                json.putBinary("publicKeyModulus", publicKeyModulus);
	                json.putBinary("publicKeyExponent", publicKeyExponent);
	                
	                UserSession userSession = new UserSession();
	                userSession.privateKey = privateKey;
	                
	                vertx.sharedData().getMap("1").put("userSession", userSession);
	            	req.response().end(json.toString());
					
	            	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            	

            }
        });
        
        routeMatcher.post("/hello", new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                
            	String sid = GetSessionId(req);
                
                req.bodyHandler(new Handler<Buffer>()
                {
                    @Override
                    public void handle(Buffer buff)
                    {
                        String contentType = req.headers().get("Content-Type");
                        if ("application/octet-stream".equals(contentType))
                        {
                        	/*
                            QueryStringDecoder qsd = new QueryStringDecoder(buff.toString(), false);
                            Map<String, List<String>> params = qsd.parameters();
                            System.out.println(params);
                            */
                            
                            UserSession userSession = (UserSession)vertx.sharedData().getMap("1").get("userSession");
                        	
                            try {
                            	String decryptedText = RSA.DecryptToString(userSession.privateKey, buff.getBytes());
            		            
            		            JsonObject json = new JsonObject();
            	                json.putString("decryptedText", decryptedText);
            	                
            	                req.response().end(json.toString());
            				} catch (Exception e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
                        }
                    }
                });                
            }
        });
        
        routeMatcher.post("/name/:name", new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                
            	req.bodyHandler(new Handler<Buffer>()
                    {
                        @Override
                        public void handle(Buffer buff)
                        {
                        	
                        	QueryStringDecoder qsd = new QueryStringDecoder(buff.toString(), false);
                            Map<String, List<String>> params = qsd.parameters();
                            System.out.println(params);
                            
                        	req.response().end("Your name is " + req.params().get("name"));
                        }
                    }
            	);
            	
                  
            }
        });
        
        routeMatcher.put("/age/:age", new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                
            	req.bodyHandler(new Handler<Buffer>()
                    {
                        @Override
                        public void handle(Buffer buff)
                        {
                        	
                        	QueryStringDecoder qsd = new QueryStringDecoder(buff.toString(), false);
                            Map<String, List<String>> params = qsd.parameters();
                            System.out.println(params);
                            
                            if (params.size() > 0)
                            	req.response().end("Your age is " + req.params().get("age") + params.get("name").get(0));
                            else
                            	req.response().end("Your age is " + req.params().get("age"));
                        }
                    }
            	);
            	
                  
            }
        });
        
 
        routeMatcher.get("/json", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                JsonObject obj = new JsonObject().putString("name", "chope");
                req.response().end(obj.encode());
            }
        });
 
        
 
        server.requestHandler(routeMatcher).listen(808, "localhost");
	}
	
	void startWebsocketServerWithJson()
	{
		final EventBus eventBus = vertx.eventBus();
        final Pattern chatUrlPattern = Pattern.compile("/chat/(\\w+)");
        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
    	  	
            
    		@Override
    		public void handle(final ServerWebSocket ws) {
    			final Matcher m = chatUrlPattern.matcher(ws.path());
    			if (!m.matches()) {
    				ws.reject();
    				return;
    			}
    	 
    			final String chatRoom = m.group(1);
    			final String id = ws.textHandlerID();
    			System.out.println("registering new connection with id: " + id + " for chat-room: " + chatRoom);
    			vertx.sharedData().getSet("chat.room." + chatRoom).add(id);
    	 
    			ws.closeHandler(new Handler<Void>() {
    				@Override
    				public void handle(final Void event) {
    					System.out.println("un-registering connection with id: " + id + " from chat-room: " + chatRoom);
    					vertx.sharedData().getSet("chat.room." + chatRoom).remove(id);
    				}
    			});
    	 
    			ws.dataHandler(new Handler<Buffer>() {
    				@Override
    				public void handle(final Buffer data) {    	 
    					
    					try {
    						
    						JSONObject json = (JSONObject) new JSONParser().parse(data.toString());
    						for (Object chatter : vertx.sharedData().getSet("chat.room." + chatRoom)) {
    							eventBus.send((String) chatter, json.toString());    							
    						}
    					} catch (Exception e) {
    						ws.reject();
    					}
    				}
    			});
    	 
    		}
    	}).listen(8090);
	}
	    
	void startWebsocketServerWithThrift()
	{
		final EventBus eventBus = vertx.eventBus();
        final Pattern chatUrlPattern = Pattern.compile("/chat/(\\w+)");
        
        final ThriftHandler<Protocol, ServerWebSocket> thandler = new ThriftHandler<Protocol, ServerWebSocket> ();
        thandler.AddHandler(TestReq.class, Protocol.Test1, new ThriftHandler.Handler<ServerWebSocket, TestReq>() {        	

			@Override
			public void DoHandler(ServerWebSocket ws, TestReq req) {
				// TODO Auto-generated method stub
				ByteArrayOutputStream outStream = new ByteArrayOutputStream(256);
				TBinaryProtocol tProtocol = new TBinaryProtocol(new TIOStreamTransport(null,  outStream));
    			
    			try {
    				TestAck testAck = new TestAck();
    				testAck.header = new Header();
    				testAck.header.key = Protocol.Test1;
    				testAck.header.ok = 1;
    				//testAck.setIvalue(true);
    				testAck.value = "aa";
    				
    				testAck.write(tProtocol);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			ws.writeBinaryFrame(new Buffer(outStream.toByteArray()));    	
			}
		});
        
        thandler.AddHandler(TestReq.class, Protocol.Test2, new ThriftHandler.Handler<ServerWebSocket, TestReq2>() {        	

			@Override
			public void DoHandler(ServerWebSocket ws, TestReq2 req) {
				// TODO Auto-generated method stub
				ByteArrayOutputStream outStream = new ByteArrayOutputStream(256);
				TBinaryProtocol tProtocol = new TBinaryProtocol(new TIOStreamTransport(null,  outStream));
    			
    			try {
    				Test2Ack testAck = new Test2Ack();
    				testAck.header = new Header();
    				testAck.header.key = Protocol.Test1;
    				testAck.header.ok = 1;
    				//testAck.setIvalue(true);
    				
    				testAck.write(tProtocol);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			ws.writeBinaryFrame(new Buffer(outStream.toByteArray()));    	
			}
		});
        
        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
    	  	
            
    		@Override
    		public void handle(final ServerWebSocket ws) {
    			final Matcher m = chatUrlPattern.matcher(ws.path());
    			if (!m.matches()) {
    				ws.reject();
    				return;
    			}
    	 
    			final String chatRoom = m.group(1);
    			final String id = ws.textHandlerID();
    			System.out.println("registering new connection with id: " + id + " for chat-room: " + chatRoom);
    			vertx.sharedData().getSet("chat.room." + chatRoom).add(id);
    	 
    			ws.closeHandler(new Handler<Void>() {
    				@Override
    				public void handle(final Void event) {
    					System.out.println("un-registering connection with id: " + id + " from chat-room: " + chatRoom);
    					vertx.sharedData().getSet("chat.room." + chatRoom).remove(id);
    				}
    			});
    	 
    			ws.dataHandler(new Handler<Buffer>() {
    				@Override
    				public void handle(final Buffer data) {    	 
    					
    					try {    						
    						ByteArrayInputStream stream = new ByteArrayInputStream(data.getBytes());
    						TProtocol tProtocol = new TBinaryProtocol(new TIOStreamTransport(stream, null));
    						tProtocol.readStructBegin();
    						tProtocol.readFieldBegin();
    						Header header = new Header();
    						header.read(tProtocol);
    						stream.reset();
    						
    						thandler.DoHandle(ws, Protocol.Test1, tProtocol);
    						
    						
    					} catch (Exception e) {
    						ws.reject();
    					}
    				}
    			});
    	 
    		}
    	}).listen(8091);
        
	}
	
	void startWebsocketServerWithThriftService()
	{
		MultiplicationHandler handler = new MultiplicationHandler();;

		final MultiplicationService.Processor processor = new MultiplicationService.Processor(handler);
		
		final EventBus eventBus = vertx.eventBus();
        final Pattern chatUrlPattern = Pattern.compile("/chat/(\\w+)");
        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
    	  	
            
    		@Override
    		public void handle(final ServerWebSocket ws) {
    			final Matcher m = chatUrlPattern.matcher(ws.path());
    			if (!m.matches()) {
    				ws.reject();
    				return;
    			}
    	 
    			final String chatRoom = m.group(1);
    			final String id = ws.textHandlerID();
    			System.out.println("registering new connection with id: " + id + " for chat-room: " + chatRoom);
    			vertx.sharedData().getSet("chat.room." + chatRoom).add(id);
    	 
    			ws.closeHandler(new Handler<Void>() {
    				@Override
    				public void handle(final Void event) {
    					System.out.println("un-registering connection with id: " + id + " from chat-room: " + chatRoom);
    					vertx.sharedData().getSet("chat.room." + chatRoom).remove(id);
    				}
    			});
    	 
    			ws.dataHandler(new Handler<Buffer>() {
    				@Override
    				public void handle(final Buffer data) {    	 
    					
    					try {    						
    						
    		    			TProtocol tInProtocol = new TBinaryProtocol(new TIOStreamTransport(new ByteArrayInputStream(data.getBytes()), null));
    		    			ByteArrayOutputStream outStream = new ByteArrayOutputStream(data.length());
    		    			TProtocol tOutProtocol = new TBinaryProtocol(new TIOStreamTransport(null,  outStream));
    		    			
    		    			processor.process(tInProtocol, tOutProtocol);
    		    			
    		    			TestReq fooBarMessage2 = new TestReq();
    		    			ws.writeBinaryFrame(new Buffer(outStream.toByteArray()));    		    			
    						
    					} catch (Exception e) {
    						ws.reject();
    					}
    				}
    			});
    	 
    		}
    	}).listen(8092);
	}
}
