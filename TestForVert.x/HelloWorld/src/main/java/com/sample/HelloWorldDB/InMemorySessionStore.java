package com.sample.HelloWorldDB;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import com.jetdrone.vertx.yoke.store.SessionStore;

public class InMemorySessionStore implements SessionStore {

	Map<String, JsonObject> m_map = new HashMap<String, JsonObject>();
	@Override
	public void get(String sid, Handler<JsonObject> callback) {
		System.out.println("InMemorySessionStore::get " + sid);		
		
		callback.handle(m_map.get(sid));
	}

	@Override
	public void set(String sid, JsonObject sess, Handler<Object> callback) {
		System.out.println("InMemorySessionStore::set " + sid);
		
		m_map.put(sid, sess);
		
		callback.handle(sess);
	}

	@Override
	public void destroy(String sid, Handler<Object> callback) {
		System.out.println("InMemorySessionStore::destroy " + sid);
		
		m_map.remove(sid);
		
		callback.handle(null);
	}

	@Override
	public void all(Handler<JsonArray> callback) {
		System.out.println("InMemorySessionStore::all ");
		
		JsonArray jsonArray = new JsonArray();
		
		for(Entry<String, JsonObject> entry : m_map.entrySet()) {
			jsonArray.add(entry.getValue());
		}
		
		callback.handle(jsonArray);
	}

	@Override
	public void clear(Handler<Object> callback) {
		System.out.println("InMemorySessionStore::clear ");
		
		m_map.clear();
		
		callback.handle(null);
	}

	@Override
	public void length(Handler<Integer> callback) {
		System.out.println("InMemorySessionStore::length ");
		
		callback.handle(m_map.size());
	}

}
