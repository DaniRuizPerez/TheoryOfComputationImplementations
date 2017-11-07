
public class Configuration {
	
	State actualState;
	Tape tape;
	
	public Configuration(State actualState, Tape tape) {
		super();
		this.actualState = actualState;
		this.tape = tape;
	}

	public State getActualState() {
		return actualState;
	}

	public void setActualState(State actualState) {
		this.actualState = actualState;
	}

	public Tape getTape() {
		return tape;
	}

	public void setTape(Tape tape) {
		this.tape = tape;
	}

	public void print(){
		System.out.print("printing configuration -> ");
		tape.printTape(actualState);
	}

}
