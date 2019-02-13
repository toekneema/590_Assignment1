package io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class VarianceTree { //used for encoder
	
	//construct varianceTree to get the code lengths, then use lengths to construct Canonical Tree, using tree from part1
	
	private int height;
	private NodeE rootNode = new NodeE();
	private ArrayList<NodeE> allTrees = new ArrayList<NodeE>(); //nodes are combine to become Trees, then stored in this ArrayList to be sorted
	ArrayList<NodeE> leafNodes = new ArrayList<NodeE>(); //used to construct the Canonical tree since its easy to loop thru
	
	private int debug = 0; //debugging
	
	public VarianceTree(ArrayList<PairsEncoder> lengths) { //why is this arrayList only 83 long
		System.out.println("lengths size: " + lengths.size());
		
		for (int i = 0; i < lengths.size(); i++) { //works
			allTrees.add(new NodeE(lengths.get(i))); //created a new list of all leaf Nodes
		}
		
		System.out.println("allTrees size: " + allTrees.size()); //debugging
		
		//this might be better looping logic
		while (allTrees.size() > 1) {
			
			NodeE parent = new NodeE(allTrees.get(0), allTrees.get(1));
			parent.calcFreq();
			parent.calcHeight();			
			
			parent.setHeight(parent.getHeight() + 1); //maybe the height is making the sort fucked up
			
			
			System.out.println("yo: " + parent.getFreq()); //check this to see if the sort is working
			System.out.println(parent.getHeight());
			
			allTrees.remove(1); //removes the first two items in arraylist
			allTrees.remove(0);
			allTrees.add(parent); //add the pair/node back into the ArrayList
			Collections.sort(allTrees); //DEFINITELY NOT SORTING CORRECTLY
		}
		
		//set the lengths here
		allTrees.get(0).setLengths(0);
		
		//set the rootNode to the only element in allTrees
		rootNode = allTrees.get(0);		
	}
	
	public NodeE getRoot() {
		return rootNode;
	}
	
	public ArrayList<NodeE> getLeafArray() {
		return leafNodes;
	}

	public void printLeafNodes(NodeE node) { //debugging to print out the tree
		if (node == null) {
			return;
		}
		if (node.left == null && node.right == null) { //prints the leaf
			leafNodes.add(node);
			System.out.println("ughh: " + debug + ": " + node.getChar()); //why is the pair null?
			debug++;
		}
		printLeafNodes(node.left); printLeafNodes(node.right);
	}
}
