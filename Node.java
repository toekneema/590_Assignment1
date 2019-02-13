package io;

public class Node { //used for decoder 
	Node left = null;
	Node right = null;
	
	Pairs p; //symbol associated with leafs
	
	public Node() {
		p = null;
	}
	
	public Node (Pairs pair) {
		p = pair;
	}
	
	public boolean isLeaf() {
		
		if (p == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public char getChar() {
		if (p == null) {
			return '-';
		} else {
			return p.getCharacter();
		}
	}
	
	public boolean isFull() {
		//check if the bottom is all full of leaves, so use the isLeaf() method, recursive
		//travel to bottom, then if any are not leaves, then it's not full, if they're all leaves then it's full
		
		if(isLeaf()) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.isLeaf() && right.isLeaf()) {
			return true;
		}
		return (left.isFull() && right.isFull());	
	}
}
