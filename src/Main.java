import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) {

		System.out.println("ORIGINAL GRAMMAR");
		Grammar grammar = Parser.parseGrammar("test_files/grammarChomskyBook.txt");
		grammar.printPrettyGrammar();
/*
		System.out.println("\n\n\n GENERATORS");
		Grammar generatorsGramar = grammar.onlyGenerators();
		generatorsGramar.printPrettyGrammar();
		
		System.out.println("\n\n\n REACHABLES");
		Grammar reachedGrammar = grammar.onlyReachables();
		reachedGrammar.printPrettyGrammar();
		
		System.out.println("\n\n\n NOEPSILON");
		Grammar notEpsilonGrammar = grammar.notEpsilon();
		notEpsilonGrammar.printPrettyGrammar();
		
		System.out.println("\n\n\n NOUNITARY");
		Grammar notUnitaryGrammar = grammar.notUnitary();
		notUnitaryGrammar.orderProductions();
		notUnitaryGrammar.printPrettyGrammar();

		System.out.println("\n\n\n TOCHOMSKY");
		Grammar chomskyGrammar = grammar.toChomsky();
		chomskyGrammar.printPrettyGrammar();
	*/
		System.out.println("\n\n\n ALLGRAMMAR");
		Grammar allGrammar = grammar.notEpsilon();
		allGrammar.printPrettyGrammar();
		allGrammar = allGrammar.notUnitary();
		allGrammar.printPrettyGrammar();
		allGrammar = allGrammar.onlyGenerators();
		allGrammar.printPrettyGrammar();
		System.out.println("ahroa vamos a reachables");
		allGrammar = allGrammar.onlyReachables();
		allGrammar.printPrettyGrammar();
		System.out.println("ahroa vamos a chomsky");
		allGrammar = allGrammar.toChomsky();
		allGrammar.printPrettyGrammar();
		
		System.out.println(" \n\n\n\n\n\n\n\n\n");
		grammar.printPrettyGrammar();
		allGrammar = grammar.notEpsilon().notUnitary().onlyGenerators().onlyReachables().toChomsky();
		allGrammar.printPrettyGrammar();
	}		
}
