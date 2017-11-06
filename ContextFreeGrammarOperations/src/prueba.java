import static org.junit.Assert.*;

import org.junit.Test;


public class prueba {

	@Test
	public void testGenerators() {
		Grammar generators = Parser.parseGrammar("test_files/generators.txt");
		generators = generators.onlyGenerators();
		Grammar generatorsS = Parser.parseGrammar("test_files/generatorsS.txt");
		assertEquals(generators, generatorsS);
		System.out.println("\n");
	}

	@Test
	public void testReachables() {
		Grammar reachables = Parser.parseGrammar("test_files/reachables.txt");
		reachables =  reachables.onlyReachables();
		Grammar reachablesS = Parser.parseGrammar("test_files/reachablesS.txt");
		assertEquals(reachables, reachablesS);
		System.out.println("\n");
	}

	@Test
	public void testEpsilon() {
		Grammar noEpsilon = Parser.parseGrammar("test_files/noEpsilon.txt");
		noEpsilon =  noEpsilon.notEpsilon();
		Grammar noEpsilonS = Parser.parseGrammar("test_files/noEpsilonS.txt");
		assertEquals(noEpsilon, noEpsilonS);
		System.out.println("\n");
	}
	

	@Test
	public void testUnitarys() {
		Grammar notUnitarys = Parser.parseGrammar("test_files/notUnitarys.txt");		
		notUnitarys =  notUnitarys.notUnitary();
		Grammar notUnitarysS = Parser.parseGrammar("test_files/notUnitarysS.txt");
		assertEquals(notUnitarys, notUnitarysS);
		System.out.println("\n");
	}
	

	@Test
	public void testChomsky() {
		Grammar chomsky = Parser.parseGrammar("test_files/chomsky.txt");	
		chomsky =  chomsky.toChomsky();
		Grammar chomskyS = Parser.parseGrammar("test_files/chomskyS.txt");
		assertEquals(chomsky, chomskyS);
		System.out.println("\n");
	}
	
	
	@Test
	public void testAll() {
		Grammar allGrammar = Parser.parseGrammar("test_files/allGrammar.txt");	
		allGrammar = allGrammar.notEpsilon().notUnitary().onlyGenerators().onlyReachables().toChomsky();
		Grammar allGrammarS = Parser.parseGrammar("test_files/allGrammarS.txt");
		assertEquals(allGrammar, allGrammarS);
		System.out.println("\n");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
