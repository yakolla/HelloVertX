package com.sample.HelloWebsocket;
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

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class PingVerticle extends Verticle {

  public void start() {
	  final Pattern chatUrlPattern = Pattern.compile("/chat/(\\w+)");
		final EventBus eventBus = vertx.eventBus();
		final Logger logger = container.logger();

		EventBus eb = vertx.eventBus();
		  
        // Register Handler 1
        eb.registerLocalHandler("app.conduit",
                new Handler<Message>() {
 
                    @Override
                    public void handle(Message message) {
                        logger.info("Handler 1 received: "
                                + message.body().toString());
                    }
 
                });
 
        // Register Handler 2
        eb.registerHandler("app.conduit", new Handler<Message>() {
 
            @Override
            public void handle(Message message) {
                logger.info("Handler 2 received: " + message.body().toString());
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
				logger.info("registering new connection with id: " + id + " for chat-room: " + chatRoom);
				vertx.sharedData().getSet("chat.room." + chatRoom).add(id);
		 
				ws.closeHandler(new Handler<Void>() {
					@Override
					public void handle(final Void event) {
						logger.info("un-registering connection with id: " + id + " from chat-room: " + chatRoom);
						vertx.sharedData().getSet("chat.room." + chatRoom).remove(id);
					}
				});
		 
				ws.dataHandler(new Handler<Buffer>() {
					@Override
					public void handle(final Buffer data) {
		 
						ObjectMapper m = new ObjectMapper();
						try {
							JsonNode rootNode = m.readTree(data.toString());
							((ObjectNode) rootNode).put("received", new Date().toString());
							String jsonOutput = m.writeValueAsString(rootNode);
							logger.info("json generated: " + jsonOutput);
							for (Object chatter : vertx.sharedData().getSet("chat.room." + chatRoom)) {
								eventBus.send((String) chatter, jsonOutput);
							}
						} catch (IOException e) {
							ws.reject();
						}
					}
				});
		 
			}
		}).listen(8090);
	  
  }
}
