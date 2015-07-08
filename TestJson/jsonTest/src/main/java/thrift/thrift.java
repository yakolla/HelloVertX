package thrift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class thrift {	
	private static List<thriftStruct> thriftSturctList;
	public thrift()
	{
		thriftSturctList = new ArrayList<thriftStruct>();
	}
	
	public static List<thriftStruct> getThriftSturctList() {
		return thriftSturctList;
	}

	public static void setThriftSturctList(List<thriftStruct> thriftSturctList) {
		thrift.thriftSturctList = thriftSturctList;
	}

	public void addThrift(thriftStruct addData)
	{
		thriftSturctList.add(addData);
	}
	
	public static void WriteThrift(String fileName, String sheetName) throws IOException {
		////////////////////
		String structName = fileName.replace(".xlsx", "");
		
		fileName = fileName.replace("xlsx", "");
		fileName = fileName + "." + sheetName + ".thrift";
		
		String txt = "namespace java " + structName + "." + sheetName+".thrift \r\n";		
		txt = txt + " struct " + structName + " { \r\n";							
		String total_txt;		
		String struct_txt = null;		
		
		try{
			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
			
			int index = 1;
			for(int n = 0; n <thriftSturctList.size(); n++)
			{			 		
				struct_txt = struct_txt +" " +  index++ + ":" +  thriftSturctList.get(n).getType()  + " " +  thriftSturctList.get(n).getName() + " , \r\n";			 		
			}			 	
			struct_txt = struct_txt.replace("null","");			 	
			total_txt = txt + struct_txt + "\r\n}";			 	
			
			fw.write(total_txt);
			fw.flush();
			fw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
