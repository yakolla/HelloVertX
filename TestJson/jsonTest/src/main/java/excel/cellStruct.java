package excel;


public class cellStruct {
	int index;
	String cellName;
	boolean ignoreCheck;
	boolean arrayCheck;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public boolean isIgnoreCheck() {
		return ignoreCheck;
	}
	public void setIgnoreCheck(boolean ignoreCheck) {
		this.ignoreCheck = ignoreCheck;
	}
	public boolean isArrayCheck() {
		return arrayCheck;
	}
	public void setArrayCheck(boolean arrayCheck) {
		this.arrayCheck = arrayCheck;
	}
	public cellStruct(int index, String cellName, boolean ignoreCheck,
			boolean arrayCheck) {
		super();
		this.index = index;
		this.cellName = cellName;
		this.ignoreCheck = ignoreCheck;
		this.arrayCheck = arrayCheck;
	}
	

}
