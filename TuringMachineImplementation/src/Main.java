import java.util.HashSet;


public class Main {

	public static void main(String[] args) {

		boolean wich = false;
		
		if (wich){
			
		/****************** CAMBIAR AS POR BES ************/
			HashSet<Symbol<String>> alphabet = new HashSet<Symbol<String>>();
			Symbol<String> a = new AlphabetSymbol<String>("a");
			Symbol<String> b = new AlphabetSymbol<String>("b");
			alphabet.add(a);
			alphabet.add(b);
			WhiteSymbol white = new WhiteSymbol("$");
	
			HashSet<State> finals = new HashSet<State>();
			HashSet<State> states = new HashSet<State>();
			State s1= new State("s1");
			State s2= new State("s2");
			State initialState =s1;
			states.add(s1);
			states.add(s2);
			finals.add(s2);
			
			HashSet<Delta> delta = new HashSet<Delta>();
			Delta delta1 = new Delta(s1, a, b, "R", s1);
			Delta delta2 = new Delta(s1, b, a, "R", s1);
			Delta delta3 = new Delta(s1, white, white, "R", s2);
			delta.add(delta1);
			delta.add(delta2);
			delta.add(delta3);
			
			
			Tape tapeT = new Tape("P b a a b b a b a a ", white);
			TurinMachine M = new TurinMachine(tapeT, finals, alphabet, null, initialState, states, white,delta);
			M.run();
		}
		else{
		
		/****************** FIN CAMBIAR AS POR BES ************/
	
			
			HashSet<Symbol<String>> alphabet = new HashSet<Symbol<String>>();
			Symbol<String> a = new AlphabetSymbol<String>("a");
			Symbol<String> b = new AlphabetSymbol<String>("b");
			Symbol<String> X = new AlphabetSymbol<String>("X");
			Symbol<String> Y = new AlphabetSymbol<String>("Y");
			alphabet.add(a);
			alphabet.add(b);
			alphabet.add(X);
			alphabet.add(Y);
			WhiteSymbol white = new WhiteSymbol("$");
	
			HashSet<State> finals = new HashSet<State>();
			HashSet<State> states = new HashSet<State>();
			State s0= new State("s0");
			State s1= new State("s1");
			State s2= new State("s2");
			State s3= new State("s3");
			State s4= new State("s4");
			State s5= new State("s5");
			State s6= new State("s6");
			State initialState =s0;
			states.add(s1);
			states.add(s0);
			states.add(s2);
			states.add(s3);
			states.add(s4);
			states.add(s5);
			states.add(s6);
			finals.add(s5);
			
			HashSet<Delta> delta = new HashSet<Delta>();
			Delta delta1 = new Delta(s0, a, X, "R", s1);
			Delta delta2 = new Delta(s1, a, a, "R", s1);
			Delta delta3 = new Delta(s1, Y, Y, "R", s1);
			Delta delta4 = new Delta(s2, X, X, "R", s0);
			Delta delta5 = new Delta(s3, Y, Y, "R", s3);
			Delta delta6 = new Delta(s0, Y, Y, "R", s3);
			Delta delta7 = new Delta(s1, b, Y, "L", s2);
			Delta delta8 = new Delta(s2, a, a, "L", s2);
			Delta delta9 = new Delta(s2, Y, Y, "L", s2);
			Delta delta10 = new Delta(s3, white, white, "R", s4);
			Delta delta11 = new Delta(s1, Y, Y, "R", s4);
			Delta delta12 = new Delta(s4, Y, Y, "R", s4);
			Delta delta13 = new Delta(s4, white, white, "R", s5);
			

			
			Delta delta14 = new Delta(s0, a, a, "L", s6);
			Delta delta15 = new Delta(s6, X, X, "L", s6);
			Delta delta16 = new Delta(s6, white, white, "L", s6);
			
			/* descomentar esto para que acepte la segunda configuracion*/
//			Delta delta15 = new Delta(s6, X, X, "L", s5);
//			delta.add(delta15);
			
			delta.add(delta1);
			delta.add(delta2);
			delta.add(delta3);
			delta.add(delta4);
			delta.add(delta5);
			delta.add(delta6);
			delta.add(delta7);
			delta.add(delta8);
			delta.add(delta9);
			delta.add(delta10);
			delta.add(delta11);
			delta.add(delta12);
			delta.add(delta13);
			delta.add(delta14);
			delta.add(delta15);
			delta.add(delta16);
			
			Tape tapeT = new Tape("P a a a b b b ", white);
			TurinMachine M = new TurinMachine(tapeT, finals, alphabet,null, initialState, states, white,delta);
			M.run();
		}	
	}
}
