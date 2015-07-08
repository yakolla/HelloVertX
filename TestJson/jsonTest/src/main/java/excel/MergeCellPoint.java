package excel;

public class MergeCellPoint {
	int colStart;
	int colEnd;			
	int rowStart;			
	int rowEnd;	
	boolean  ignoreFlag;
	public int getColStart() {
		return colStart;
	}
	public void setColStart(int colStart) {
		this.colStart = colStart;
	}
	public int getColEnd() {
		return colEnd;
	}
	public void setColEnd(int colEnd) {
		this.colEnd = colEnd;
	}
	public int getRowStart() {
		return rowStart;
	}
	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}
	public int getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}
	public boolean getIgnoreFlag()
	{
		return ignoreFlag;
	}
	public MergeCellPoint(int colStart, int colEnd, int rowStart, int rowEnd) {
		super();
		this.colStart = colStart;
		this.colEnd = colEnd;
		this.rowStart = rowStart;
		this.rowEnd = rowEnd;
	}			
}
