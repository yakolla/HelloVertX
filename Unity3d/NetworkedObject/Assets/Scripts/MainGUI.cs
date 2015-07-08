using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System;
using LitJson;
using NetworkLib.WebSocketSharp;


public class MainGUI : MonoBehaviour {


	ConcurrentQueue<string>	m_recvMsgs = new ConcurrentQueue<string> ();
	const string ip = "172.20.42.42";

	Dictionary<Protocol.ID, System.Action<string>>	m_handlers = new Dictionary<Protocol.ID, System.Action<string>>();
	Dictionary<string, Creature>	m_cretures = new Dictionary<string, Creature> ();
	Creature	m_myCreature;
	string		m_myUid;
	int			m_myCamp;

	WebSocket ws = new WebSocket("ws://" + ip + ":10001/chat/1");
	// Use this for initial

	void Start () {



		ws.OnOpen += (sender, e) => {
			Protocol.LoginReq req = new Protocol.LoginReq();
			req.camp = m_myCamp;
			ws.Send(JsonMapper.ToJson(req));
		};

		ws.OnMessage += (sender, e) => {
			m_recvMsgs.Enqueue(e.Data);
		};

		ws.OnError += (object sender, NetworkLib.WebSocketSharp.ErrorEventArgs e) => {
		};

		ws.OnClose += (object sender, CloseEventArgs e) => {
		};


		m_handlers.Add (Protocol.ID.LOGIN, OnLogin);
		m_handlers.Add (Protocol.ID.LOGOUT, OnLogout);
		m_handlers.Add (Protocol.ID.CREATURE_STATE, OnCreatureState);
	}
	
	// Update is called once per frame
	void Update () {
		string rawData = null;

		while (m_recvMsgs.Count > 0) {
			rawData = m_recvMsgs.Dequeue();
			OnRecieveMsg (rawData);
		}

		if (m_myCreature == null)
			return;

		if (Input.GetMouseButtonUp (0)) {
			
			Vector3 v3 = Input.mousePosition;
			v3.z = 10.0f;
			v3 = Camera.main.ScreenToWorldPoint(v3);
			v3.y = 0;

			Protocol.MoveReq req = new Protocol.MoveReq();
			req.goal = v3;

			ws.Send(JsonMapper.ToJson(req));
		}

		if (Input.GetKeyUp (KeyCode.Space)) {

			Vector3 v3 = Input.mousePosition;
			v3.z = 10.0f;
			v3 = Camera.main.ScreenToWorldPoint(v3);
			v3.y = 0;

			Vector3 dir = (v3-m_myCreature.transform.position).normalized;
			Protocol.FireBulletReq req = new Protocol.FireBulletReq();
			req.dir = dir;
			
			ws.Send(JsonMapper.ToJson(req));
		}
	}

	void OnRecieveMsg(string rawData)
	{

		Protocol.ProtocolAck protoBase = JsonMapper.ToObject<Protocol.ProtocolAck>(rawData);
		System.Action<string> handler = null;
		if (m_handlers.TryGetValue (protoBase.header.pid, out handler)) {
			handler(rawData);
		}
	}

	public void OnClickLogin(int camp)
	{
		m_myCamp = camp;
		Security.PrefetchSocketPolicy (ip, 843);
		ws.ConnectAsync ();
	}

	void OnLogin(string rawData)
	{
		Protocol.LoginAck ack = JsonMapper.ToObject<Protocol.LoginAck>(rawData.ToString());

		GameServerTime.Instance.SyncTime(ack.serverTick);
		GameObject obj = Instantiate(Resources.Load("Pref/Champ"), Vector3.zero, Quaternion.Euler(0, 0, 0)) as GameObject;

		Creature creature = obj.GetComponent<Creature> ();
		creature.Init (ack.name, m_myCamp);
		m_cretures.Add(ack.name, creature);

		m_myCreature = creature;
		m_myUid = ack.name;
		transform.Find ("Panel").gameObject.SetActive (false);
	}

	void OnLogout(string rawData)
	{
		Protocol.LogoutAck ack = JsonMapper.ToObject<Protocol.LogoutAck>(rawData.ToString());

		Creature creature = null;
		if (m_cretures.TryGetValue (ack.name, out creature) == true) 
		{
			DestroyObject(creature.gameObject);
			m_cretures.Remove(ack.name);
		}

	}

	void OnCreatureState(string rawData)
	{
		Protocol.CreatureStateAck ack = JsonMapper.ToObject<Protocol.CreatureStateAck>(rawData.ToString());
		float jitter = GameServerTime.Instance.SyncTime(ack.serverTick);
		//Debug.Log ("latency:" + m_logText.text);
		foreach(Protocol.CreatureState state in ack.creatureStates)
		{
			Creature creature = null;
			if (m_cretures.TryGetValue (state.uid, out creature) == false) 
			{
				GameObject obj = Instantiate(Resources.Load("Pref/" + state.type), state.spos, Quaternion.Euler(0, 0, 0)) as GameObject;
				Debug.Log("type:" + state.type + " cpos:" + obj.transform.position);
				creature = obj.GetComponent<Creature> ();
				creature.Init(state.uid, state.camp);
				m_cretures.Add(state.uid, creature);

				if (m_myUid.Equals(state.uid))
					m_myCreature = creature;
			}

			creature.SyncMove(state.spos, state.goal, (float)state.speed, state.movingStartTick);

			if (state.death == true)
			{
				DestroyObject(creature.gameObject);
				m_cretures.Remove(state.uid);
			}
		}
		
	}
}
