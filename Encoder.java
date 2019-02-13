package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Encoder {
		
	public static void main(String[] args) throws IOException {
		
		//input source
		String file = "C:\\Users\\Tony\\decoded.txt";
		InputStream iS = new FileInputStream(file);
		InputStreamBitSource iSBS = new InputStreamBitSource(iS);
		
		//output source
		OutputStream oS = new FileOutputStream("C:\\Users\\Tony\\compressedFileAfterEncoding.dat");
		OutputStreamBitSink oSBS = new OutputStreamBitSink(oS);
		
		ArrayList<PairsEncoder> lengths = new ArrayList<PairsEncoder>();
		Map<Character, Integer> numOfAppears = new HashMap<Character, Integer>(); //only used once
		
		//first need to populate numOfAppears with all 256 ASCII symbols
		for (int i = 0; i < 256; i++) {
			numOfAppears.put((char)i, 0);
		}
		
		//need to read first 256 bytes now
		int totalChars = 0;
		BufferedReader bR = new BufferedReader(new FileReader(file));
		char currentCharacter;
		while ((currentCharacter = (char) bR.read())!= '\u0000') { //loops until it is null, maybe this logic is wrong, idk i'll come back to it later
			totalChars++;
			if(numOfAppears.containsKey(currentCharacter)) {
				numOfAppears.put(currentCharacter, numOfAppears.get(currentCharacter) + 1);
			} else {
				numOfAppears.put(currentCharacter, 1);
			}
		}
		
		
		
		for(Map.Entry<Character, Integer> entry : numOfAppears.entrySet()) { //put all the frequencies and characters from hashmap into the ArrayList lengths. use foreach loop
			lengths.add(new PairsEncoder(entry.getValue(), entry.getKey()));
		}
		Collections.sort(lengths); //need this to sort the list of frequencies
		
		double[] probabil = new double[256];
		for (int i = 0; i < 256; i++) { //for Part 3.2
			probabil[i] = (double)lengths.get(i).getFreq() / (double)totalChars;
			//System.out.println("Probability of " + lengths.get(i).getCharacter() + ": " + probabil[i]);
		}
		double entropy = 0.0;
		for (int i = 0; i < 256; i++) { //for Part 3.3
			double prob = probabil[i];
			if (prob > 0.0000000000000000000000001) {
				entropy += (double)(-1)*prob*Math.log(prob);
			}
		}
		System.out.println("ques 3: " + entropy);
		
		double compressedEntropy = 4599968/574992; //for Part 3.4
		System.out.println(compressedEntropy);
		
		VarianceTree vt = new VarianceTree(lengths); //not creating properly
		vt.printLeafNodes(vt.getRoot()); //debugging
		System.out.println("fucked up: " + vt.getLeafArray().size());
		ArrayList<NodeE> leafNodes = vt.getLeafArray();
		//turn the variancetree into a huffman tree.

		ArrayList<Pairs> lengthsForCT = new ArrayList<Pairs>();
		
		for (int i = 0; i < 256; i++) { //debugging
			lengthsForCT.add(new Pairs(leafNodes.get(i).getLength(), leafNodes.get(i).getChar()));
//			System.out.println("lengths!!!!!!!!!!!!!: " + leafNodes.get(i).getLength());
		}
		Collections.sort(lengthsForCT);
		for (int i = 0; i < 256; i++) { //debugging after the sort
			System.out.println(lengthsForCT.get(i).getCharacter());
			System.out.println("LENGTHS!!!!!!!!!!!!!: " + lengthsForCT.get(i).getLength());
			
		}
		System.out.println("hello?: " + lengthsForCT.size()); //debugging
		
		CanonicalTree newCT = new CanonicalTree(lengthsForCT);
		newCT.printLeafNodes(newCT.getRoot());
		
		
		//first 256 bytes
		for (int i = 0; i < 256; i++) {
			oSBS.write(lengthsForCT.get(i).getLength(), 8); //could this be wrong?

		}
		//output next 4 bytes
		oSBS.write(totalChars, 32);
		
		//output the rest of the file
		HashMap<Character, String> chart = newCT.getStrings(); //gives values of each key

		//i should be using a HashMap and entering the character that I read in, and then giving it the corresponding code
		BufferedReader bR2 = new BufferedReader(new FileReader(file));
		char currentCharacter2;
		for (int i = 0; i < totalChars; i++) {
			currentCharacter2 = (char) bR2.read();
			oSBS.write(chart.get(currentCharacter2));
			System.out.println("PELASEEEEEEEE: " + currentCharacter2);
		}
		
		//pad
		oSBS.padToWord();
	}
	
}
