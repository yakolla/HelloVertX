package com.sample.HelloGame;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;

import javax.vecmath.Vector3f;

public class CreatureManager {
	
	private static volatile  CreatureManager ins;
	
	private CreatureManager() {}
	
	public static CreatureManager Instance() 
	{			
		synchronized(CreatureManager.class) 
		{
			if (ins == null)
	        	 ins = new CreatureManager();
		}
		
     	return ins;
	}
	
	
	HashMap< String , Creature > m_creatures = new HashMap< String , Creature >();
	HashMap< String , Creature > m_bullets = new HashMap< String , Creature >();
	
	public void CreateBullet(Creature owner, Vector3f dir, float length)
	{
		String bulletUid = UUID.randomUUID().toString();
		Bullet bullet = new Bullet(owner, bulletUid, owner.GetCurPos(), dir, length);
		m_bullets.put(bulletUid, bullet);
	}
	
	public void ForEachCreatureAlive(BiConsumer<String,Creature> action)
	{
		m_creatures.forEach((key, creature)->{
			if (creature.m_death)
				return;
			
			action.accept(key, creature);
		});
	}
}
