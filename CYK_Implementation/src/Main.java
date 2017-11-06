import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) {

//		Grammar grammar = Parser.parseGrammar("test_files/example.txt");
		Grammar grammar = Parser.parseGrammar("test_files/example_wikipedia_2conjuntos_final.txt");
//		Grammar grammar = Parser.parseGrammar("test_files/example_clase.txt");
		grammar.printPrettyGrammar();

//		Set<Element>[][] cyk = cyk_algorithm.cyk(grammar, "el conejo se levantó a las cinco de la mañana");
		Set<Element>[][] cyk = cyk_algorithm.cyk(grammar, "she eats a fish with a fork");
//		Set<Element>[][] cyk = cyk_algorithm.cyk(grammar, "a a b a a");
//		Set<Element>[][] cyk = cyk_algorithm.cyk(grammar, "a b a a");
		System.out.println("\n\n");
		
		
		for (int i = 1; i < cyk.length; i++) 
			System.out.printf("%-15s", "     " + i);
		System.out.println("\n");
		
		
		for (int i = 0; i < cyk.length; i++) {
			System.out.print(cyk.length-i-1 + " ");
			for (int j = 0; j < cyk.length-1; j++) 
				if (j>i && cyk[i][j].equals(new HashSet<String>()))
					System.out.printf("%-15s", "   ######");
				else
					System.out.printf("%-15s", "   " +cyk[i][j]);
			System.out.println("\n");
		}
		
		
//		
//		for (int i = 1; i < cyk.length; i++) 
//			System.out.printf("%-20s", "     " + i);
//		System.out.println("\n");
//		
//		
//		for (int i = 0; i < cyk.length; i++) {
//			System.out.print(cyk.length-i-1 + " ");
//			for (int j = 0; j < cyk.length-1; j++) 
//				if (j>i && cyk[i][j].equals(new HashSet<String>()))
//					System.out.printf("%-20s", "   ######");
//				else{
//					Iterator iterator = cyk[i][j].iterator();
//					while(iterator.hasNext()){
//						Element next = (Element) iterator.next();
//						System.out.printf("%-20s",next.value + ", " + next.getI() + ", " +  next.getJ());
//
//					}
//					
//				}
//			System.out.println("\n");
//		}
//		
		
		
		
		
	}		
}
