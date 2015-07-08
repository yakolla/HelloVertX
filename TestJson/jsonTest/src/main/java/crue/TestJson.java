package crue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;




import com.google.gson.Gson;

import excel.excel;

public class TestJson {
	 public static void main(String[] args) {
		 try{			 
				
			 System.err.println("------------------------");
			 System.exit(-1);
			 if(args.length == 0)
			 {
				 System.err.println("Excel file Name is null!!!!");
			 }
			 else
			 {
				
				 String fileName = args[0];			 
				 System.out.println("TestJson.main() : fileName =" + fileName);
				// excel.OpenExcel(fileName);
				 excel.OpenExcelSheet(fileName, 0);
			 }
		 }
		 catch(Exception e){
				e.printStackTrace();
		}
	 }
}
