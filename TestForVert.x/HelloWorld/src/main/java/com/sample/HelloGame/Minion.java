package com.sample.HelloGame;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class Minion extends Creature {
	
	Minion(String uid, int camp, Vector3f spos)		
	{
		super(uid, "Minion", camp, spos);
	}
	
	@Override
	public void Update()
	{
		if (0 == Distance(m_goal, GetCurPos()))
		{
			Move(new Vector3f(-5f+(float)Math.random()*10, 0f, -5f+(float)Math.random()*10));
		}
		
		super.Update();
		
	}
}
