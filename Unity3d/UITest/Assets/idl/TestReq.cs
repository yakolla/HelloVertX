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

namespace idl
{

  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class TestReq : TBase
  {
    private Header _header;
    private int _key;
    private string _value;

    public Header Header
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

    public int Key
    {
      get
      {
        return _key;
      }
      set
      {
        __isset.key = true;
        this._key = value;
      }
    }

    public string Value
    {
      get
      {
        return _value;
      }
      set
      {
        __isset.@value = true;
        this._value = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool header;
      public bool key;
      public bool @value;
    }

    public TestReq() {
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
              Header = new Header();
              Header.Read(iprot);
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 2:
            if (field.Type == TType.I32) {
              Key = iprot.ReadI32();
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 3:
            if (field.Type == TType.String) {
              Value = iprot.ReadString();
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
      TStruct struc = new TStruct("TestReq");
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
      if (__isset.key) {
        field.Name = "key";
        field.Type = TType.I32;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteI32(Key);
        oprot.WriteFieldEnd();
      }
      if (Value != null && __isset.@value) {
        field.Name = "value";
        field.Type = TType.String;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(Value);
        oprot.WriteFieldEnd();
      }
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("TestReq(");
      bool __first = true;
      if (Header != null && __isset.header) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Header: ");
        __sb.Append(Header== null ? "<null>" : Header.ToString());
      }
      if (__isset.key) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Key: ");
        __sb.Append(Key);
      }
      if (Value != null && __isset.@value) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("Value: ");
        __sb.Append(Value);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
