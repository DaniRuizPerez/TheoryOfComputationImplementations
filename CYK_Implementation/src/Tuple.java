
public class Tuple {

	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Tuple(int i, int j) {
		this.i = i;
		this.j = j;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	int i;
	int j;
	
	
	@Override
	public String toString(){
		return "("+ i + ", " + j + ")";
	}
}
