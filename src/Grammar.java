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
				if (!contains(other.P,p))
					return false;
			for (Production p : other.P) 
				if (!contains(P,p))
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
			if (compareSymbols(last,this.getP().get(j).getLeft())){
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
				if (compareSymbols(this.getP().get(i).getLeft(),left))
					aux.add(this.getP().get(i));
		this.setP(aux);
	}
	
	
	
	public boolean compareSymbols(Symbol s1, Symbol s2){
		/* COMO HE TENIDO TANTOS PROBLEMAS AL COMPARAR POR LOS ESPACIOS, HAGO EL REPLACE Y PISTA*/
		return s1.getValue().toString().replace(" ", "").equals(s2.getValue().toString().replace(" ", ""));
	}

	
	public boolean contains(List<Symbol> list, Symbol symbol){
		for (int i = 0; i < list.size(); i++) 
			if (compareSymbols(list.get(i),symbol))
				return true;
		return false;	
	}
	
	public boolean containsSymbolInRight (List<Production> productionsVariablesAux, Symbol symbol){
		for (Production p : productionsVariablesAux) 
			if (contains(p.getRight(), symbol))
				return true;
		return false;
	}
	
	
	public boolean containsSymbolsInRight (List<Production> productionsVariablesAux, List<Symbol> symbol){
		for (Production p : productionsVariablesAux) 
			if (containsAll(p.getRight(), symbol))
				return true;
		return false;
	}
	
	public int wherecontainsSymbolInRight (List<Production> productionsVariablesAux, Symbol symbol){
		for (int i = 0; i < productionsVariablesAux.size(); i++) 
			if (contains(productionsVariablesAux.get(i).getRight(), symbol))
				return i;
		return -1;
	}
	
	public boolean contains(Set<Symbol> set, Symbol symbol){
		Iterator iterator = set.iterator();
		
		while(iterator.hasNext()){
			Symbol element = (Symbol) iterator.next();
			if (compareSymbols(element,symbol))
				return true;	
		}
		return false;
	}
	
	public boolean compareListSymbols(List<Symbol> ls1, List<Symbol> ls2){
		if (ls1.size() != ls2.size())
			return false;
		for (int i = 0; i < ls1.size(); i++) {
			if (!compareSymbols(ls1.get(i), ls2.get(i)))
				return false;
		}
		return true;
	}
	
	public boolean contains (List<Production> productions, Production p){
		for (Production production : productions) 
			if (compareSymbols(production.getLeft(), p.getLeft()) && compareListSymbols(production.getRight(), p.getRight()))
				return true;
		return false;
	}
	
	
	public boolean containsAll(List<Symbol> contenedor, List<Symbol> contenido){
		for (int i = 0; i < contenido.size(); i++) 
			if (!contains(contenedor,contenido.get(i)))
				return false;
		return true;
	}
	
	public  List<Integer> wichIsntContent(List<Symbol> contenedor, List<Symbol> contenido){
		ArrayList<Integer> auxList = new ArrayList<Integer>();
		for (int i = 0; i < contenido.size(); i++) 
			if (!contains(contenedor,contenido.get(i)))
				auxList.add(i);
		return auxList;		
	}
	
	
	
	public Grammar onlyGenerators(){
		//inicializo los generadores a los terminales que ya lo son fijo
		ArrayList<Symbol> generators = new ArrayList<Symbol>(this.getT());
		boolean newgenerators = true;
		while(newgenerators){
			//Mientras queden generadores por encontrar, para cada produccion mira si su parte
			//derecha son todo generadores para ver si puedo meter su izda en la lista
			newgenerators = false;
			for(Production p: this.getP()){
				if (p.getRight().size() != 0){
					if(containsAll(generators,p.getRight()) && !contains(generators,p.getLeft()))
						newgenerators = generators.add(p.getLeft()) || newgenerators;
				}
				//si A->Epsilon A tambien es generador
				else if (!contains(generators,p.getLeft()))
					newgenerators = generators.add(p.getLeft());
			}
		}
		
		//si todos los elementos de la derecha estan en la lista de generadores, la produccion se pasa tal cual
		ArrayList<Production> productions = new ArrayList<Production>();
		for(Production p: this.getP())
			if(containsAll(generators,p.getRight()))
				productions.add(p);
			
		ArrayList<Terminal> terminals = new ArrayList<Terminal>();
		ArrayList<NoTerminal> noTerminals = new ArrayList<NoTerminal>();
		Iterator<Symbol> iterator = generators.iterator();
		//ahora clasifico los simbolos generadores en terminales y no terminales
		while(iterator.hasNext()){
			Symbol aux = iterator.next();
			if (this.getT().contains(aux))
				terminals.add((Terminal) aux);
			else
				noTerminals.add((NoTerminal) aux);
		}
		
		//esto es para imprimir
		ArrayList<NoTerminal> noGeneradores = new ArrayList<NoTerminal>();
		for (NoTerminal noTerminal : this.getV()) 
			if (!contains(generators,noTerminal))
				noGeneradores.add(noTerminal);
		System.out.println("generadores ->" + generators);
		System.out.println("NOgeneradores ->" + noGeneradores);

		return new Grammar(noTerminals,terminals,productions,this.getS());
	}
	
	

	public Grammar onlyReachables(){
		ArrayList<Symbol> reachables = new ArrayList<Symbol>();	
		ArrayDeque<Symbol> canReachQue = new ArrayDeque<Symbol>();

		//añado el simbolo inicial a los reachables
		reachables.add(this.getS());

		//Relleno los alcanzables por el símbolo inicial, siempre y cuando no hayan sido añadidos ya
		for (int i = 0; i < this.getP().size(); i++) 
			if (compareSymbols(this.getP().get(i).getLeft(),this.getS()))
				for (int j = 0; j < this.getP().get(i).getRight().size(); j++) 
					if (!canReachQue.contains(this.getP().get(i).right.get(j)))
						canReachQue.offer(this.getP().get(i).right.get(j));
	
			
		//segun voy alcanzando nuevos elementos los meto en la cola
		//voy sacando de la cola y viendo los que son alcanzables por ese elemento
		//con que la parte izda sea alcanzable, ya es alcanzable
		while(canReachQue.size() != 0){
			Symbol element = canReachQue.pop();
			reachables.add(element);
			for (int i = 0; i < this.getP().size(); i++) 
				if (compareSymbols(this.getP().get(i).getLeft(),element))
					if (!canReachQue.contains(element))
						for (int j = 0; j < this.getP().get(i).getRight().size(); j++) 
							if (!canReachQue.contains(this.getP().get(i).getRight().get(j)) && !contains(reachables, this.getP().get(i).getRight().get(j)))
								canReachQue.offer(this.getP().get(i).getRight().get(j)); 
		}
				
		//las producciones finales son las que su parte derecha e izda son alcanzables
		ArrayList<Production> productions = new ArrayList<Production>();
		for(Production p: this.getP()){
			if(containsAll(reachables,p.getRight()) && contains(reachables,p.getLeft()))
				productions.add(p);
		}
		
		ArrayList<Terminal> terminals = new ArrayList<Terminal>();
		ArrayList<NoTerminal> noTerminals = new ArrayList<NoTerminal>();
		Iterator<Symbol> iterator = reachables.iterator();
		//ahora clasifico los simbolos generadores en terminales y no terminales
		while(iterator.hasNext()){
			Symbol aux = iterator.next();
			if (this.getT().contains(aux))
				terminals.add((Terminal) aux);
			else
				noTerminals.add((NoTerminal) aux);
		}		
		
		ArrayList<Symbol> noReachables = new ArrayList<Symbol>();
		for (NoTerminal noTerminal : this.getV()) 
			if (!contains(reachables,noTerminal))
				noReachables.add(noTerminal);
		for (Terminal terminal : this.getT()) 
			if (!contains(reachables,terminal))
				noReachables.add(terminal);
		
		
		System.out.println("alcanzables -> " + reachables);
		System.out.println("NOalcanzables -> " + noReachables);
		return new Grammar(noTerminals,terminals,productions,this.getS());
		
	}
	
	
	public Set<Symbol> anulables(){
		//devuelve un conjunto de simbolos que son los anulables de la gramatica
		HashSet<Symbol> anulables = new HashSet();
		ArrayDeque<Symbol> tempQueue = new ArrayDeque<Symbol>();
		boolean añadir = true;

		//añado los elementos que van a dar a epsilon
		for(Production p: this.getP())
			if (p.getRight().size()==0)
				tempQueue.offer(p.getLeft());
				
		//para el resto de elementos, miro si todos los elementos de su derecha son anulables
		//utlilizando una cola
		while(tempQueue.size() != 0){
			Symbol element = tempQueue.pop();
			anulables.add(element);
			for(Production p: this.getP()){
				for (Symbol symbol : p.getRight()) 
					if (!contains(anulables, symbol))
						añadir = false;
				if (añadir){
					if (!anulables.contains(p.getLeft()) && ! tempQueue.contains(p.getLeft()))
						tempQueue.offer(p.getLeft());
				}
				else
					añadir = true;
			}
		}
	return anulables;
	}
	
	
	
	public Set<List<Symbol>> combinationsRight (Set<Symbol> anulables, Set<List<Symbol>> s,int size, ArrayList<Symbol> eliminables){
		//devuelve un conjunto de las combinaciones posibles para una lista de simbolos alcanzables
		//la idea es para la lista inicial, añadir al set una nueva lista eliminando el primer elemento
		//el segundo, el tercero... y hacer lo mismo para los nuevos elementos añadidos al set
		HashSet<List<Symbol>> news = (HashSet<List<Symbol>>) s;		
		HashSet<List<Symbol>> aux = new HashSet();	
		ArrayList<Symbol> arrayAux = new ArrayList<Symbol>();
		ArrayList<Symbol> arrayAuxAux = new ArrayList<Symbol>();
		
		//primer bucle para repetirlo hasta que tengan longitd 1
		for (int i = 0; i < size-1; i++) {
			//segundo bucle para todos los elementos d ela lista
			for (List<Symbol> list : s) {
				arrayAux = (ArrayList<Symbol>) list;
				//tercer bucle para las combinaciones
				for (int j = 0; j < size-i; j++) {
					if (contains(anulables,arrayAux.get(j))){
						arrayAuxAux = new ArrayList<Symbol>(arrayAux);					
						arrayAuxAux.remove(j);
						aux.add(arrayAuxAux);
					}
				}
			}
			s.addAll(aux);		
		}
		return s;
	}
	
	
	public ArrayList<Symbol> eliminables (Set<Symbol> anulables){
		//devuelve una lista con los simbolos que solo estan en la parte izquierda de una
		//produccion y su derecha es epsilon
		
		 ArrayList<Symbol> eliminables = new  ArrayList<Symbol>();
		int count = 0;
		for (Symbol symbol : anulables) {
			count = 0;
			//para cada simbolo buscamos si da lugar a una produccion epsilon
			for (Production p : this.getP()) 
				if (compareSymbols(p.getLeft(),symbol) && p.getRight().size() == 0)
					count ++;
				
			//si hay por lo menos una, buscamos a ver si da lugar a una produccion no epsilon
			//y si no lo añadimos a eliminables
			if (count >= 1){
				for (Production p : this.getP()) 
					if (compareSymbols(p.getLeft(),symbol) && p.getRight().size() != 0)
						count = 0;
			
				//si solo da lugar a una produccion epsilon lo añadimos a eliminables
				if (count != 0)
					eliminables.add(symbol);
			}
		}
		
		return eliminables;
	}
	
	
	
	public Grammar notEpsilon(){
		//devuelve una gramatica sin producciones epsilon
		Set<Symbol> anulables = this.anulables();
		System.out.println("anulables-> " + anulables);
		
		//calculo los que solo van a dar epsilon
		ArrayList<Symbol> eliminables =  this.eliminables(anulables);
		System.out.println("eliminables -> " + eliminables);
		//y los elimino de los anulables
		anulables.removeAll(eliminables);

		ArrayList<NoTerminal> noTerminals = (ArrayList<NoTerminal>) this.getV();
		if (eliminables.size() != 0)
			// si hay algun elemento que solo va a dar epsilon lo elimino de los no terminales
			for (int i = 0; i <noTerminals.size(); i++) 
				if (eliminables.contains(noTerminals.get(i))){
					noTerminals.remove(i);
					i--;
				}
		
		HashSet<List<Symbol>> a = new HashSet();
		HashSet<List<Symbol>> combinations = new HashSet();
		ArrayList<Production> productions = new ArrayList<Production>();
		ArrayList<Production> productionsAux = new ArrayList<Production>(); 

		//este bucle es para inicializar productionsAux a productions que si no no funciona :S
		for (int i = 0; i < this.getP().size(); i++) {
			ArrayList<Symbol> listAux = new ArrayList<Symbol>(this.getP().get(i).getRight());
			Production aux = new Production(this.getP().get(i).getLeft(),listAux);
			productionsAux.add(aux);
			
		}
		// este bucle elimina los simbolos que solo van a dar epsilon de todas las producciones.
		for (Production production : productionsAux) 
			for (int i = 0; i < production.getRight().size(); i++) 
				if (contains(eliminables,production.getRight().get(i))){
					production.getRight().remove(i);
					i--;
				}
		
		//si el simbolo inicial es anulable, creamos una produccion para dar a epsilon
		if(contains(anulables,this.getS())){
			Production newP = new Production(this.getS(), new ArrayList<Symbol>());
			productions.add(newP);
		}
		
		for(Production p: productionsAux){
			// si no es epsilon:
			if (p.getRight().size() != 0){
				a = new HashSet();
				a.add(p.getRight());
				combinations = (HashSet<List<Symbol>>) combinationsRight(anulables,a,p.getRight().size(),eliminables);
				for (List<Symbol> list : combinations) {
					Production newP = new Production(p.getLeft(), list);
					//para evitar repetidos
					if (!contains(productions,newP))
						productions.add(newP);
				}
				
			}
		}
		return new Grammar(this.getV(),this.getT(),productions,this.getS());
	}
	
	
	
	
	public ArrayList<Production> returnUnitarys(){
		//Devuelve una lista con las producciones unitarias de la gramática
		ArrayList<Production> unitarys = new ArrayList<Production>();
		//inicializo unitarys a las producciones que van de A -> B siendo los dos no terminales
		for (Production p : this.getP()) 
			if (p.getRight().size() == 1 && this.getV().contains(p.getRight().get(0)) && !contains(unitarys,p))
				unitarys.add(p);
		
		ArrayList<Production> aux = new ArrayList<Production>();	
		//mientras siga añadiendo nuevas producciones unitarias a la lista sigo
		//para cada una de las producciones, cojo su parte izquierda, y si hay alguna otra produccion que en
		//su parte derecha sea la izquierda de antes, hago una nueva produccion con la parte derecha de la primera
		boolean añadido = true;
		while(añadido){
			añadido = false;		
			for (Production p1 : unitarys) {
				Symbol left = p1.getLeft();
				for (Production p2 : unitarys) {
					if (compareSymbols(left,p2.getRight().get(0))){
						Production newP = new Production(p2.getLeft(),p1.getRight());
						if (!contains(unitarys,newP) && !contains(aux,newP)){ 
								aux.add(newP);
								añadido = true;
							
						}
					}
				}	
			}
			unitarys.addAll(aux);
			aux = new ArrayList<Production>();
		}
	return unitarys;			
	}
	
	
	// EN LAS UNITARIAS NO ME PREOCUPO DE SI SON O NO ALCANZABLES		

	public Grammar notUnitary(){
		//producciones unitarias
		ArrayList<Production> unitarys = returnUnitarys();
		
		//producciones no unitarias (el resto)
		ArrayList<Production> notUnitarys = new ArrayList<Production>();	
		for (Production p : this.getP()) {
			if (!contains(unitarys,p))
				notUnitarys.add(p);
		}

		boolean añadido = true;
		ArrayList<Production> aux = new ArrayList<Production>(unitarys);	
		ArrayList<Production> auxAux = new ArrayList<Production>(unitarys);	
		
		//mientras siga tirando del hilo, para cada producion unitaria, cojo su parte derecha y si esta 
		// en la izquierda de laguna produccion, añado esa nueva produccion
		while(añadido){
			añadido = false;
			auxAux = new ArrayList<Production>(aux);
			aux = new ArrayList<Production>();	 
			for (Production pU : auxAux) {
				Production pAux = new Production(pU.getLeft(),pU.getRight());
				for (Production pG : notUnitarys) {
					if (pAux.getRight().size() != 0 && compareSymbols(pAux.getRight().get(0),pG.getLeft())){
						Production pAux1 = new Production(pAux.getLeft(),pG.getRight());
						if (!contains(aux,pAux1) && !contains(auxAux,pAux1) && !contains(notUnitarys,pAux1)){
							añadido = true;
							aux.add(pAux1);	
						}	
					}		
				}					
			}		
			notUnitarys.addAll(aux);
		}		
		return new Grammar(this.getV(), this.getT(), notUnitarys, this.getS());
	}
	
	
	
	public List<Production> artificialVariables(List<Production> productions, List<NoTerminal> noTerminals){
		//este metodo genera las variables auxiliares para los no terminales que lo precisen
		int count = 0;
		Production productionAux = null;
		ArrayList<Production> newProductionsAux = new ArrayList<Production>();
		//para cada produccion de las de la gramática
		for (Production production : productions) {
			// si la parte derecha tiene mas de un símbolo
			if (production.getRight().size()>1){
				ArrayList<Symbol> rightAux = (ArrayList<Symbol>) production.getRight();
				// para cada simbolo miro si alguno es un terminal
				for (int i = 0; i < rightAux.size(); i++) {
					if (this.getT().contains(rightAux.get(i))){
						//si es el primero que añado lo añado de forma diferente
						if (newProductionsAux.size() == 0){
							ArrayList<Symbol> listAux= new ArrayList<Symbol> ();
							listAux.add(rightAux.get(i));
							//este bucle es para que los nombres de las variables auxiliares no existan ya
							NoTerminal<String> aux = new NoTerminal<String>("A"+count);
							while(noTerminals.contains(aux)){
								count++;
								aux = new NoTerminal<String>("A"+count);
							}
							
							productionAux = new Production(aux ,listAux);
							newProductionsAux.add(productionAux);
						}
						else{
							//miro si ya  construi una variable auxiliar para ese no terminal y si no, lo añado
							if (!containsSymbolInRight(newProductionsAux,rightAux.get(i))){
								count ++;
								ArrayList<Symbol> listAux= new ArrayList<Symbol> ();
								listAux.add(rightAux.get(i));
								
								//este bucle es para que los nombres de las variables auxiliares no existan ya
								NoTerminal<String> aux = new NoTerminal<String>("A"+count);
								while(noTerminals.contains(aux)){
									count++;
									aux = new NoTerminal<String>("A"+count);
								}
								
								productionAux = new Production(aux,listAux);
								newProductionsAux.add(productionAux);
							}
						}
					}					
				}	
			}
		}	
		System.out.println(newProductionsAux);
		return newProductionsAux;
	}
	
	
	public List<Production> reemplazarAuxEnProduciones (List<Production> productionsVariablesAux, List<Production> productions, ArrayList<NoTerminal> noTerminals){
		// este metodo substituye las variables auxiliares en las produciones
		// y añade las auxiliares a las produccciones
		
		ArrayList<Production> productionsAux = new ArrayList<Production>();
		int intAux = 0;
		//este bucle es para inicializar productionsAux a productions que si no no funciona :S
		for (int i = 0; i < productions.size(); i++) {
			ArrayList<Symbol> listAux = new ArrayList<Symbol>(productions.get(i).getRight());
			Production aux = new Production(productions.get(i).getLeft(),listAux);
			productionsAux.add(aux);
			
		}
		
		// para cada produccion
		for (Production production : productionsAux) {
			// para cada simbolo de la parte derecha de la produccion
			for (int i = 0; i < production.getRight().size(); i++) {
				// busco en todas las producciones de variables auxiliares a ver si tengo que cambiar algo
				for (int j = 0; j < productionsVariablesAux.size(); j++) {
					// si hay que substituir substituyo
					intAux = wherecontainsSymbolInRight(productionsVariablesAux, production.getRight().get(i));
					if (intAux>=0)
						production.getRight().set(i, productionsVariablesAux.get(intAux).getLeft());	
				}				
			}			
		}
		
		//añado las auxiliares a las producciones y los simbolos no terminales
		for (Production p : productionsVariablesAux) {
			productionsAux.add(p);
			noTerminals.add(p.getLeft());
		}

		return productionsAux;
		
	} 
	
	//devuelve la posicion de la produccion en la lista de producciones
	public int searchForSymbols (List <Production> productions, Production p){
		for (int i = 0; i < productions.size(); i++) {
			if (productions.get(i).getRight().equals(p.getRight()))
				return i;
		}
		return -1;
	}
	
	
	
	public List<Production> variablesLongitudDerecha (List<Production> productions, ArrayList<NoTerminal> noTerminals){
		int count = 0;
		// en este array voy guardando las producciones que creo
		ArrayList<Production> productionsAux = new ArrayList<Production>();
		
		for (Production p : productions) {
			//para cada produccion mientras siga teniendo que hacer oepraciones
			while(p.getRight().size() > 2){
				List<Symbol> right = p.getRight();
				count++;
				NoTerminal<String> aux = new NoTerminal<String>("C"+count);
				while(noTerminals.contains(aux)){
					count++;
					aux = new NoTerminal<String>("C"+count);
				}
				//listaux va a contener las dos ultimas variables de la parte derecha
				//creo una nueva produccion y la añado a la lista
				// y borro las dos ultimas variables dela produccion original 
				//reemplazandolas por la nueva
				ArrayList<Symbol> listAux = new ArrayList<Symbol>();
				listAux.add(right.get(right.size()-2));
				listAux.add(right.get(right.size()-1));
				Production newProduction = new Production(aux,listAux);
				//si no existe lo añado
				if (!containsSymbolsInRight(productionsAux,newProduction.getRight()))
					productionsAux.add(newProduction);
				else{
					//si existe, busco como se llama el noterminal que deriva a ello y lo guardo
					int productionPosition = searchForSymbols(productionsAux,newProduction);
					aux = productionsAux.get(productionPosition).getLeft();
				}
				//como al borrar la longitud disminiye son dos -1
				p.getRight().remove(right.size()-1);
				p.getRight().remove(right.size()-1);
				p.getRight().add(aux);		
			}
		}			
		

		//añadimos los nuevos no terminales
		for (Production p : productionsAux) {
			noTerminals.add(p.getLeft());
		}
		
		//añadimos las producciones creadas
		productionsAux.addAll(productions);
		
		return productionsAux;
	}
	
	
	public List<Production> eliminarUnitariasTerminales (List<Production> producciones, List<Production> newVariables, List<NoTerminal> noTerminalList){
		//este metodo recorre la lista de producciones y para cada produccion Ax -> NoTerminal si en las producciones
		// el noTerminal solo aparece en partes de recha de longitud 1, se peude eliminar esa produccion 
		for (Production pn : newVariables) 
			for (Production p : producciones) {
				// si la variable esta y está sola, la reemplazo por le no terminal
				if (contains(p.getRight(),pn.getLeft())){
					if ( p.getRight().size() == 1){
						p.setRight(pn.getRight());
					}
				}
			}
		return producciones;
	}
	
	
	
	public Grammar toChomsky(){
		ArrayList<NoTerminal> noTerminals = new ArrayList<NoTerminal>(this.getV());
		//creo una lista de producciones que van de las nuevas variables a los simbolos terminales
		ArrayList<Production> newVariables = (ArrayList<Production>) artificialVariables(this.getP(),this.getV());
		//reemplazo las nuevas variables en las producciones originales
		List<Production> productionsReemplazadas = reemplazarAuxEnProduciones(newVariables,this.getP(),noTerminals);
		//compruebo que no haya las unitarias donde no tiene que haberlas
		List<Production> productionsSinUnitarias = eliminarUnitariasTerminales(productionsReemplazadas,newVariables, noTerminals);
		//para cada produccion con la right.size > 2 creo una nueva variable y todo eso
		List<Production> finalProductions = variablesLongitudDerecha(productionsSinUnitarias,noTerminals);		
		return new Grammar(noTerminals, this.getT(), finalProductions, this.getS());
	}
	

}
