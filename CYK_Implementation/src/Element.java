
public class Element {

	String value;
	Tuple i;
	Tuple j;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		 else if (value.equals(obj))
			return true;
		Element other = (Element) obj;
		if (other != null)
		 if (value.equals(other.value))
			return true;
		return false;
	}

	@Override
	public String toString(){
		return value;
	}
	
	public Element(String value, Tuple i, Tuple j) {
		this.value = value;
		this.i = i;
		this.j = j;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Tuple getI() {
		return i;
	}
	public void setI(Tuple i) {
		this.i = i;
	}
	public Tuple getJ() {
		return j;
	}
	public void setJ(Tuple j) {
		this.j = j;
	}
	
}
