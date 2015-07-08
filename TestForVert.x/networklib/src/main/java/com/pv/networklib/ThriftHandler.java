package com.pv.networklib;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.TBase;



public class ThriftHandler<ProtocolIdType, SocketType>
{
	
	private interface InnerHandler<T>
	{
		public void DoHandle(T ws, TProtocol tProtocol);
	}
	
	public interface Handler <T, U extends TBase> 
	{
		public void DoHandle(T ws, U data);
	}
	
    Map<ProtocolIdType, InnerHandler<SocketType>> m_handlers = new HashMap<ProtocolIdType, InnerHandler<SocketType>>();

    public <T extends TBase> void AddHandler(final Class<T> tClass, ProtocolIdType pid, final Handler handler)
    {
        m_handlers.put(pid, new InnerHandler<SocketType>(){
        	
	        public void DoHandle(SocketType ws, TProtocol protocol)
	        {    
				try {					
					T ack = tClass.newInstance();
					ack.read(protocol);
			        handler.DoHandle(ws, ack);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	           
	        }}
        );        
       
    }

    public void DoHandle(SocketType ws, ProtocolIdType pid, TProtocol tProtocol) throws Exception
    {
    	
    	InnerHandler<SocketType> handler = m_handlers.get(pid);
        if (null != handler)
        {
            handler.DoHandle(ws, tProtocol);
            return;
        }

        throw new Exception();
    }
}
