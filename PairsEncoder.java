package io;

public class PairsEncoder implements Comparable<PairsEncoder> {
	
	public char character;
	public int freq;
	
	public PairsEncoder(int f, char c) {
		character = c;
		freq = f;
	}
	
	public int getFreq() {
		return freq;
	}
	public char getCharacter() {
		return character;
	}
	public void setChar(char c) {
		character = c;
	}
	
	@Override
	public int compareTo(PairsEncoder p) {
		if (this.freq == p.freq) {
			return this.character - p.character;
		} else {
			return this.freq - p.freq;
		}
	}
}
