package io;

import java.util.ArrayList;
import java.util.HashMap;

public class CanonicalTree {
	
	private Node rootNode = new Node();
	
	private int debug = 0; //debugging the printTree method

	private HashMap<Character, String> chart = new HashMap<Character, String>();
	
	
	public CanonicalTree(ArrayList<Pairs> lengths) {
		String binary = ""; //not necessary, just for debugging
		
		for (int i = 0; i < lengths.size(); i++) { //going thru to create the Nodes for the tree
			binary = "";
			Node current = rootNode;
			
			for (int j = 0; j < lengths.get(i).getLength(); j++) {

				if (j == lengths.get(i).getLength()-1) {
					//places nodes with leaf
					if (current.left == null) {
						current.left = new Node(lengths.get(i)); //change back to lengths.get(i)
						binary += "0";
						chart.put(current.left.p.getCharacter(), binary);
					} else {
						current.right = new Node(lengths.get(i)); //lengths.get(i) is what i had before
						binary += "1";
						chart.put(current.right.p.getCharacter(), binary);
					}
				} else if (current.left == null) {
					current.left = new Node(); //setting the newly created node as the left child
					current = current.left; //passing the current status to the newly created node
					binary+="0";
				} else if (!current.left.isFull() && !current.left.isLeaf()) {
					//when there is already a node there, just traverse the tree
					current = current.left;
					binary += "0";
				} else if (current.right == null) {
					current.right = new Node();
					current = current.right;
					binary += "1";
				} else {
					current = current.right;
					binary += "1";
				} 
			}
		}
	}
	
	public Node getRoot() {
		return rootNode;
	}
	
	public HashMap<Character, String> getStrings() {
		return chart;
	}
	
	public void printLeafNodes(Node node) { //works
		if (node == null) {
			return;
		} 
		if (node.left == null && node.right == null) {
			System.out.println(debug + ": " + node.getChar());
			debug++;
		}
		printLeafNodes(node.left); printLeafNodes(node.right);
	}

		
}
