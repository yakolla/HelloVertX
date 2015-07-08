package com.sample.HelloGame;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class Tower extends Creature {
	
	long		m_fireCoolTick = 2*1000;
	long		m_startFiringTick = 0;
	
	long		m_searchCoolTick = 1*1000;
	long		m_startSearchingTick = 0;
	float		m_searchRadius = 3f;
	
	Creature	m_target;
	
	Tower(String uid, int camp, Vector3f spos)		
	{
		super(uid, "Tower", camp, spos);
		m_speed = 0;
	}
	
	@Override
	public void Update()
	{
		super.Update();
		
		if (m_target == null)
		{
			CreatureManager.Instance().ForEachCreatureAlive((key, creature)->{
				
				if (m_death)
					return;
				
				if (m_target != null)
					return;
				
				if (m_camp != creature.m_camp && (m_searchRadius+creature.m_radius) > Distance(GetCurPos(), creature.GetCurPos()))
				{
					m_target = creature;
				}
			});
		}
		else
		{
			if (m_target.m_death)
			{
				m_target = null;
				return;
			}
			
			if ((m_searchRadius+m_target.m_radius) < Distance(GetCurPos(), m_target.GetCurPos()))
			{
				m_target = null;
				return;
			}		
			
			if (m_startFiringTick+m_fireCoolTick < GameTime.Instance().Tick())
			{
				m_startFiringTick = GameTime.Instance().Tick();
				
				CreatureManager.Instance().CreateBullet(this, NormalizeBetween(m_target.GetCurPos(), GetCurPos()), 5f);
			}
		}
		
	}
}
