package excel;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import thrift.thrift;
import thrift.thriftStruct;

public class excel {
	
	public static int thriftData(XSSFSheet sheet, String fileName, CellInfo cellInfo) throws IOException{	
		try{
			int startNum = 0;			
			String sheetName = sheet.getSheetName();			
			int startRowNum = sheet.getFirstRowNum();
			int rowCountNum = sheet.getLastRowNum();			
			XSSFRow row = sheet.getRow(startRowNum);	// row		
			int cellStart = row.getFirstCellNum();    
			int cellCount = row.getLastCellNum();		
			
			boolean checkflag = false;
			for(int tt = startRowNum; tt < rowCountNum; tt++)
			{
				String check;
				XSSFRow checkRow = sheet.getRow(tt); //
				if(checkflag == true)
					break;			
				if(checkRow == null)
					continue;			
				int cellStartNum = checkRow.getFirstCellNum();    
				int cellCountNum = checkRow.getLastCellNum();
				for(int kk = cellStartNum; kk <  cellCountNum; kk ++ )
				{				
					XSSFCell checkCell = checkRow.getCell(kk);
					if(checkCell == null)
						continue;
					
					switch(checkCell.getCellType()){
					case Cell.CELL_TYPE_STRING:
						check = checkCell.getStringCellValue();
						if(check.equals("$key"))
						{
							startRowNum = checkRow.getRowNum();
							checkflag = true;
							break;
						}
						break;				
					}							
				}						
			}		
			if(checkflag == false)
			{							
				System.err.println("[filename : " + fileName + " / sheet Name :"+ sheetName+ "]  start point Error!!!!!!!");
				return -1;
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			row = sheet.getRow(startRowNum);	// row			
			cellStart = row.getFirstCellNum();    
			cellCount = row.getLastCellNum();
			XSSFRow row1 = sheet.getRow(startRowNum + 1);
			
			thrift thr = new thrift();
			//////////////////////////////////////////////////////////////////////////////////////////////////////////
			// thrift  file write
			for(int i = cellStart; i < cellCount; i++)
			{
				String name;
				String type = null;	
				
				XSSFCell cell = row.getCell(i);
				if(cell == null)
				{
					System.out.println("teste");
					return -1;
				}
				name = cell.getStringCellValue();				
				if(name.equals("$key"))
				{
					startNum = cell.getColumnIndex();
					name = name.replace("$","");
				}				
				XSSFCell cell2 = row1.getCell(i);			
				switch(cell2.getCellType()){
						case Cell.CELL_TYPE_STRING:
						type = cell2.getStringCellValue();
						break;				
					}
					if(type == null)
					continue;			
					
					if(name.contains("[]"))
					{
					type = "list<" + type + ">";
					name = 	name.replace("[]","");				
				}		
					
				thriftStruct temp = new thriftStruct(type,name);
				thr.addThrift(temp);
				
				if(cellInfo.GetCellNameMapSize() > 0)
				{				
					if(cellInfo.CheckName(name))
					{
						System.err.println("[filename : " + fileName + " / sheet Name :"+ sheetName+ "] name !!!!!!");
						return -2;
					}
				}
				cellInfo.AddCellNameMap(name);		
				
			}	
			
			thr.WriteThrift(fileName, sheetName);
			return startRowNum;
		}  
		catch (Exception e)
	    {
	        e.printStackTrace();
	    }
		return 0;	
		
	}
	

	public static void OpenExcel(String filename)
	{
		try{
		
			System.out.print("excel.ReadExcel(): fileName =" + filename +"\n");		
			InputStream ExcelFileToRead = new FileInputStream(filename);
			XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
			int sheetCount = wb.getNumberOfSheets();	
			System.out.print(sheetCount + "\n");
			
			for( int i= 0; i < sheetCount; i++)
			{
				XSSFSheet sheet = wb.getSheetAt(i);
				ReadExcel(sheet, filename);
			}
		}
		catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	//uint test 
	public static void OpenExcelSheet(String filename, int sheetNo)
	{	
		try{
			System.out.print("excel.ReadExcel(): fileName =" + filename +"\n");		
			InputStream ExcelFileToRead = new FileInputStream(filename);
			XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
			int sheetCount = wb.getNumberOfSheets();	
			System.out.print(sheetCount + "\n");
			
			if(sheetNo < sheetCount)
			{
				XSSFSheet sheet = wb.getSheetAt(sheetNo);
				ReadExcel(sheet, filename);
			}
		}
		catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	public static void ReadExcel(XSSFSheet sheet, String filename)  {	
		
		try{		
			String 	sheetName = sheet.getSheetName();		
			CellInfo cellInfo = new CellInfo();				
			int startRowNum ;
			startRowNum = thriftData(sheet, filename, cellInfo);		
			if(startRowNum <= 0)
			{
				System.err.println("[filename : " + filename + " / sheet Name :"+ sheetName+ "]:start point error !!!!");				
				return ;
			}
				
			XSSFRow row = sheet.getRow(startRowNum);		
			int cellStart = row.getFirstCellNum();    
			int cellCount = row.getLastCellNum();
			
			row = sheet.getRow(startRowNum);		
			cellStart = row.getFirstCellNum();    
			cellCount = row.getLastCellNum();
			XSSFRow row1 = sheet.getRow(startRowNum + 1);
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//json 
			int rowCount = sheet.getLastRowNum();	
			// cell merge check
				
			cellInfo.MakeMergeList(sheet, startRowNum);		
		
			System.out.print("RowCount=" + rowCount + "\n");
			String jsonString = null;		
			jsonString = "[";		
			
			// n 변경
			for(int n = startRowNum+2; n <= rowCount; n++)
			{
				String dataName = null;
				String dataValue = null;	
				int temp =0;	
				
				//data
				XSSFRow rowJson = sheet.getRow(n); //
				for(int m =cellStart; m < cellCount; m++ )
				{			
					//name
					dataName = null;					
					XSSFCell cell = row.getCell(m);
					switch(cell.getCellType())
					{
					case Cell.CELL_TYPE_STRING:
						dataName = cell.getStringCellValue();	
						break;				
					case Cell.CELL_TYPE_NUMERIC:
						temp = (int) cell.getNumericCellValue();
						dataName = String.valueOf(temp);
						break;
					case Cell.CELL_TYPE_FORMULA:
						temp = (int)cell.getNumericCellValue();	
						dataName = String.valueOf(temp);
						break;
					case Cell.CELL_TYPE_BLANK:					
						dataName = "$merge";
						break;
					}
					
					if(cellInfo.GetMergeColStart(cell.getColumnIndex()))
					{				
						if(dataName.contains("[]"))
						{
							dataName = dataName.replace("[]","");
							
						}
						else
						{
							System.err.println("[filename : " + filename + " / sheet Name :"+ sheetName+ "]:  배열일 경우  '[]'가 필요합니다!!");
							return;						
						}				
					}
					else if(dataName.contains("[]"))
					{					
						System.err.println("[filename : " + filename + " / sheet Name :"+ sheetName+ "]: 배열이 아닌데 []가 있습니다.!!");		
						return;	
					}
						
					if((! dataName.isEmpty()) && (!dataName.equals("$merge")))				
					{
						if(!cellInfo.GetMerge(cell.getColumnIndex()))
						{						
						}	
						
						if(m == cellStart)
						{
							jsonString =  jsonString + "{";
						}
						if(dataName.equals("$key"))
						{						
							dataName = dataName.replace("$","");
						}					
						jsonString =  jsonString + "\"" + dataName +"\"" +":";
					}
					
					
					////////////////////////////////////////////////////////////////////////////////////////////////////
					dataValue = null;
					//////////////////////////////////////////////////////////////////
					XSSFCell dataCell = rowJson.getCell(m); 	
					switch(dataCell.getCellType())
					{
					case Cell.CELL_TYPE_STRING:
						dataValue = dataCell.getStringCellValue();	
						break;				
					case Cell.CELL_TYPE_NUMERIC:
						temp = (int) dataCell.getNumericCellValue();
						dataValue = String.valueOf(temp);
						break;
					case Cell.CELL_TYPE_FORMULA:
						temp = (int)dataCell.getNumericCellValue();	
						dataValue = String.valueOf(temp);
						break;						
					}	
					
					if(dataValue == null)
					{
						System.err.println("[filenaem : " + filename + " / sheet Name :"+ sheetName+ "]: data  is null !!!!");	
						return;
					}				
					else //(dataValue != null)
					{						
						int startEnd = 0;					
						if(cellInfo.GetMergeColStart(dataCell.getColumnIndex()))
						{							
								startEnd = 1;
						}
						if(cellInfo.GetMergeColEnd(dataCell.getColumnIndex()))
						{
								startEnd = 2;					
						}					
						
						if(startEnd == 1)
						{
							jsonString = jsonString +"["+  "\""  +   dataValue + "\"";				
						}
						else if( startEnd == 2)
						{
							if(dataValue.equals("null"))
							{
								jsonString = jsonString.substring(0, jsonString.length() -1);
								jsonString = jsonString +  "]";
							}		
							else
							{
								jsonString = jsonString + "\""  +   dataValue + "\"" + "]";
							}						
						}
						else
						{
							if(dataValue.equals("null"))
							{
								continue;
							}
							jsonString = jsonString + "\"" + dataValue + "\"" ;
						}
						
						
						// end
						if( m == cellCount -1)
						{
							if(n == rowCount)
							{
								jsonString = jsonString + "}]";
							}
							else
							{
								jsonString = jsonString + "},";	
							}
						}
						else
						{
							jsonString = jsonString + ",";						
						}					
					}
				}
			}	
			System.out.print(jsonString);
			JSONObject  obj = new JSONObject();
			
			filename = filename.replace("xlsx", "");
			filename = filename + "." + sheetName + ".json";
			
			FileWriter file = new FileWriter(filename);
			file.write(jsonString);
		 		
		 	file.flush();
		 	file.close();
			
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	  }
}
