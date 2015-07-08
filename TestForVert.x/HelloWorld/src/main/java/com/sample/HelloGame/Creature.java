package com.sample.HelloGame;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class Creature {
	Vector3f	m_goal;
	Vector3f	m_spos = new Vector3f();
	float		m_speed = 1f;
	float		m_radius = 1f;
	String		m_uid;
	String		m_type;
	int			m_camp;
	private long		m_revivalTime = 5*1000;
	private long		m_revivalFinishTick = 0;
	private float		m_movingElapsed;
	long		m_movingStartTick;
	boolean		m_death = false;
	private Vector3f m_pos;
	
	Creature(String uid, String type, int camp, Vector3f spos)
	{
		m_uid = uid;
		m_type = type;
		m_camp = camp;
		m_goal = spos;
		m_pos = spos;
		m_spos = spos;

		m_movingStartTick = GameTime.Instance().Tick();
	}
	
	public void Move(Vector3f goal)
	{		
		
		m_spos = GetCurPos();
		m_goal = goal;
		m_movingElapsed = 0f;
		m_movingStartTick = GameTime.Instance().Tick();
		
		System.out.println("id: " + m_uid + " " + m_type + " m_spos:" + m_spos + " goal: " + goal);
	}
	
	public void Update()
	{
		if (m_death == true)
		{
			if (GameTime.Instance().Tick() > m_revivalFinishTick)
				Revival();
			
			return;
		}		
		
		m_pos = MoveTowards(m_spos, m_goal, m_speed*((GameTime.Instance().Tick()-m_movingStartTick)/1000f));
		
		//System.out.println("id: " + m_uid + " " + m_type + " pos: " + m_pos);
	}
	
	Vector3f GetCurPos()
	{
		return m_pos;
	}
	
	public void Death()
	{
		m_death = true;
		Move(new Vector3f(0, 0, 0));
		m_revivalFinishTick = GameTime.Instance().Tick() + m_revivalTime;
		System.out.println("Death id: " + m_uid + " " + m_type + " goal:" + m_goal);
	}
	
	public void Revival()
	{
		m_death = false;
	}
	
	public static Vector3f MoveTowards (Vector3f current, Vector3f target, float maxDistanceDelta)
	{	
		Vector3f a = new Vector3f(target);
		a.sub(current);
		
		float magnitude = a.length();
		if (magnitude <= maxDistanceDelta || magnitude == 0f)
		{
			return target;
		}
		
		a.scale(1/magnitude*maxDistanceDelta);
		a.add(current);
		return a;
	}
	
	public static float Distance(Vector3f from, Vector3f to)
	{
		Vector3f a = new Vector3f(from);
		a.sub(to);
		
		return a.length();
	}
	
	public static Vector3f NormalizeBetween(Vector3f from, Vector3f to)
	{
		Vector3f a = new Vector3f(from);
		a.sub(to);
		
		a.normalize();
		
		return a;
	}
	
}
