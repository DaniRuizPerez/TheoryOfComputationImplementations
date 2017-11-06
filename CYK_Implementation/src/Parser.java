import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Parser {
	
	
	public static boolean contains(List<NoTerminal> list, String value){
		for (int i = 0; i < list.size(); i++) 
			if (list.get(i).getValue().toString().equals(value))
				return true;
		return false;	
	}
	
	public static boolean containsT(List<Terminal> list, String value){
		for (int i = 0; i < list.size(); i++) 
			if (list.get(i).getValue().toString().equals(value))
				return true;
		return false;	
	}
	
	
	public static ArrayList<String> parseFile(String fileE){

		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(fileE));
		} catch (FileNotFoundException e) {
			System.out.println("Error en el fichero");
		}
		
		String line;
		ArrayList<String> totalLines = new ArrayList();
		try {
			while ((line = bf.readLine())!=null) {
				totalLines.add(line);
				}
		} catch (IOException e) {
			System.out.println("Error al parsear el fichero");
		}
		
		return totalLines;
	}
	
	
	
	public static Grammar parseGrammar(String file) {
	
	ArrayList<String> linesEntry = parseFile(file);
		
	// NO TERMINALES
	ArrayList<NoTerminal> noTerminals = new ArrayList<NoTerminal>();
	String[] lineNoTerminals = linesEntry.get(0).split(":")[1].split("-");
	
	for (int i = 0; i < lineNoTerminals.length; i++) {
		if (!lineNoTerminals[i].equals("") && !lineNoTerminals[i].equals("|")){
			Integer num = null;
			try{
				num = Integer.parseInt(lineNoTerminals[i]);
				NoTerminal<Integer> aux = new NoTerminal<Integer> (num);
				noTerminals.add(aux);
			}catch (NumberFormatException e){
				NoTerminal<String> aux = new NoTerminal<String> (lineNoTerminals[i]);
				noTerminals.add(aux);
			}
		}
	}	
	
	// TERMINALES
	ArrayList<Terminal> terminals = new ArrayList<Terminal>();
	String[] lineTerminals = linesEntry.get(1).split(":")[1].split("-");
	
	for (int i = 0; i < lineTerminals.length; i++) {
		if (!lineTerminals[i].equals("") && !lineTerminals[i].equals("|")){
			Integer num = null;
			try{
				num = Integer.parseInt(lineTerminals[i]);
				Terminal<Integer> aux = new Terminal<Integer> (num);
				terminals.add(aux);
			}catch (NumberFormatException e){
				Terminal<String> aux = new Terminal<String> (lineTerminals[i]);
				terminals.add(aux);
			}
			
		}
	}

	//PRODUCCIONES
	ArrayList<Production> productions = new ArrayList<Production>();
	for (int i = 2; i < linesEntry.size(); i++) {
		String[] split = linesEntry.get(i).split("->");
		String left = split[0];
		Integer num = null;

		ArrayList<Symbol> symbolsRight = new ArrayList<Symbol>();
		String[] splitRight = split[1].split("-");

		for (int j = 0; j < splitRight.length; j++) {
				if (!splitRight[j].equals("") && !splitRight[j].equals("|")){	
					if(containsT(terminals,splitRight[j])){
						try{
							num = Integer.parseInt(splitRight[j]);
							Terminal<Integer> aux = new Terminal<> (num);	
							symbolsRight.add(aux);
						}catch (NumberFormatException e){
							Terminal<String> aux = new Terminal<String> (splitRight[j]);
							symbolsRight.add(aux);
						}
					}
					else if (contains(noTerminals,splitRight[j])){
						try{
							num = Integer.parseInt(splitRight[j]);
							NoTerminal<Integer> aux = new NoTerminal<> (num);	
							symbolsRight.add(aux);
						}catch (NumberFormatException e){
							NoTerminal<String> aux = new NoTerminal<String> (splitRight[j]);
							symbolsRight.add(aux);
						}
					}	
				}
		}
		
		Production production = null;
		try{
			num = Integer.parseInt(left);
			NoTerminal<Integer> noterminalLeft = new NoTerminal<Integer> (num);
			production = new Production(noterminalLeft,symbolsRight);
		}catch (NumberFormatException e){
			NoTerminal<String> noterminalLeft = new NoTerminal<String> (left);
			production = new Production(noterminalLeft,symbolsRight);
		}
		if (!productions.contains(production))
			productions.add(production);
	}
	
	
	Grammar grammar = new Grammar(noTerminals,terminals,productions,noTerminals.get(0));
	
	return grammar;
	}
	
	/*USAGE
	 * 
	 * NoTerminales : S A B C X D 5
Terminales : a b 1
S -> A B 1 
S -> A C 
S -> b A
A -> a
B -> b
C -> D
5 -> a a b c 1


el simbolo inicial es el primero de los noterminales
	 * 
	 * epsilon se representa como #
	 * 
	 * */
	
	
	
}
