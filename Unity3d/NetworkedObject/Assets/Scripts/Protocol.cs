using System;
namespace Protocol
{
	public enum ID
	{
		LOGIN = 1,
		MOVE,
		SOMEONE_JOIN,
		CREATURE_STATE,
		FIRE_BULLET,
		LOGOUT,
	}

	public class ProtocolBase
	{
		public ID	pid;
		public ProtocolBase(){
		}

		public ProtocolBase(ID id)
		{
			pid = id;
		}
	}

	public class ProtocolAck
	{
		public ProtocolBase header;
	}

	public class LoginReq{

		public ProtocolBase header = new ProtocolBase(ID.LOGIN);
		public int			camp;
		public LoginReq()
		{

		}
	}

	public class LoginAck  {
		public ProtocolBase header;
		public string 	name;
		public long		serverTick;
		public LoginAck()
		{
		}
	}

	public class LogoutAck  {
		public ProtocolBase header;
		public string 	name;
		public LogoutAck()
		{
		}
	}

	public class Vector33
	{
		public double x;
		public double y;
		public double z;

		public static implicit  operator UnityEngine.Vector3(Vector33 v)  // explicit byte to digit conversion operator
		{
			UnityEngine.Vector3 d = new UnityEngine.Vector3();  // explicit conversion
			d.x = (float)v.x;
			d.y = (float)v.y;
			d.z = (float)v.z;

			return d;
		}

		public static implicit  operator Vector33(UnityEngine.Vector3 v)  // explicit byte to digit conversion operator
		{
			Vector33 d = new Vector33();  // explicit conversion
			d.x = v.x;
			d.y = v.y;
			d.z = v.z;
			
			return d;
		}

		public Vector33()
		{
		}
	}

	public class MoveReq{
		public ProtocolBase header = new ProtocolBase(ID.MOVE);
		public Vector33	goal;

		public MoveReq()
		{
		}
	}

	public class  FireBulletReq {
		public ProtocolBase header = new ProtocolBase(ID.FIRE_BULLET);
		public Vector33 dir;

		public FireBulletReq()
		{
		}
	}

	public class CreatureState {
		public string uid;
		public string type;
		public int camp;
		public long movingStartTick;
		public Vector33 spos;
		public Vector33 cpos;
		public Vector33 goal;
		public double	speed;
		public bool		death;
	}
	
	struct CreatureStateAck {
		public ProtocolBase header;
		public long		serverTick;
		public CreatureState[] creatureStates;
	}
}

