import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Grammar {

	List<NoTerminal> V;
	List<Terminal> T;
	List<Production> P;
	NoTerminal S;
	public Grammar(List<NoTerminal> v, List<Terminal> t, List<Production> p, NoTerminal s) {
		V = v;
		T = t;
		P = p;
		S = s;
	}
	public List<NoTerminal> getV() {
		return V;
	}
	public void setV(List<NoTerminal> v) {
		V = v;
	}
	public List<Terminal> getT() {
		return T;
	}
	public void setT(List<Terminal> t) {
		T = t;
	}
	public List<Production> getP() {
		return P;
	}
	public void setP(List<Production> p) {
		P = p;
	}
	public NoTerminal getS() {
		return S;
	}
	public void setS(NoTerminal s) {
		S = s;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((P == null) ? 0 : P.hashCode());
		result = prime * result + ((S == null) ? 0 : S.hashCode());
		result = prime * result + ((T == null) ? 0 : T.hashCode());
		result = prime * result + ((V == null) ? 0 : V.hashCode());
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
		Grammar other = (Grammar) obj;
		
		if (P == null) {
			if (other.P != null)
				return false;
		} else {
			for (Production p : P) 
				if (!other.P.contains(p))
					return false;
			for (Production p : other.P) 
				if (!other.P.contains(p))
					return false;
			return true;
		}
		if (S == null) {
			if (other.S != null)
				return false;
		} else if (!S.equals(other.S))
			return false;
		if (T == null) {
			if (other.T != null)
				return false;
		} else if (!T.equals(other.T))
			return false;
		if (V == null) {
			if (other.V != null)
				return false;
		} else if (!V.equals(other.V))
			return false;
		return true;
	}
	
	public void printGrammar(){
		System.out.println("\nPRODUCCIONES");
		for (int j = 0; j < this.getP().size(); j++) {
			System.out.println(this.getP().get(j).toString());
		}
		
		System.out.println("\nNO TERMINALES");
		for (int j = 0; j < this.getV().size(); j++) {
			System.out.println(this.getV().get(j).toString() +  " : " + this.getV().get(j).getValue().getClass().getSimpleName());
		}
		
		System.out.println("\nTERMINALES");
		for (int j = 0; j < this.getT().size(); j++) {
			System.out.println(this.getT().get(j).toString() +  " : " + this.getT().get(j).getValue().getClass().getSimpleName());
		}
		
		System.out.println("\nINICIAL");
		System.out.println(this.getS().toString() + " : " +  this.getS().getValue().getClass().getSimpleName());
		
	}
	
	public void printPrettyGrammar(){
		this.orderProductions();
		Symbol last = new Terminal<String>("");
		String acum = new String("");
		HashSet<String> vistos = new HashSet<String>();

		System.out.println("\nPRODUCCIONES");
		for (int j = 0; j < this.getP().size(); j++) {
			if (last.equals(this.getP().get(j).getLeft())){
				if (this.getP().get(j).getRight().size() == 0)
					acum += " | " + "Epsilon";
				else
					acum += " | " + toStringList(this.getP().get(j).getRight());
			}
			else{
				last = this.getP().get(j).getLeft();
				if (!vistos.contains(acum)){
					vistos.add(acum);
					System.out.println(acum);
				}
				if (this.getP().get(j).getRight().size() == 0)
					acum = this.getP().get(j).getLeft() + " -> Epsilon";
				else
					acum = this.getP().get(j).getLeft() + " -> " + toStringList(this.getP().get(j).getRight());
			}			
		}
		System.out.println(acum);

		System.out.println("\nNO TERMINALES");
		for (int j = 0; j < this.getV().size(); j++) {
			System.out.println(this.getV().get(j).toString() +  " : " + this.getV().get(j).getValue().getClass().getSimpleName());
		}
		
		System.out.println("\nTERMINALES");
		for (int j = 0; j < this.getT().size(); j++) {
			System.out.println(this.getT().get(j).toString() +  " : " + this.getT().get(j).getValue().getClass().getSimpleName());
		}
		
		System.out.println("\nINICIAL");
		System.out.println(this.getS().toString() + " : " +  this.getS().getValue().getClass().getSimpleName());
		
	}
	
	
	public String toStringList(List<Symbol> list){
		String aux = "";
		for (Symbol symbol : list) {
			aux += " " +symbol.toString();
		}
		return aux;
	}
	
	public int value(String s){
		int acum = 0;
	    for (char c : s.toCharArray())
	    	acum += s.charAt(0);
	    return acum;
	}
	
	
	public void orderProductions(){
		ArrayList<Production> aux = new ArrayList<Production>();
		for (Symbol left : this.getV()) 
			for (int i = 0; i < this.getP().size(); i++) 
				if (this.getP().get(i).getLeft().equals(left))
					aux.add(this.getP().get(i));
		this.setP(aux);
	}
	
}