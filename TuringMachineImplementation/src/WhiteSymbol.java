
public  class WhiteSymbol extends Symbol<String>{

	String value;
	
	public WhiteSymbol(String value) {
		super();
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	@Override
	public java.lang.String toString() {
		return value + " ";
	}

}
