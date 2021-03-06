
public class Terminal<E> extends Symbol {

	E value;

	public String toString(){
		return value.toString();
	}
	public E getValue() {
		return value;
	}


	public void setValue(E value) {
		this.value = value;
	}

	public Terminal(E value) {
		super();
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Terminal other = (Terminal) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	

}
