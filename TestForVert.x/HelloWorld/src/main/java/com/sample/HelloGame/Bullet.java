package com.sample.HelloGame;

import java.util.UUID;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class Bullet extends Creature {
	
	
	
	Bullet(Creature shooter, String uid, Vector3f spos, Vector3f dir, float length)		
	{
		super(uid, "Bullet", shooter.m_camp, spos);
		m_radius = 0.5f;
		m_speed = 1.5f;
		dir.scale(length);
		dir.add(spos);
		Move(dir);
	}
	
	@Override
	public void Update()
	{
		super.Update();
		
		CreatureManager.Instance().ForEachCreatureAlive((key, creature)->{
			
			if (m_death)
				return;
			
			if (m_camp != creature.m_camp && (m_radius+creature.m_radius) > Distance(GetCurPos(), creature.GetCurPos()))
			{
				creature.Death();
				Death();
			}
		});
		
		if (0f == Distance(GetCurPos(), m_goal))
		{
			Death();
		}
	}
	
}
