import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.naming.LimitExceededException;

public class cyk_algorithm {
	
	//wich es un string con cosas como 1001, que nos indica que elemento cojer del set para continuar con ese posible arbol
	public static void generateTree(Tree tree, Element element, Set<Element>[][] matriz,String wich){
		//guardo las coordenadas de los hijos
		int II = element.getI().getI();
		int IJ = element.getI().getJ();
		int JI = element.getJ().getI();
		int JJ = element.getJ().getJ();
		Tree izdo = null;
		Tree derecho = null;
		//si tiene hijos
		if (!(II == 0  && JI == 0)){
			//si tiene mas de un elemento, añado el que dice el parametro wich
			if (matriz[II][IJ].size() >= 2 && !wich.equals("")){
				izdo = new Tree(matriz[II][IJ].toArray()[Integer.parseInt(wich.substring(0,1))].toString());	
				wich = wich.substring(1);
			}
			else
				//sino, lo añado a pelo
				if (matriz[II][IJ].toArray().length != 1)
					izdo = new Tree(matriz[II][IJ].toString());
				else
					izdo = new Tree(matriz[II][IJ].iterator().next().toString());

			if (matriz[JI][JJ].size() >= 2 && !wich.equals("")){
				derecho = new Tree(matriz[JI][JJ].toArray()[Integer.parseInt(wich.substring(0,1))].toString());	
				wich = wich.substring(1);
			}
			else
				if (matriz[JI][JJ].toArray().length != 1)
					derecho = new Tree(matriz[JI][JJ].toString());
				else
					derecho = new Tree(matriz[JI][JJ].iterator().next().toString());

			//guardo los hijos del arbol
			tree.setIzdo(izdo);
			tree.setDerecho(derecho);
			//y hago la llamada recursiva
			generateTree(tree.getIzdo(), (Element) matriz[II][IJ].iterator().next(), matriz,wich);
			generateTree(tree.getDerecho(), (Element) matriz[JI][JJ].iterator().next(), matriz,wich);
		}
	}
	
	
	
	
	public static void limpiarTablaP(Set<Element> matrizFinal [][],String thisName, String padre, Element thisElement, Set<Element> matriz [][], int i, int j, Grammar grammar){
	
		if (i != matriz.length && j != matriz[0].length)
			if ((i != 0 || j != 0 ) || thisName.equals(grammar.getS().getValue())){
				
				Element rightE = null;
				Element leftE = null;
				
				for (Production p : grammar.getP()) {
					if (p.getRight().size() == 2){
						if (thisName.equals(p.getLeft().toString().trim())){
							if (p.getLeft().equals(grammar.getS()))
								matrizFinal[i][j].add(new Element (p.getLeft().toString().trim(), thisElement.getI(), thisElement.getJ()));
													
							HashSet<Element>  left = (HashSet<Element>) matriz[thisElement.getI().getI()][thisElement.getI().getJ()];
							HashSet<Element> right = (HashSet<Element>) matriz[thisElement.getJ().getI()][thisElement.getJ().getJ()];
							for (Element element : left) 
								if (element.getValue().equals(p.getRight().get(0).toString())){
									leftE = element;
									matrizFinal[thisElement.getI().getI()][thisElement.getI().getJ()].add(leftE);
								}

							for (Element element : right) 
								if (element.getValue().equals(p.getRight().get(1).toString())){
									rightE = element;
									matrizFinal[thisElement.getJ().getI()][thisElement.getJ().getJ()].add(rightE);
								}
						}
					}
				}
				
		
				if (leftE != null)
					limpiarTablaP(matrizFinal, leftE.getValue(), thisName, leftE, matriz,leftE.getI().getI(),leftE.getI().getJ(), grammar);
				if (rightE != null)
					limpiarTablaP(matrizFinal, rightE.getValue(), thisName, rightE, matriz,rightE.getJ().getI(),rightE.getJ().getJ(), grammar);

			}
	}
	
	


