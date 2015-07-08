package com.sample.HelloGame;
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


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.vertx.java.core.Handler;
import org.vertx.java.core.VoidHandler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.net.NetServer;
import org.vertx.java.core.net.NetSocket;

import com.jetdrone.vertx.yoke.core.JSON;
import com.sample.HelloGame.idl.*;


/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class PingVerticle extends Verticle {

	static String webrootDir = "webroot/aa/"; 
	
	public void start() {
		
		container.deployWorkerVerticle("com.sample.HelloGame.ProtocolHandler",  1);
		
		startHttpServer();
		startWebsocketServerWithThrift();
		startPolicyTCPServer();
	}	
	
	void startHttpServer()
	{
		HttpServer server = vertx.createHttpServer();
        RouteMatcher routeMatcher = new RouteMatcher();

        routeMatcher.get("/", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
            	req.response().sendFile(webrootDir + "aa.html");
            }
        });
        
        routeMatcher.get(".*", new Handler<HttpServerRequest>() {
		      public void handle(HttpServerRequest req) {
		    	  System.out.println(req.path());
		        req.response().sendFile(webrootDir + req.path());
		      }
		});

 
        server.requestHandler(routeMatcher).listen(10000);
	}
	
	void startWebsocketServerWithThrift()
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
    			
    	 
    			ws.closeHandler(new Handler<Void>() {
    				@Override
    				public void handle(final Void event) {
    					
    					LogoutReq req = new LogoutReq();
    					req.header = new ProtocolBase();
    					req.header.pid = Protocol.LOGOUT.getValue();
    					
    					JsonObject json = new JsonObject(JSON.encode(req));
						json.putString("chatRoom", chatRoom);
						json.putString("uid", id);
						 
						eventBus.send("game.onrecv", json);
    				}
    			});
    	 
    			ws.dataHandler(new Handler<Buffer>() {
    				@Override
    				public void handle(final Buffer data) {    	 
    					
    					try {    						
    						JsonObject json = new JsonObject(data.toString());
    						json.putString("chatRoom", chatRoom);
    						json.putString("uid", id);
    						String eventBusCode = "game.onrecv"; 
    						eventBus.send(eventBusCode, json);
    						
    						
    					} catch (Exception e) {
    						ws.reject();
    					}
    				}
    			});
    	 
    		}
    	}).listen(10001);
        
	}	
	
	public void startPolicyTCPServer()
	{
		NetServer server = vertx.createNetServer();
		final String AllPolicy =
				"<?xml version='1.0'?>"+
				"<cross-domain-policy>"+
				        "<allow-access-from domain='*' to-ports='*' />"+
				"</cross-domain-policy>";
		
		server.connectHandler(new Handler<NetSocket>() {
		    public void handle(final NetSocket sock) {

		        sock.dataHandler(new Handler<Buffer>() {
		            public void handle(Buffer buffer) {
		                sock.write(new Buffer(AllPolicy));
		                if (sock.writeQueueFull()) {
		                    sock.pause();
		                    sock.drainHandler(new VoidHandler() {
		                        public void handle() {
		                            sock.resume();
		                        }
		                    });
		                }
		            }
		        });

		    }
		}).listen(843);
	}
}
