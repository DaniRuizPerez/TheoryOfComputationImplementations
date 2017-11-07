import java.util.ArrayDeque;


public class Tape implements Cloneable {
	
	@Override
	public Tape clone(){
		Tape newTape = new Tape("",whiteSymbol);
		newTape.stackL = stackL.clone();
		newTape.stackR = stackR.clone();
		return newTape;
	}
	
	ArrayDeque<Symbol<String>> stackL = new ArrayDeque<Symbol<String>>();
	ArrayDeque<Symbol<String>> stackR = new ArrayDeque<Symbol<String>>();
	WhiteSymbol whiteSymbol;
	
	public void printTape (State actualState){
		System.out.print(stackL);
		System.out.print(" " + actualState.getState() + " ");
		System.out.println(stackR);
	}
	
	
	public Tape(String entry, WhiteSymbol whiteSymbol){
		this.whiteSymbol = whiteSymbol;
		
		if (!entry.equals("")){
			String[] splitP = entry.split("P");
			String[] splitL = splitP[0].split(" ");
			String[] splitR= splitP[1].split(" ");
	
			stackL.push(whiteSymbol);
			
			for (String string : splitL) 
				if (string.equals("_b_"))
					stackL.offer(whiteSymbol);
				else if (!string.equals("") && !string.equals(" "))
					stackL.offer(new AlphabetSymbol<String>(string));
	
			for (String string : splitR) 
				if (string.equals("_b_"))
					stackR.offerLast(whiteSymbol);
				else if (!string.equals("") && !string.equals(" "))
					stackR.offerLast(new AlphabetSymbol<String>(string));
			stackR.offerLast(whiteSymbol);
		}

	}
	
	
	public Symbol<String> read(){
		return stackR.peekFirst();
	}
	
	public void Left(){
		if (!stackL.isEmpty())
			stackR.offerFirst(stackL.pollLast());
		else
			stackR.offerFirst(whiteSymbol);
		
		if (stackL.isEmpty())
			stackL.offer(whiteSymbol);
	}
	
	public void Right(){
		if (!stackR.isEmpty())
			stackL.offer(stackR.poll());
		else
			stackL.offer(whiteSymbol);
		
		if (stackR.isEmpty())
			stackR.offer(whiteSymbol);
	}
	
	public void Write(Symbol<String> symbol){
		stackL.offer(symbol);		
	}
	
	public void Remove(){
		stackL.pollLast();
	}
	
	public void Replace1(Symbol<String> symbol){
		stackL.pollLast();
		stackL.offer(symbol);		
	}

	public void Replace(Symbol<String> symbol){
		stackR.pollFirst();
		stackR.offerFirst(symbol);			
	}
	
	
	public void moveLeftWrite(Symbol<String> symbol){
		this.Replace(symbol);
		this.Left();
	}
	
	public void moveRightWrite(Symbol<String> symbol){
		this.Replace(symbol);
		this.Right();
	}

}
