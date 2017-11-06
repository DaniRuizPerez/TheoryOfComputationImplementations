import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Tree {

	private String raiz;
	private Tree izdo = null;
	private Tree derecho = null;
	
	

    public <T extends Comparable<?>> void printNode(Tree root) {
        int maxLevel = this.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private <T extends Comparable<?>> void printNodeInternal(List<Tree> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || this.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        this.printWhitespaces(firstSpaces);

        List<Tree> newNodes = new ArrayList<Tree>();
        for (Tree node : nodes) {
            if (node != null) {
                System.out.print(node.raiz);
                newNodes.add(node.izdo);
                newNodes.add(node.derecho);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            Tree.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
            	Tree.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                	Tree.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).izdo != null)
                    System.out.print("/");
                else
                	Tree.printWhitespaces(1);

                Tree.printWhitespaces(i + i - 1);

                if (nodes.get(j).derecho != null)
                    System.out.print("\\");
                else
                	Tree.printWhitespaces(1);

                Tree.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private  <T extends Comparable<?>> int maxLevel(Tree node) {
        if (node == null)
            return 0;

        return Math.max(this.maxLevel(node.izdo), this.maxLevel(node.derecho)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

	

    public Tree copy() {
    	Tree left = null;
    	Tree right = null;
        if (this.izdo != null) 
            left = this.izdo.copy();
        if (this.derecho != null) 
            right = this.derecho.copy();
        return new Tree(raiz, left, right);
    }

	
	
	
	
	
	
	

	
	public Tree(String raiz, Tree izdo, Tree derecho) {
		this.raiz = raiz;
		this.izdo = izdo;
		this.derecho = derecho;
	}
	public Tree(String raiz) {
		this.raiz = raiz;
	}
	public String getRaiz() {
		return raiz;
	}
	public void setRaiz(String raiz) {
		this.raiz = raiz;
	}
	public Tree getIzdo() {
		return izdo;
	}
	public void setIzdo(Tree izdo) {
		this.izdo = izdo;
	}
	public Tree getDerecho() {
		return derecho;
	}
	public void setDerecho(Tree derecho) {
		this.derecho = derecho;
	}
}
