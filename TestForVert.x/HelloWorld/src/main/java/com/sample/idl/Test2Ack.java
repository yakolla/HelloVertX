/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sample.idl;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-7-24")
public class Test2Ack implements org.apache.thrift.TBase<Test2Ack, Test2Ack._Fields>, java.io.Serializable, Cloneable, Comparable<Test2Ack> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Test2Ack");

  private static final org.apache.thrift.protocol.TField HEADER_FIELD_DESC = new org.apache.thrift.protocol.TField("header", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField CONTAINER_FIELD_DESC = new org.apache.thrift.protocol.TField("container", org.apache.thrift.protocol.TType.MAP, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new Test2AckStandardSchemeFactory());
    schemes.put(TupleScheme.class, new Test2AckTupleSchemeFactory());
  }

  public Header header; // required
  public Map<String,String> container; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    HEADER((short)1, "header"),
    CONTAINER((short)2, "container");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // HEADER
          return HEADER;
        case 2: // CONTAINER
          return CONTAINER;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.CONTAINER};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HEADER, new org.apache.thrift.meta_data.FieldMetaData("header", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Header.class)));
    tmpMap.put(_Fields.CONTAINER, new org.apache.thrift.meta_data.FieldMetaData("container", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Test2Ack.class, metaDataMap);
  }

  public Test2Ack() {
  }

  public Test2Ack(
    Header header)
  {
    this();
    this.header = header;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Test2Ack(Test2Ack other) {
    if (other.isSetHeader()) {
      this.header = new Header(other.header);
    }
    if (other.isSetContainer()) {
      Map<String,String> __this__container = new HashMap<String,String>(other.container);
      this.container = __this__container;
    }
  }

  public Test2Ack deepCopy() {
    return new Test2Ack(this);
  }

  @Override
  public void clear() {
    this.header = null;
    this.container = null;
  }

  public Header getHeader() {
    return this.header;
  }

  public Test2Ack setHeader(Header header) {
    this.header = header;
    return this;
  }

  public void unsetHeader() {
    this.header = null;
  }

  /** Returns true if field header is set (has been assigned a value) and false otherwise */
  public boolean isSetHeader() {
    return this.header != null;
  }

  public void setHeaderIsSet(boolean value) {
    if (!value) {
      this.header = null;
    }
  }

  public int getContainerSize() {
    return (this.container == null) ? 0 : this.container.size();
  }

  public void putToContainer(String key, String val) {
    if (this.container == null) {
      this.container = new HashMap<String,String>();
    }
    this.container.put(key, val);
  }

  public Map<String,String> getContainer() {
    return this.container;
  }

  public Test2Ack setContainer(Map<String,String> container) {
    this.container = container;
    return this;
  }

  public void unsetContainer() {
    this.container = null;
  }

  /** Returns true if field container is set (has been assigned a value) and false otherwise */
  public boolean isSetContainer() {
    return this.container != null;
  }

  public void setContainerIsSet(boolean value) {
    if (!value) {
      this.container = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case HEADER:
      if (value == null) {
        unsetHeader();
      } else {
        setHeader((Header)value);
      }
      break;

    case CONTAINER:
      if (value == null) {
        unsetContainer();
      } else {
        setContainer((Map<String,String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case HEADER:
      return getHeader();

    case CONTAINER:
      return getContainer();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case HEADER:
      return isSetHeader();
    case CONTAINER:
      return isSetContainer();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Test2Ack)
      return this.equals((Test2Ack)that);
    return false;
  }

  public boolean equals(Test2Ack that) {
    if (that == null)
      return false;

    boolean this_present_header = true && this.isSetHeader();
    boolean that_present_header = true && that.isSetHeader();
    if (this_present_header || that_present_header) {
      if (!(this_present_header && that_present_header))
        return false;
      if (!this.header.equals(that.header))
        return false;
    }

    boolean this_present_container = true && this.isSetContainer();
    boolean that_present_container = true && that.isSetContainer();
    if (this_present_container || that_present_container) {
      if (!(this_present_container && that_present_container))
        return false;
      if (!this.container.equals(that.container))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_header = true && (isSetHeader());
    list.add(present_header);
    if (present_header)
      list.add(header);

    boolean present_container = true && (isSetContainer());
    list.add(present_container);
    if (present_container)
      list.add(container);

    return list.hashCode();
  }

  @Override
  public int compareTo(Test2Ack other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetHeader()).compareTo(other.isSetHeader());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHeader()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.header, other.header);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContainer()).compareTo(other.isSetContainer());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContainer()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.container, other.container);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Test2Ack(");
    boolean first = true;

    sb.append("header:");
    if (this.header == null) {
      sb.append("null");
    } else {
      sb.append(this.header);
    }
    first = false;
    if (isSetContainer()) {
      if (!first) sb.append(", ");
      sb.append("container:");
      if (this.container == null) {
        sb.append("null");
      } else {
        sb.append(this.container);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (header != null) {
      header.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class Test2AckStandardSchemeFactory implements SchemeFactory {
    public Test2AckStandardScheme getScheme() {
      return new Test2AckStandardScheme();
    }
  }

  private static class Test2AckStandardScheme extends StandardScheme<Test2Ack> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Test2Ack struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // HEADER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.header = new Header();
              struct.header.read(iprot);
              struct.setHeaderIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CONTAINER
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                struct.container = new HashMap<String,String>(2*_map0.size);
                String _key1;
                String _val2;
                for (int _i3 = 0; _i3 < _map0.size; ++_i3)
                {
                  _key1 = iprot.readString();
                  _val2 = iprot.readString();
                  struct.container.put(_key1, _val2);
                }
                iprot.readMapEnd();
              }
              struct.setContainerIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Test2Ack struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.header != null) {
        oprot.writeFieldBegin(HEADER_FIELD_DESC);
        struct.header.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.container != null) {
        if (struct.isSetContainer()) {
          oprot.writeFieldBegin(CONTAINER_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.container.size()));
            for (Map.Entry<String, String> _iter4 : struct.container.entrySet())
            {
              oprot.writeString(_iter4.getKey());
              oprot.writeString(_iter4.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class Test2AckTupleSchemeFactory implements SchemeFactory {
    public Test2AckTupleScheme getScheme() {
      return new Test2AckTupleScheme();
    }
  }

  private static class Test2AckTupleScheme extends TupleScheme<Test2Ack> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Test2Ack struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetHeader()) {
        optionals.set(0);
      }
      if (struct.isSetContainer()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetHeader()) {
        struct.header.write(oprot);
      }
      if (struct.isSetContainer()) {
        {
          oprot.writeI32(struct.container.size());
          for (Map.Entry<String, String> _iter5 : struct.container.entrySet())
          {
            oprot.writeString(_iter5.getKey());
            oprot.writeString(_iter5.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Test2Ack struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.header = new Header();
        struct.header.read(iprot);
        struct.setHeaderIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map6 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.container = new HashMap<String,String>(2*_map6.size);
          String _key7;
          String _val8;
          for (int _i9 = 0; _i9 < _map6.size; ++_i9)
          {
            _key7 = iprot.readString();
            _val8 = iprot.readString();
            struct.container.put(_key7, _val8);
          }
        }
        struct.setContainerIsSet(true);
      }
    }
  }

}

