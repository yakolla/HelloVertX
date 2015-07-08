namespace java com.sample.HelloGame.idl

enum Protocol
{
	LOGIN = 1,
	MOVE,
	SOMEONE_JOIN,
	CREATURE_STATE,
	FIRE_BULLET,
	LOGOUT,
}


struct ProtocolBase {
  	1: i32 pid
}


struct LoginReq {
	1: ProtocolBase header
  	2: i32 camp
}

struct LoginAck {
	1: ProtocolBase header
	2: string name
	3: i64	serverTick
}

struct LogoutReq {
	1: ProtocolBase header
	2: string name
}

struct LogoutAck {
	1: ProtocolBase header
	2: string name
}

struct Vector3 {
	1: double x
	2: double y
	3: double z
}

struct MoveReq {
	1: ProtocolBase header
	2: Vector3 goal
}

struct FireBulletReq {
	1: ProtocolBase header
	2: Vector3 dir
}

struct CreatureState {
	1: string uid
	2: string type
	3: i32 camp
	4: i64 movingStartTick
	5: Vector3 spos
	6: Vector3 cpos
	7: Vector3 goal
	8: double speed
	9: bool death
}

struct CreatureStateAck {
	1: ProtocolBase header
	2: i64	serverTick
	3: list<CreatureState> creatureStates
}


