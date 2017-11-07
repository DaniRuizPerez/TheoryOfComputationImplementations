
public class Delta {
	private State state;
	private Symbol<String> readed;
	private Symbol<String> write;
	private String move;
	private State next;
	
	public Delta(State state, Symbol<String> readed, Symbol<String> write, String move, State next) {
		this.state = state;
		this.readed = readed;
		this.write = write;
		this.move = move;
		this.next = next;
	}
	
	public void printDelta(){
		System.out.println(state.getState() + " " + readed + " " +  write + " " +  move +  " " +  next.getState());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((move == null) ? 0 : move.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((readed == null) ? 0 : readed.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((write == null) ? 0 : write.hashCode());
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
		Delta other = (Delta) obj;
		if (move == null) {
			if (other.move != null)
				return false;
		} else if (!move.equals(other.move))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (readed == null) {
			if (other.readed != null)
				return false;
		} else if (!readed.equals(other.readed))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (write == null) {
			if (other.write != null)
				return false;
		} else if (!write.equals(other.write))
			return false;
		return true;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Symbol<String> getReaded() {
		return readed;
	}
	public void setReaded(Symbol<String> readed) {
		this.readed = readed;
	}
	public Symbol<String> getWrite() {
		return write;
	}
	public void setWrite(Symbol<String> write) {
		this.write = write;
	}

	public State getNext() {
		return next;
	}
	public void setNext(State next) {
		this.next = next;
	}

	State actualState;
	

}