	@SuppressWarnings("unchecked")
	public static Set<Element>[][] cyk (Grammar grammar, String entrada){		
		//leo la entada y la parseo teniendo en cuenta que si aparece un se, se considera a esa y la siguiente palabra una sola
		//por lo que terms vale uno menos por cada "se"
		String[] palabras = entrada.split(" ");
		int terms = palabras.length;
		for (String string : palabras) 
			if (string.equals("se"))
				terms--;

		//creo la matriz y la relleno de elementos neutarles
		@SuppressWarnings({ "rawtypes" })
		Set [][] matriz =(Set<Element> [][]) new Set<?>[terms+1][terms];
		for (int i = 0; i <= terms; i++) 
			for (int j = 0; j < terms; j++) 
				matriz[i][j] = new HashSet<Element>();
		
		//ahora relleno la ultima fila de la matriz con la entrada
		int g = 0;
		for (int i = 0; i < terms; i++){
			if (palabras[i].equals("se")){
				g++;
				matriz[terms][i].add(new Element(palabras[i] + " " + palabras[i+g],new Tuple(0,0),new Tuple(0,0)));
			}
			else
				matriz[terms][i].add(new Element(palabras[i+g],new Tuple(0,0),new Tuple(0,0)));
		}
		//relleno la penultima fila, asignando a cada terminal el noterminal del que deriva
		for (int i = 0; i < terms; i++) 
			for (int j = 0; j < grammar.getP().size(); j++) 
				for (int k = 0; k < grammar.getP().get(j).getRight().size(); k++) 
					if (matriz[terms][i].iterator().next().toString().equals(grammar.getP().get(j).getRight().get(k).toString()))
						matriz[terms-1][i].add(new Element(grammar.getP().get(j).getLeft().toString().trim(),new Tuple(0,i),new Tuple(0,i)));
					
				
		//AHORA EMPIEZA EL ALGORITMO CYK
		//compara en diagonal
		//primer bucle para las filas
		for (int i = terms-2; i >= 0 ; i--) {
			//segundo bucle para las columnas
			for (int j = 0; j <= i; j++) {
				//tercer bucle para cada casilla, mirar las de debajo y las laterales a lad erecha
				for (int x = 1; x + i < terms && x + j < terms; x++) {
					//para revisar todas las producciones
					for (Production p : grammar.getP()) {
						//siempre si es mayor que dos (por las que van a terminal)
						if (p.getRight().size() == 2){
							Iterator<?> iterator = matriz[terms-x][j].iterator();
							//recorro todos los elementos que hay en esa casilla
							while(iterator.hasNext()){
								Object a = (Element) iterator.next();
								//si coincide con alguna produccion, sigo
								if (p.getRight().get(0).getValue().equals(a.toString())){
									Iterator<?> iterator2 = matriz[i+x][j+x].iterator();
									//para ese elemento, recorro todos los de la otra casilla
									while(iterator2.hasNext()){
										Object b = (Element) iterator2.next();
										if (p.getRight().get(1).getValue().toString().equals(b.toString())){
											matriz[i][j].add(new Element(p.getLeft().toString().trim(), new Tuple(terms-x,j), new Tuple(i+x,j+x)));
										}	
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (matriz[0][0].size() == 0){
			System.out.println("NO PERTENECE!");
			return matriz;
		}

		boolean imprimir = true;
		if (imprimir){
			
			//VUELVO A DECLARAR LA MATRIZ
			//creo la matriz y la relleno de elementos neutarles
			@SuppressWarnings({ "rawtypes" })
			Set [][] matriz1 =(Set<Element> [][]) new Set<?>[terms+1][terms];
			for (int i = 0; i <= terms; i++) 
				for (int j = 0; j < terms; j++) 
					matriz1[i][j] = new HashSet<Element>();
			
			//ahora relleno la ultima fila de la matriz con la entrada
			g = 0;
			for (int i = 0; i < terms; i++){
				if (palabras[i].equals("se")){
					g++;
					matriz1[terms][i].add(new Element(palabras[i] + " " + palabras[i+g],new Tuple(0,0),new Tuple(0,0)));
				}
				else
					matriz1[terms][i].add(new Element(palabras[i+g],new Tuple(0,0),new Tuple(0,0)));
			}

			
			//limpio la tabla de combinaciones imposibles
			limpiarTablaP(matriz1,"S", "S",(Element) matriz[0][0].iterator().next(), matriz, 0, 0, grammar);
			
			//imprimo la tabla limpia
			System.out.println("        TABLA LIMPIA        ");
			for (int i = 1; i < matriz1.length; i++) 
				System.out.printf("%-15s", "     " + i);
			System.out.println("\n");
			for (int i = 0; i < matriz1.length; i++) {
				System.out.print(matriz1.length-i-1 + " ");
				for (int j = 0; j < matriz1.length-1; j++) 
					if (j>i && matriz1[i][j].equals(new HashSet<String>()))
						System.out.printf("%-15s", "   ######");
					else
						System.out.printf("%-15s", "   " +matriz1[i][j]);
				System.out.println("\n");
			}
			
			
			/******** AHORA PARA TODAS LAS COMBIANCIONES DE ARBOLES ********/
			
			ArrayList<Integer> sizeCellsBigg = new ArrayList<Integer>();

			//recorro toda la matriz y donde haya mas de un elemento, añado a sizecellsbig
			for (int i = 0; i <= terms; i++) 
				for (int j = 0; j < terms; j++) 
					if (matriz1[i][j].size() > 1)
						sizeCellsBigg.add(matriz1[i][j].size());
			
			//y calculo el numero de combinaciones posibles
			int numCombinations = 1;
			for (int i : sizeCellsBigg) 
				numCombinations *= i;
				
			//es en aux donde voy a guardar los valores de las combinaciones
			String aux [][] = new String [sizeCellsBigg.size()][numCombinations];
		
			//sólo si hay que hacer combinaciones:
			if (sizeCellsBigg.size() > 0 ){
				int size = numCombinations;
				//relleno todas las filas con los cachos de las combinaciones, que más adelante iré concatenando
				for (int z = 0; z < sizeCellsBigg.size(); z++) {
					size/= sizeCellsBigg.get(z);
					for (int j = 0; j < sizeCellsBigg.get(z); j++) 	
						for (int i = 0; i < size; i++) 
							aux[z][j * size + i] = String.valueOf(j);
				}
				
				//sólo hago esto si hay mas de un nodo con varias posibilidades, si no, ya me vale
				if (sizeCellsBigg.size() > 1 ){
					//ahora voy concatenando a cada fila, los elementos de la fila siguiente
					size *= sizeCellsBigg.get(sizeCellsBigg.size()-2);
					for (int z = sizeCellsBigg.size() -1; z > 0 ; z--) {
						size *= sizeCellsBigg.get(z);
						for (int i = 0; i < size; i++) 
							if (i < numCombinations)
							aux[z-1][i] += aux[z][(i % (size / sizeCellsBigg.get(z-1)))];					
					}
				}
				
				//imprimo como quedaría finalmente:
				for (int i = 0; i < aux.length; i++) {
					for (int j = 0; j < aux[0].length; j++) 
						if (aux[i][j] != null)
							System.out.print(aux[i][j] + " ");
					System.out.println();
				}
				System.out.println("\n");
			}	
			
			/******	 FINALMENTE GENERO E IMPRIMO LOS ARBOLES *******/
			
			Element primero = (Element) matriz1[0][0].iterator().next();		
			Tree tree = new Tree(primero.toString());
			generateTree(tree, primero, matriz1,"");
			tree.printNode(tree);
			//si hay mas de un arbol, hago un bucle y voy generando, imprimiendo
			if (numCombinations > 1){
				String combinations[] = aux[0];
				for (String combination : combinations) {
					generateTree(tree, primero, matriz1,combination);
					tree.printNode(tree);
				}
			}	
		}
		return matriz;
	}	
}





