package thrift;

public class thriftStruct {
	private String structType;
	private String structName;
	
	
	public thriftStruct(String t, String n){
		this.structType = t;
		this.structName = n;
	}
	
	public String getType(){
		return structType;		
	}
	
	public String getName(){
		return structName;
	}
	
	public void setStructType(String t)
	{
		this.structType = t;
	}
	
	public void setStructName(String n)
	{
		this.structName = n;
	}	
}
