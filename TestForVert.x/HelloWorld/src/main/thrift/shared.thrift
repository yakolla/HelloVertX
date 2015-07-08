/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * This Thrift file can be included by other Thrift files that want to share
 * these definitions.
 */

namespace java com.sample.idl
namespace csharp idl

enum Protocol
{
	Test1 = 1,
	Test2,
}

struct Header {
  	1: Protocol key
  	2: byte ok
}

struct TestReq {
	1: Header header
  	2: i32 key
  	3: string value
}

struct TestAck {
	1: Header header	
	2: bool ivalue
	3: optional string value
}

struct TestReq2 {
	1: Header header
  	2: i32 key
}

struct Test2Ack {
	1: Header header
	2: optional map<string, string> container
}

service MultiplicationService
{
        i32 multiply(1:i32 n1, 2:i32 n2),
}
