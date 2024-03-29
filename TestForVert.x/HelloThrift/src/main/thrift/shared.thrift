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

namespace cpp shared
namespace d share // "shared" would collide with the eponymous D keyword.
namespace java com.sample.idl.shared
namespace perl shared
namespace php shared

struct SharedStructV1 {
  1: i32 key
  2: string name
}

struct SharedStructV2 {
  1: i32 key
  2: string name
}

struct SharedStructV3 {
  1: i32 key
  3: string name
}

struct SharedStructV4 {
  1: i32 key1
  2: string name1
}

struct SharedStructV5 {
  1: i32 key
}

struct SharedStructV6 {
  1: i32 key
  2: string name
  3: string name1
}

struct SharedStructV7 {
  1: string name
  2: i32 key
}