package com.sample.HelloWorldWithJavaScript;
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


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.validation.constraints.NotNull;

import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.QueryStringDecoder;

import org.vertx.java.core.Handler;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;

import com.jetdrone.vertx.yoke.Middleware;
import com.jetdrone.vertx.yoke.Yoke;
import com.jetdrone.vertx.yoke.engine.StringPlaceholderEngine;
import com.jetdrone.vertx.yoke.middleware.AuthHandler;
import com.jetdrone.vertx.yoke.middleware.BodyParser;
import com.jetdrone.vertx.yoke.middleware.CookieParser;
import com.jetdrone.vertx.yoke.middleware.Favicon;
import com.jetdrone.vertx.yoke.middleware.FormAuth;
import com.jetdrone.vertx.yoke.middleware.Router;
import com.jetdrone.vertx.yoke.middleware.Session;
import com.jetdrone.vertx.yoke.middleware.Static;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;
import com.jetdrone.vertx.yoke.store.SessionStore;
import com.jetdrone.vertx.yoke.store.json.SessionObject;
import com.sample.HelloWorldDB.InMemorySessionStore;


/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class PingVerticle extends Verticle {

	public void start() {
		container.deployWorkerVerticle("com.sample.HelloWorldDB.DBVerticle",  1);
		System.out.println("PingVerticle2");
		startHttpServer();
	}
	
	
	void startHttpServer()
	{
		final EventBus eb = vertx.eventBus();		
		
		final Yoke app = new Yoke(this);
		app.secretSecurity("keyboard cat");
		
		final Middleware RequiredAuth = new Middleware() {
	        @Override
	        public void handle(@NotNull final YokeRequest request, @NotNull final Handler<Object> next) {
	        	SessionObject session = request.get("session");

	            if (session != null) {
	                if (session.getString("id") != null) {
	                    next.handle(null);
	                    return;
	                }
	            }

	            request.response().redirect("/examples");
	        }
	    };
		
		final Mac hmac = app.security().getMac("HmacSHA256");
		
		  app.use(new Favicon())
		  .store(new InMemorySessionStore())
		  .engine(new StringPlaceholderEngine("webroot"))
		  .use(new BodyParser())
		  .use(new CookieParser(hmac))
		  .use(new Session(hmac))
		  .use(new Static("webroot/examples"))
		  .use(new Router()
		    .get("/examples", new Handler<YokeRequest>() {
		      @Override
		      public void handle(YokeRequest request) {
		    	  
		    	  System.out.println("ex:"+Thread.currentThread().getId());
		    	  request.response().render("examples/login.shtml");		    	  
		      }
		    })	
		    
		     .get("/profile",RequiredAuth, new Middleware() {
                    @Override
                    public void handle(final YokeRequest request, Handler<Object> next) {                  
                    	SessionObject session = request.get("session");
                    	System.out.println("lo:"+Thread.currentThread().getId());                   	                   	
                    	
                    	for (String key : session.getFieldNames())
                    	{
                    		request.put(key, session.getString(key));
                    	}
                    	request.response().render("examples/profile.shtml"); 
                    	
                    }
                })
		    
		    .get(".*", new Handler<YokeRequest>() {
			      public void handle(YokeRequest req) {
			        req.response().sendFile("webroot/" + req.path());
			      }
			    })
			    
			    
			   
		    .post("/login", new Middleware() {
                    @Override
                    public void handle(final YokeRequest request, Handler<Object> next) {                  
                    	
                    	System.out.println("lo:"+Thread.currentThread().getId());                   	                   	
                    	
                    	
                    	final JsonObject session = request.createSession();
                    	
                    	if (request.body() != null)
                    	{                    		
                            
                            eb.send("test.address", "This is a message", new Handler<Message<String>>() {
                        	    public void handle(Message<String> message) {
                        	        System.out.println("I received a reply " + message.body());
                        	        
                        	        QueryStringDecoder qsd = new QueryStringDecoder(request.body().toString(), false);
                                    Map<String, List<String>> params = qsd.parameters();
                                    
                        	        System.out.println(request.body());
                        	        
                                	for (Map.Entry<String, List<String>> entry : params.entrySet())
                                	{
                                		session.putString(entry.getKey().toString(), entry.getValue().toString());                                		
                                		request.put(entry.getKey().toString(), entry.getValue().toString());
                                		
                                	}
                                	request.response().render("examples/welcome.shtml");                            	
                        	    }
                        	});	
                    	}
                    	else
                    	{
                    		eb.send("test.address", "This is a message", new Handler<Message<String>>() {
                        	    public void handle(Message<String> message) {
                        	        System.out.println("I received a reply " + message.body());
                        	        
                        	        System.out.println(request.formAttributes());
                                	
                                	for (Map.Entry entry : request.formAttributes().entries())
                                	{
                                		session.putString(entry.getKey().toString(), entry.getValue().toString());
                                		request.put(entry.getKey().toString(), entry.getValue().toString());
                                	}
                                	
                                	request.put("tag", "<button class='ui active button' id='One'>${name}${name}</button>");
                                	request.response().render("examples/welcome.shtml");                            	
                        	    }
                        	});	
                    	}
                    }
                })
                                
		   
			).listen(9090);
	}
}
