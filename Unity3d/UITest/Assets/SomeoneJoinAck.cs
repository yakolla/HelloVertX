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
public partial class SomeoneJoinAck : TBase
{
  private ProtocolAckBase _header;
  private List<string> _names;

  public ProtocolAckBase Header
  {
    get
    {
      return _header;
    }
    set
    {
      __isset.header = true;
      this._header = value;
    }
  }

  public List<string> Names
  {
    get
    {
      return _names;
    }
    set
    {
      __isset.names = true;
      this._names = value;
    }
  }


  public Isset __isset;
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public struct Isset {
    public bool header;
    public bool names;
  }

  public SomeoneJoinAck() {
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
          if (field.Type == TType.Struct) {
            Header = new ProtocolAckBase();
            Header.Read(iprot);
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 2:
          if (field.Type == TType.List) {
            {
              Names = new List<string>();
              TList _list0 = iprot.ReadListBegin();
              for( int _i1 = 0; _i1 < _list0.Count; ++_i1)
              {
                string _elem2;
                _elem2 = iprot.ReadString();
                Names.Add(_elem2);
              }
              iprot.ReadListEnd();
            }
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
    TStruct struc = new TStruct("SomeoneJoinAck");
    oprot.WriteStructBegin(struc);
    TField field = new TField();
    if (Header != null && __isset.header) {
      field.Name = "header";
      field.Type = TType.Struct;
      field.ID = 1;
      oprot.WriteFieldBegin(field);
      Header.Write(oprot);
      oprot.WriteFieldEnd();
    }
    if (Names != null && __isset.names) {
      field.Name = "names";
      field.Type = TType.List;
      field.ID = 2;
      oprot.WriteFieldBegin(field);
      {
        oprot.WriteListBegin(new TList(TType.String, Names.Count));
        foreach (string _iter3 in Names)
        {
          oprot.WriteString(_iter3);
        }
        oprot.WriteListEnd();
      }
      oprot.WriteFieldEnd();
    }
    oprot.WriteFieldStop();
    oprot.WriteStructEnd();
  }

  public override string ToString() {
    StringBuilder __sb = new StringBuilder("SomeoneJoinAck(");
    bool __first = true;
    if (Header != null && __isset.header) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Header: ");
      __sb.Append(Header== null ? "<null>" : Header.ToString());
    }
    if (Names != null && __isset.names) {
      if(!__first) { __sb.Append(", "); }
      __first = false;
      __sb.Append("Names: ");
      __sb.Append(Names);
    }
    __sb.Append(")");
    return __sb.ToString();
  }

}

