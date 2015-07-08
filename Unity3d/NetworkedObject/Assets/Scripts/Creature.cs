using UnityEngine;
using System.Collections;

public class Creature : MonoBehaviour {

	Vector3 m_spos = Vector3.zero;
	Vector3	m_goal = Vector3.zero;
	float m_speed = 1f;
	long m_movingStartTick;
	void Start () {
	}

	public void Init(string uid, int camp)
	{
		switch (camp) {
		case 0:
			GetComponent<Renderer>().material.color = Color.blue;
			break;
		case 1:
			GetComponent<Renderer>().material.color = Color.red;
			break;
		}
	}
	
	public void SyncMove (Vector3 spos, Vector3 goal, float speed, long movingStartTick) 
	{
		if (m_movingStartTick != movingStartTick) 
		{
			Debug.Log (" pos:"+spos + " goal:" + goal);

			m_spos = spos;
			m_goal = goal;
			m_movingStartTick = movingStartTick;
		}

		m_speed = speed;
	}

	void Update()
	{
		transform.position = Vector3.MoveTowards (m_spos, m_goal, m_speed*((GameServerTime.Instance.ServerTick - m_movingStartTick) / 1000f));

		//Debug.Log ("type:" + name + " pos:" + transform.position);
	}

}
