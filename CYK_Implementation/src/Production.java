import java.util.List;


public class Production {

	NoTerminal left;
	List<Symbol> right;
	
	public String toString(){
		String aux = "[";
		if (right.size() != 0){
			for (int i = 0; i < right.size()-1; i++) 
				aux += right.get(i).getValue().getClass().getSimpleName() + ", ";
			aux += right.get(right.size()-1).getValue().getClass().getSimpleName() + "]";
		}
		else 
			aux += "]";
		return left.toString() + " -> " + right.toString() +" : " +   left.getValue().getClass().getSimpleName() + " -> " + aux;
		
	}
	
	public NoTerminal getLeft() {
		return left;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	public void setLeft(NoTerminal left) {
		this.left = left;
	}

	public List<Symbol> getRight() {
		return right;
	}

	public void setRight(List<Symbol> right) {
		this.right = right;
	}

	public Production(NoTerminal left, List<Symbol> right) {
		super();
		this.left = left;
		this.right = right;
	}
	

	
}
