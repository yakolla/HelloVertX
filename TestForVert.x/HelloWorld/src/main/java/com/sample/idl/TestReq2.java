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
public class TestReq2 implements org.apache.thrift.TBase<TestReq2, TestReq2._Fields>, java.io.Serializable, Cloneable, Comparable<TestReq2> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TestReq2");

  private static final org.apache.thrift.protocol.TField HEADER_FIELD_DESC = new org.apache.thrift.protocol.TField("header", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("key", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TestReq2StandardSchemeFactory());
    schemes.put(TupleScheme.class, new TestReq2TupleSchemeFactory());
  }

  public Header header; // required
  public int key; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    HEADER((short)1, "header"),
    KEY((short)2, "key");

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
        case 2: // KEY
          return KEY;
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
  private static final int __KEY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HEADER, new org.apache.thrift.meta_data.FieldMetaData("header", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Header.class)));
    tmpMap.put(_Fields.KEY, new org.apache.thrift.meta_data.FieldMetaData("key", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TestReq2.class, metaDataMap);
  }

  public TestReq2() {
  }

  public TestReq2(
    Header header,
    int key)
  {
    this();
    this.header = header;
    this.key = key;
    setKeyIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TestReq2(TestReq2 other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetHeader()) {
      this.header = new Header(other.header);
    }
    this.key = other.key;
  }

  public TestReq2 deepCopy() {
    return new TestReq2(this);
  }

  @Override
  public void clear() {
    this.header = null;
    setKeyIsSet(false);
    this.key = 0;
  }

  public Header getHeader() {
    return this.header;
  }

  public TestReq2 setHeader(Header header) {
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

  public int getKey() {
    return this.key;
  }

  public TestReq2 setKey(int key) {
    this.key = key;
    setKeyIsSet(true);
    return this;
  }

  public void unsetKey() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __KEY_ISSET_ID);
  }

  /** Returns true if field key is set (has been assigned a value) and false otherwise */
  public boolean isSetKey() {
    return EncodingUtils.testBit(__isset_bitfield, __KEY_ISSET_ID);
  }

  public void setKeyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __KEY_ISSET_ID, value);
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

    case KEY:
      if (value == null) {
        unsetKey();
      } else {
        setKey((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case HEADER:
      return getHeader();

    case KEY:
      return Integer.valueOf(getKey());

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
    case KEY:
      return isSetKey();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TestReq2)
      return this.equals((TestReq2)that);
    return false;
  }

  public boolean equals(TestReq2 that) {
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

    boolean this_present_key = true;
    boolean that_present_key = true;
    if (this_present_key || that_present_key) {
      if (!(this_present_key && that_present_key))
        return false;
      if (this.key != that.key)
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

    boolean present_key = true;
    list.add(present_key);
    if (present_key)
      list.add(key);

    return list.hashCode();
  }

  @Override
  public int compareTo(TestReq2 other) {
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
    lastComparison = Boolean.valueOf(isSetKey()).compareTo(other.isSetKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key, other.key);
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
    StringBuilder sb = new StringBuilder("TestReq2(");
    boolean first = true;

    sb.append("header:");
    if (this.header == null) {
      sb.append("null");
    } else {
      sb.append(this.header);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("key:");
    sb.append(this.key);
    first = false;
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TestReq2StandardSchemeFactory implements SchemeFactory {
    public TestReq2StandardScheme getScheme() {
      return new TestReq2StandardScheme();
    }
  }

  private static class TestReq2StandardScheme extends StandardScheme<TestReq2> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TestReq2 struct) throws org.apache.thrift.TException {
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
          case 2: // KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.key = iprot.readI32();
              struct.setKeyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TestReq2 struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.header != null) {
        oprot.writeFieldBegin(HEADER_FIELD_DESC);
        struct.header.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(KEY_FIELD_DESC);
      oprot.writeI32(struct.key);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TestReq2TupleSchemeFactory implements SchemeFactory {
    public TestReq2TupleScheme getScheme() {
      return new TestReq2TupleScheme();
    }
  }

  private static class TestReq2TupleScheme extends TupleScheme<TestReq2> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TestReq2 struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetHeader()) {
        optionals.set(0);
      }
      if (struct.isSetKey()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetHeader()) {
        struct.header.write(oprot);
      }
      if (struct.isSetKey()) {
        oprot.writeI32(struct.key);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TestReq2 struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.header = new Header();
        struct.header.read(iprot);
        struct.setHeaderIsSet(true);
      }
      if (incoming.get(1)) {
        struct.key = iprot.readI32();
        struct.setKeyIsSet(true);
      }
    }
  }

}

