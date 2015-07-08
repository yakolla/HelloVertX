package excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class CellInfo {
	private List<MergeCellPoint> mergeCellList;	
	private List<Integer> ignoreList;
	private HashMap<String, String> cellNameMap;
	
	public CellInfo() {
		mergeCellList = new ArrayList<MergeCellPoint>();
		ignoreList = new ArrayList<Integer>();
		cellNameMap = new HashMap<String, String>();
	}
	
	public void AddIgnoreList(int t)
	{
		ignoreList.add(t);
	}
	public void AddCellNameMap(String name)
	{
		cellNameMap.put(name, name);	
	}
	public int GetCellNameMapSize()
	{
		int size =  cellNameMap.size();
		return size;
	}
	public boolean CheckName(String name)
	{
		if(cellNameMap.get(name) != null)
		{
			return true;
		}
		return false;
	}
	
	public boolean CheckIgnoreCell(int cellIndex)
	{
		for(int i = 0; i < ignoreList.size(); i++)
		{
			if((int)ignoreList.get(i) == cellIndex)
			{
				return true;
			}
		}
		return false;
	}
	
	public List<MergeCellPoint> getMergeCellList() {
		return mergeCellList;
	}
	
	public void setMergeCellList(List<MergeCellPoint> mergeCellList) {
		this.mergeCellList = mergeCellList;
	}
	
	public boolean getIgnoreMergeCheck(int startNo)
	{
		for(int i = 0; i <ignoreList.size(); i++ )
		{
			if(startNo == (int)ignoreList.get(i))
			{
				return true;
			}
		}
		return false;
	}
	
	public void setMergeIgnore(int start, int end)
	{
		for(int i = start; i <end; i++  )
		{
			ignoreList.add(i);
		}
	}
	
	public int MakeMergeList(XSSFSheet checkSheet , int startRowNum)
	{
		int nbrMergedRegions = checkSheet.getNumMergedRegions();
		for(int x =0; x < nbrMergedRegions; x++)
		{			
			org.apache.poi.ss.util.CellRangeAddress region = checkSheet.getMergedRegion(x);			
			int colStart = region.getFirstColumn();
			int colEnd = region.getLastColumn();			
			int rowStart = region.getFirstRow();			
			int rowEnd = region.getLastRow();
			
			MergeCellPoint point = new MergeCellPoint(colStart,colEnd,rowStart,rowEnd);			
			if(startRowNum == rowStart )
			{
				mergeCellList.add(point);		
				System.out.print("colStart:"+ colStart + "/"+ "colend:" + colEnd+ "/"+ "rowStart:"+ rowStart +"/"+  "rowEnd:" + rowEnd + "\n");	
				if(getIgnoreMergeCheck(point.getRowStart()))
				{
					setMergeIgnore(point.getColStart(), point.getColEnd());
				}
				
				
			}
		}		
		return 0;
	}
	
	public boolean GetMerge(int colNum)
	{
		for(int o = 0; o < mergeCellList.size(); o++)
		{	
			MergeCellPoint point = mergeCellList.get(o);					
			if(point.getColStart() == colNum)
			{					
				return true;			
			}
		}
		return false;
	}
	
	
	public  boolean GetMergeColStart(int colNum)
	{
		for(int o = 0; o < mergeCellList.size(); o++)
		{
			MergeCellPoint point = mergeCellList.get(o);					
			if(point.getColStart() == colNum)
			{					
				return true;			
			}
		}
		return false;
	}
	public  boolean GetMergeColEnd(int colNum)
	{
		for(int o = 0; o < mergeCellList.size(); o++)
		{
			MergeCellPoint point = mergeCellList.get(o);					
			if(point.getColEnd() == colNum)
			{					
				return true;			
			}
		}
		return false;
	}
}
