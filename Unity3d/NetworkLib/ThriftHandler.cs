using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Text;
using System.Net;
using System.IO;

namespace NetworkLib
{
#if THRIFT
    public class ThriftHandler<ProtocolIdType>
    {
        Dictionary<ProtocolIdType, System.Action<Thrift.Protocol.TProtocol>> m_handlers = new Dictionary<ProtocolIdType, System.Action<Thrift.Protocol.TProtocol>>();

        public void AddHandler<T>(ProtocolIdType pid, System.Action<T> handler) where T : Thrift.Protocol.TBase
        {
            m_handlers.Add(pid, (Thrift.Protocol.TProtocol protocol) =>
            {
                T ack = System.Activator.CreateInstance<T>();
                ack.Read(protocol);
                handler(ack);
            });
        }

        public void DoHandle(ProtocolIdType pid, Thrift.Protocol.TProtocol tProtocol)
        {
            System.Action<Thrift.Protocol.TProtocol> handler = null;
            if (true == m_handlers.TryGetValue(pid, out handler))
            {
                handler(tProtocol);
                return;
            }

            throw new ArgumentException();
        }
    }
#endif
}

