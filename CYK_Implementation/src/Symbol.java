
public abstract class Symbol<E> {

	E value;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public E getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.toString().trim().equals(other.value.toString().trim()))
			return false;
		return true;
	} 
	

}
