import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class TurinMachine {

	Set<State> finals;
	Set<Symbol<String>> Alphabet;
	Set<Symbol<String>> TapeAlphabet;
	State initialState;
	Set<State> states;
	WhiteSymbol whiteSymbol;
	Set<Delta> delta;
	List<Configuration> configuration;
	
	public TurinMachine(Tape tape, HashSet<State> finals2,
			Set<Symbol<String>> alphabet,Set<Symbol<String>> TapeAlphabet,State initialState, Set<State> states,
			WhiteSymbol whiteSymbol, Set<Delta> delta) {
		super();
		this.configuration = new ArrayList<Configuration>();
		this.configuration.add(new Configuration(initialState, tape));
		this.finals = finals2;
		Alphabet = alphabet;
		this.initialState = initialState;
		this.states = states;
		this.whiteSymbol = whiteSymbol;
		this.delta = delta;
		this.TapeAlphabet = TapeAlphabet;
	}

	public Set<Delta> searchForDelta(State state, Symbol<String> readed){
		HashSet<Delta> set = new HashSet<Delta>();
		java.util.Iterator<Delta> iterator = this.delta.iterator();
		while (iterator.hasNext()){
			Delta deltaR = iterator.next();
			if (deltaR.getState().equals(state) && deltaR.getReaded().equals(readed)){
				set.add(deltaR);
			}
		}
		
		if (delta.isEmpty()){
			System.out.println("No rules for this state, halting...");
			System.exit(0);
			return null;
		}
		else
			return set;
	}
	
	public void print(){
		int i = 0;
		while (i < configuration.size()){
			System.out.print("configuracion " + i + ": ");
			configuration.get(i).getTape().printTape(configuration.get(i).getActualState());
			i++;
		}
	}
	
	public boolean hasEnded(){
		int i = 0;
		while (i < configuration.size()){
			if (finals.contains(configuration.get(i).getActualState())){
				System.out.println(i + " acepta");
				return true;
			}
			i++;
		}
		return false;
	}
	
	public void applyDelta(Set<Delta> delta, int i){
		//ejecuto la primera iteracion de delta sobre la configuracion actual
		java.util.Iterator<Delta> iterator = delta.iterator();
		if (iterator.hasNext()){
			Delta thisDelta = iterator.next();
			if (thisDelta.getMove().equals("R")){
				configuration.get(i).getTape().moveRightWrite(thisDelta.getWrite());
				System.out.println("readed " + thisDelta.getReaded()+ " write " +thisDelta.getWrite() +" move R " + "change to " + thisDelta.getNext().getState());
			}
			else if (thisDelta.getMove().equals("L")){
				configuration.get(i).getTape().moveLeftWrite(thisDelta.getWrite());
				System.out.println("readed " + thisDelta.getReaded()+ " write " +thisDelta.getWrite() +" move L "+ "change to " + thisDelta.getNext().getState());
			}
			configuration.get(i).setActualState(thisDelta.getNext());
		
			//si hay mas de un delta, tengo que crear una nueva configuracion por cada delta extra
			if (delta.size() > 1){
				System.out.println("CREATED NEW TAPE, nº " + configuration.size());
				while(iterator.hasNext()){
					thisDelta = iterator.next();
					Configuration config = new Configuration((State)configuration.get(i).getActualState().clone(), configuration.get(i).getTape().clone());
					if (thisDelta.getMove().equals("R")){
						config.getTape().moveRightWrite(thisDelta.getWrite());
						System.out.println("readed " + thisDelta.getReaded()+ " write " +thisDelta.getWrite() +" move R " + "change to " + thisDelta.getNext().getState());
					}
					else if (thisDelta.getMove().equals("L")){
						config.getTape().moveLeftWrite(thisDelta.getWrite());
						System.out.println("readed " + thisDelta.getReaded()+ " write " +thisDelta.getWrite() +" move L "+ "change to " + thisDelta.getNext().getState());
					}
					else
						System.out.println("NINGUNO \n\n");
					config.setActualState(thisDelta.getNext());
					configuration.add(config);
				}
			}
		}
	}
	
	public void run(){
		boolean goOn = true;
		this.print();
		while(goOn){	
			for (int i = 0; i < configuration.size(); i++) {	
				Symbol<String> readed = configuration.get(i).getTape().read();
				HashSet<Delta> newDelta = (HashSet<Delta>) searchForDelta(configuration.get(i).getActualState(), readed);
				applyDelta(newDelta,i);
			}
			System.out.println("     fin tanda de ejecuciones");
			if (hasEnded()){
				goOn = false;
				System.out.println("FINAL CONFIGURATION:");
			}
			this.print();
		}
	}
}
