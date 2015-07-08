package com.sample.HelloWorldDB;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public class DBVerticle extends Verticle {

	public void start() {
		System.out.println("DBVerticle:" +Thread.currentThread().getId());
		EventBus eb = vertx.eventBus();
		
		Handler<Message<String>> myHandler = new Handler<Message<String>>() {
		    public void handle(Message<String> message) {
		        String body = message.body();
		        try {
		        	System.out.println("worker:" + Thread.currentThread().getId());
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        message.reply("This is a reply");
		    }
		};
		
		eb.registerHandler("test.address", myHandler);
	}
}
