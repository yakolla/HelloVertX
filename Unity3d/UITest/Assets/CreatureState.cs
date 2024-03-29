/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;


#if !SILVERLIGHT
[Serializable]
#endif
public partial class CreatureState : TBase
{
  private string _uid;
  private string _type;
  private int _camp;
  private long _movingStartTick;
  private Vector3 _spos;
  private Vector3 _cpos;
  private Vector3 _goal;
  private double _speed;
  private bool _death;

  public string Uid
  {
    get
    {
      return _uid;
    }
    set
    {
      __isset.uid = true;
      this._uid = value;
    }
  }

  public string Type
  {
    get
    {
      return _type;
    }
    set
    {
      __isset.type = true;
      this._type = value;
    }
  }

  public int Camp
  {
    get
    {
      return _camp;
    }
    set
    {
      __isset.camp = true;
      this._camp = value;
    }
  }

  public long MovingStartTick
  {
    get
    {
      return _movingStartTick;
    }
    set
    {
      __isset.movingStartTick = true;
      this._movingStartTick = value;
    }
  }

  public Vector3 Spos
  {
    get
    {
      return _spos;
    }
    set
    {
      __isset.spos = true;
      this._spos = value;
    }
  }

  public Vector3 Cpos
  {
    get
    {
      return _cpos;
    }
    set
    {
      __isset.cpos = true;
      this._cpos = value;
    }
  }

  public Vector3 Goal
  {
    get
    {
      return _goal;
    }
    set
    {
      __isset.goal = true;
      this._goal = value;
    }
  }

  public double Speed
  {
    get
    {
      return _speed;
    }
    set
    {
      __isset.speed = true;
      this._speed = value;
    }
  }

  public bool Death
  {
    get
    {
      return _death;
    }
    set
    {
      __isset.death = true;
      this._death = value;
    }
  }


  public Isset __isset;
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public struct Isset {
    public bool uid;
    public bool type;
    public bool camp;
    public bool movingStartTick;
    public bool spos;
    public bool cpos;
    public bool goal;
    public bool speed;
    public bool death;
  }

  public CreatureState() {
  }

  public void Read (TProtocol iprot)
  {
    TField field;
    iprot.ReadStructBegin();
    while (true)
    {
      field = iprot.ReadFieldBegin();
      if (field.Type == TType.Stop) { 
        break;
      }
      switch (field.ID)
      {
        case 1:
          if (field.Type == TType.String) {
            Uid = iprot.ReadString();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 2:
          if (field.Type == TType.String) {
            Type = iprot.ReadString();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 3:
          if (field.Type == TType.I32) {
            Camp = iprot.ReadI32();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 4:
          if (field.Type == TType.I64) {
            MovingStartTick = iprot.ReadI64();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 5:
          if (field.Type == TType.Struct) {
            Spos = new Vector3();
            Spos.Read(iprot);
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 6:
          if (field.Type == TType.Struct) {
            Cpos = new Vector3();
            Cpos.Read(iprot);
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 7:
          if (field.Type == TType.Struct) {
            Goal = new Vector3();
            Goal.Read(iprot);
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 8:
          if (field.Type == TType.Double) {
            Speed = iprot.ReadDouble();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 9:
          if (field.Type == TType.Bool) {
            Death = iprot.ReadBool();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        default: 
          TProtocolUtil.Skip(iprot, field.Type);
          break;
      }
      iprot.ReadFieldEnd();
    }
    iprot.ReadStructEnd();
  }

  public void Write(TProtocol oprot) {
    TStruct struc = new TStruct("CreatureState");
    oprot.WriteStructBegin(struc);
    TField field = new TField();
    if (Uid != null && __isset.uid) {
      field.Name = "uid";
      field.Type = TType.String;
      field.ID = 1;
      oprot.WriteFieldBegin(field);
      oprot.WriteString(Uid);
      oprot.WriteFieldEnd();
    }
    if (Type != null && __isset.type) {
      field.Name = "type";
      field.Type = TType.String;
      field.ID = 2;
      oprot.WriteFieldBegin(field);
      oprot.WriteString(Type);
      oprot.WriteFieldEnd();
    }
    if (__isset.camp) {
      field.Name = "camp";
      field.Type = TType.I32;
      field.ID = 3;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(Camp);
      oprot.WriteFieldEnd();
    }
    if (__isset.movingStartTick) {
      field.Name = "movingStartTick";
      field.Type = TType.I64;
      field.ID = 4;
      oprot.WriteFieldBegin(field);
      oprot.WriteI64(MovingStartTick);
      oprot.WriteFieldEnd();
    }
    if (Spos != null && __isset.spos) {
      field.Name = "spos";
      field.Type = TType.Struct;
      field.ID = 5;
      oprot.WriteFieldBegin(field);
      Spos.Write(oprot);
      oprot.WriteFieldEnd();
    }
    if (Cpos != null && __isset.cpos) {
      field.Name = "cpos";
      field.Type = TType.Struct;
      field.ID = 6;
      oprot.WriteFieldBegin(field);
      Cpos.Write(oprot);
      oprot.WriteFieldEnd();
    }
    if (Goal != null && __isset.goal) {
      field.Name = "goal";
      field.Type = TType.Struct;
      field.ID = 7;
      oprot.WriteFieldBegin(field);
      Goal.Write(oprot);
      oprot.WriteFieldEnd();
    }
    if (__isset.speed) {
      field.Name = "speed";
      field.Type = TType.Double;
      field.ID = 8;
      oprot.WriteFieldBegin(field);
      oprot.WriteDouble(Speed);
      oprot.WriteFieldEnd();
    }
    if (__isset.death) {
      field.Name = "death";
      field.Type = TType.Bool;
      field.ID = 9;
      oprot.WriteFieldBegin(field);
      oprot.WriteBool(Death);
      oprot.WriteFieldEnd();
    }
    oprot.WriteFieldStop();
    oprot.WriteStructEnd();
  }

  public override string ToString() {
    StringBuilder __sb = new StringBuilder("CreatureState(");
    bool __first = true;
    if (Uid != null && __isset.uid) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Uid: ");
      __sb.Append(Uid);
    }
    if (Type != null && __isset.type) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Type: ");
      __sb.Append(Type);
    }
    if (__isset.camp) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Camp: ");
      __sb.Append(Camp);
    }
    if (__isset.movingStartTick) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("MovingStartTick: ");
      __sb.Append(MovingStartTick);
    }
    if (Spos != null && __isset.spos) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Spos: ");
      __sb.Append(Spos== null ? "<null>" : Spos.ToString());
    }
    if (Cpos != null && __isset.cpos) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Cpos: ");
      __sb.Append(Cpos== null ? "<null>" : Cpos.ToString());
    }
    if (Goal != null && __isset.goal) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Goal: ");
      __sb.Append(Goal== null ? "<null>" : Goal.ToString());
    }
    if (__isset.speed) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Speed: ");
      __sb.Append(Speed);
    }
    if (__isset.death) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Death: ");
      __sb.Append(Death);
    }
    __sb.Append(")");
    return __sb.ToString();
  }

}

