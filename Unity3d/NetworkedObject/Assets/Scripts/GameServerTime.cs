using System;
using UnityEngine;

public class GameServerTime
{
	static GameServerTime _ins;
	long m_serverTick;
	float m_clientTime;
	float m_jitter;
	float m_deltaTime;

	private GameServerTime ()
	{

	}

	static public GameServerTime Instance
	{
		get{
			if (_ins == null)
				_ins = new GameServerTime ();

			return _ins;
		}
	}

	public long	ServerTick
	{
		get{
			return m_serverTick + (long)((Time.time - m_clientTime)*1000f);
			//return m_serverTick;
		}
	}

	public float DeltaTime
	{
		get{
			return m_deltaTime;
		}
	}

	public float SyncTime(long tick)
	{
		long oldTick = ServerTick;

		m_jitter = ((tick - m_serverTick) / 1000f) - (Time.time - m_clientTime);

		m_serverTick = tick;
		m_clientTime = Time.time;

		m_deltaTime = (ServerTick - oldTick)/1000f;

		return m_jitter;
	}
}

