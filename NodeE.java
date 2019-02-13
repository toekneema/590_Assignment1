package io;

public class NodeE implements Comparable<NodeE> { //used for encoder
	NodeE left;
	NodeE right;
	private int height;
	private int freq;
	PairsEncoder p;
	private int length;
	
	public NodeE() {
		p = null;
		height = 0;
	}

	public NodeE (PairsEncoder pair) {
		p = pair;
		height = 0;
	}
	
	public NodeE (NodeE left, NodeE right) { //for encoder, parent nodes
		this.left = left;
		this.right = right;
		p = new PairsEncoder(left.getFreq() + right.getFreq(), '-');
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getFreq() {
		if (p == null) {
			return freq;
		} else {
			return p.getFreq();
		}
	}

	public char getChar() {
		if (p == null) {
			return '-';
		} else {
			return p.getCharacter();
		}
	}
	
	public int getLength() {
		return length;
	}
	
	public void calcHeight() {
		
		NodeE curr = this;
		int l = 0;
		int r = 0;
		if (left != null) {
			l = left.height;
		}
		if (right != null) {
			r = right.height;
		}
		height = Math.max(l, r);
	}

	public void calcFreq() {
		int l = 0;
		int r = 0;
		if (left != null) {
			r = right.getFreq();
		}
		if (right != null) {
			r = right.getFreq();
		}
		freq = l + r;
	}
	
	public boolean isLeaf() {
		if (left == null && right == null) {
			return true;
		} else {
			return false;
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

	public void setLengths(int length) {
		if (left != null && right != null) {
			left.setLengths(length + 1);
			right.setLengths(length + 1);
		} else if (left != null) {
			left.setLengths(length + 1);
		} else if (right != null) {
			right.setLengths(length + 1);
		} else {
			this.length = length;
		}
	}
	
	@Override
	public int compareTo(NodeE o) {   //might be fucking things up
		
		//how to account for the ties in frequency
		if (this.freq == o.getFreq()) {
			if (this.height == o.getHeight()) {
				return this.getChar() - o.getChar();
			} else {
				return this.height - o.getHeight();
			}
		} else {
			return this.freq - o.getFreq();
		}
	}
	
}
