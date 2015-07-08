package com.sample.HelloGame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import com.jetdrone.vertx.yoke.core.JSON;
import com.sample.HelloGame.idl.*;
import com.google.gson.Gson;
public class ProtocolHandler extends Verticle {

	final int TimePerFrame = 1000;
	ArrayList<JsonObject>	m_queue = new ArrayList<JsonObject>();
	HashMap< Protocol , Handler<JsonObject> > m_handlers = new HashMap< Protocol, Handler<JsonObject> >();	
	
	
	public void start() {
		System.out.println("ProtocolHandler:" +Thread.currentThread().getId());
		EventBus eventBus = vertx.eventBus();		
		
		GameTime.Instance().SetTick();
		
		spawnTower(0, new Vector3f(-5, 0, 0));
		spawnTower(1, new Vector3f(5, 0, 0));
		
		vertx.setPeriodic(TimePerFrame, (Long timerID) -> {
			GameTime.Instance().SetTick();
			//System.out.println("Time:" + GameTime.Instance().Tick() + " DeltaTime:" + GameTime.Instance().DeltaTime());
		    
			
			
			//spawnMinions(0);
			//spawnMinions(1);
			
			CreatureStateAck stateAck = new CreatureStateAck();
			stateAck.header = new ProtocolBase(Protocol.CREATURE_STATE.getValue());
			stateAck.serverTick = GameTime.Instance().Tick();
			stateAck.creatureStates = new ArrayList<CreatureState>();
			
			CreatureManager.Instance().m_creatures.forEach((key, creature)->{
				
				creature.Update();
			});
			
			m_queue.forEach((data)->{
				m_handlers.get(Protocol.findByValue(data.getObject("header").getInteger("pid"))).handle(data);
			});
			
			ArrayList<String>	removeBullets = new ArrayList<String>();
			
			CreatureManager.Instance().m_bullets.forEach((key, bullet)->{
				
				bullet.Update();			
				
				addCreatureStateToAckMsg(stateAck, bullet);
				
				if (bullet.m_death == true)
					removeBullets.add(key);				
			});
			
			removeBullets.forEach((key)->{
				CreatureManager.Instance().m_bullets.remove(key);
			});
			
			
			CreatureManager.Instance().m_creatures.forEach((key, creature)->{
				
				addCreatureStateToAckMsg(stateAck, creature);
			});
			
			broadcastMsg("1", stateAck, null);
			
			
		        
			m_queue.clear();
		});
		
		eventBus.registerHandler("game.onrecv", (Message<JsonObject> message)->{
			m_queue.add(message.body());
			
		});
		
		m_handlers.put(Protocol.LOGIN, (JsonObject message)->{
			String chatRoom = message.getString("chatRoom");
			String uid = message.getString("uid");
			
			System.out.println("registering new connection with id: " + uid + " for chat-room: " + chatRoom);
			vertx.sharedData().getSet("chat.room." + chatRoom).add(uid);
			
			LoginReq req = new Gson().fromJson(message.toString(), LoginReq.class);
			
			CreatureManager.Instance().m_creatures.put(uid, new Champ(uid, req.camp, new Vector3f(0, 0, 0)));
			
			LoginAck ack = new LoginAck();
			ack.header = new ProtocolBase(Protocol.LOGIN.getValue());
			ack.name = uid;
			ack.serverTick = GameTime.Instance().Tick();
			
			sendMsg(uid, ack);
			
		});
		
		m_handlers.put(Protocol.LOGOUT, (JsonObject message)->{
			String chatRoom = message.getString("chatRoom");
			String uid = message.getString("uid");
			
			System.out.println("un-registering connection with id: " + uid + " from chat-room: " + chatRoom);
			vertx.sharedData().getSet("chat.room." + chatRoom).remove(uid);
			
			CreatureManager.Instance().m_creatures.remove(uid);
			
			LogoutAck ack = new LogoutAck();
			ack.header = new ProtocolBase(Protocol.LOGOUT.getValue());
			ack.name = uid;
			
			broadcastMsg(chatRoom, ack, null);
			
		});
		
		m_handlers.put(Protocol.MOVE, (JsonObject message)->{
			String chatRoom = message.getString("chatRoom");
			String uid = message.getString("uid");
			
			try {
				MoveReq req = new Gson().fromJson(message.toString(), MoveReq.class);
				
				CreatureManager.Instance().m_creatures.get(uid).Move(new Vector3f((float)req.goal.x, (float)req.goal.y, (float)req.goal.z));				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		
		m_handlers.put(Protocol.FIRE_BULLET, (JsonObject message)->{
			String chatRoom = message.getString("chatRoom");
			String uid = message.getString("uid");
			
			try {
				
				Creature creture = CreatureManager.Instance().m_creatures.get(uid);
				
				FireBulletReq req = new Gson().fromJson(message.toString(), FireBulletReq.class);				
				
				CreatureManager.Instance().CreateBullet(creture, new Vector3f((float)req.dir.x, (float)req.dir.y, (float)req.dir.z), 5f);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		
	}
	
	void spawnTower(int camp, Vector3f pos)
	{
		String uid = UUID.randomUUID().toString();
		CreatureManager.Instance().m_creatures.put(uid, new Tower(uid, camp, pos));
	}
	
	int m_minionCount;
	final int m_maxMinionCount = 10;
	void spawnMinions(int camp)
	{
		if (m_maxMinionCount < m_minionCount)
			return;
		
		String uid = UUID.randomUUID().toString();
		CreatureManager.Instance().m_creatures.put(uid, new Minion(uid, camp, new Vector3f(-5f+(float)Math.random()*10, 0f, -5f+(float)Math.random()*10)));
		
		++m_minionCount;
		
	}
	
	void sendMsg(String uid, Object ack)
	{
		vertx.eventBus().send(uid, JSON.encode(ack));
	}
	
	void broadcastMsg(String chatRoom, Object ack, String skipUid)
	{
		vertx.sharedData().getSet("chat.room." + chatRoom).forEach((chatter)->{
			if (skipUid != null && skipUid.equals(chatter) == true)
				return;
			
			sendMsg((String)chatter, ack);
		});
		
	}
	
	void addCreatureStateToAckMsg(CreatureStateAck stateAck, Creature creature)
	{
		CreatureState state = new CreatureState();
		state.uid = creature.m_uid;
		state.type = creature.m_type;
		state.camp = creature.m_camp;
		state.movingStartTick = creature.m_movingStartTick;
		
		state.spos = new Vector3();
		state.spos.x = creature.m_spos.x;
		state.spos.y = creature.m_spos.y;
		state.spos.z = creature.m_spos.z;
		
		Vector3f cpos = creature.GetCurPos();
		state.cpos = new Vector3();
		state.cpos.x = cpos.x;
		state.cpos.y = cpos.y;
		state.cpos.z = cpos.z;
		
		state.goal = new Vector3();
		state.goal.x = creature.m_goal.x;
		state.goal.y = creature.m_goal.y;
		state.goal.z = creature.m_goal.z;
		
		state.speed = creature.m_speed;
		
		state.death = creature.m_death;
		
		stateAck.creatureStates.add(state);
	}
}
