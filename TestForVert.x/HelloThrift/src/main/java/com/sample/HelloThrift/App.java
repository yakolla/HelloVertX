package com.sample.HelloThrift;

import java.io.File;

import org.apache.thrift.TBase;

import com.sample.idl.shared.SharedStructV1;
import com.sample.idl.shared.SharedStructV2;
import com.sample.idl.shared.SharedStructV3;
import com.sample.idl.shared.SharedStructV4;
import com.sample.idl.shared.SharedStructV5;
import com.sample.idl.shared.SharedStructV6;
import com.sample.idl.shared.SharedStructV7;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        // write
        SharedStructV1 wV1 = new SharedStructV1();
        wV1.key = 1;
        wV1.name = "test";        
        Write(wV1);
        
        // read and compare
        
        SharedStructV1 rV1 = Read(SharedStructV1.class, SharedStructV1.class);
        System.out.println("동일한 구조체 사용");
        System.out.println(wV1.toString() + " == " + rV1.toString());
        System.out.println(wV1.key == rV1.key && wV1.name.equals(rV1.name));        
        
        
        SharedStructV2 rV2 = Read(SharedStructV1.class, SharedStructV2.class);
        System.out.println("구조체 이름을 변경함");
        System.out.println(wV1.toString() + " == " + rV2.toString());
        System.out.println(wV1.key == rV2.key && wV1.name.equals(rV2.name));
        
        SharedStructV3 rV3 = Read(SharedStructV1.class, SharedStructV3.class);
        System.out.println("필드 순서번호를 변경함");
        System.out.println(wV1.toString() + " == " + rV3.toString());
        System.out.println(wV1.key == rV3.key && wV1.name.equals(rV3.name));
        
        SharedStructV4 rV4 = Read(SharedStructV1.class, SharedStructV4.class);
        System.out.println("필드명을 변경함");
        System.out.println(wV1.toString() + " == " + rV4.toString());
        System.out.println(wV1.key == rV4.key1 && wV1.name.equals(rV4.name1));
        
        SharedStructV5 rV5 = Read(SharedStructV1.class, SharedStructV5.class);
        System.out.println("필드 제거함");
        System.out.println(wV1.toString() + " == " + rV5.toString());
        System.out.println(wV1.key == rV5.key);
        
        SharedStructV6 rV6 = Read(SharedStructV1.class, SharedStructV6.class);
        System.out.println("필드 추가함");
        System.out.println(wV1.toString() + " == " + rV6.toString());
        System.out.println(wV1.key == rV6.key && wV1.name.equals(rV6.name));
        
        SharedStructV7 rV7 = Read(SharedStructV1.class, SharedStructV7.class);
        System.out.println("필드 타입을 변경함");
        System.out.println(wV1.toString() + " == " + rV7.toString());
        System.out.println(wV1.key == rV7.key && wV1.name.equals(rV7.name));
	    
    }
    
   
    
    static <T extends org.apache.thrift.TBase> void  Write(T data)
    {
    	ThriftWriter thriftOut = new ThriftWriter(new File(data.getClass().getName() + ".thrifty"));

	     // Open writer
	     try {
	    	 thriftOut.open();
			 // Write the objects to disk
		     thriftOut.write(data);
		     
		     // Close the writer
		     thriftOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    static <T extends org.apache.thrift.TBase, U extends org.apache.thrift.TBase> U Read(Class<T> fromClass, final Class<U> toClass)
    {
    	
    	ThriftReader thriftIn = new ThriftReader(new File(fromClass.getName() + ".thrifty"), new ThriftReader.TBaseCreator() {
    		@Override
 	       	public TBase create() {
    				try {
    					return toClass.newInstance();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
    				
    				return null;
 	       		}
    		});

 	     // Open it
 	     try {
 	    	 thriftIn.open();

 	    	 U data = (U)thriftIn.read();

 		     // Close reader
 		     thriftIn.close();
 		     
 		     return data;
 	     } catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 		 }

 	     return null;
    }
}
