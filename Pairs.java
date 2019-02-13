package io;

public class Pairs implements Comparable<Pairs> {

	private char character;
	private int length;
	
	public Pairs(int l, char c) {
		length = l;
		character = c;
	}
	
	public int getLength() {
		return length;
	}
	public char getCharacter() {
		return character;
	}
	@Override
	public int compareTo(Pairs p) {
		if (this.length == p.length) {
			return this.character - p.character;
		} else {
			return this.length - p.length;
		}
	}
	
}
