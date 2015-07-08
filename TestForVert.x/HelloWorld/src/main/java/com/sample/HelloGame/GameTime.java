package com.sample.HelloGame;

public class GameTime {
	
	private static volatile  GameTime ins;
	
	private GameTime() {}
	
	public static GameTime Instance() 
	{			
		synchronized(GameTime.class) 
		{
			if (ins == null)
	        	 ins = new GameTime();
		}
		
     	return ins;
	}
	
	
	private long	m_tick;
	private long	m_deltaTick;
	
	public void SetTick()
	{
		long old = m_tick;
		m_tick = System.currentTimeMillis();
		
		m_deltaTick = m_tick-old;
	}
	
	public float DeltaTime()
	{
		return m_deltaTick/1000f;
	}
	
	public long Tick()
	{
		return m_tick;
	}
}
