package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Decoder {
	
	
	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
		
		ArrayList<Pairs> lengths = new ArrayList<Pairs>();
		int totalPairs; //next 4 bytes after the first 256
		
		String file = "C:\\Users\\Tony\\compressedFileAfterEncoding.dat";
		InputStream iS = new FileInputStream(file);
		InputStreamBitSource iSBS = new InputStreamBitSource(iS);
		
		for(int i = 0; i < 256; i++) { //reads first 256 bytes
			lengths.add(new Pairs(iSBS.next(8), Character.toString((char)i).charAt(0)));
		}
		Collections.sort(lengths);
		
		
		totalPairs = iSBS.next(32); //reads next 4 bytes
		

		for(int i = 0; i < 256; i++) { //debugging tool
			System.out.println(lengths.get(i).getCharacter() + "-->" + lengths.get(i).getLength());
		}
		System.out.println(totalPairs);
		
		CanonicalTree tree = new CanonicalTree(lengths);
		tree.printLeafNodes(tree.getRoot()); //debugging
		
		OutputStream oS = new FileOutputStream("C:\\Users\\Tony\\A1Decoder2.txt");
		OutputStreamBitSink oSBS = new OutputStreamBitSink(oS);
		
		int i = 0;
		Node current = tree.getRoot();
		while (i < totalPairs) {
			while(true) {
				if (current.isLeaf()) {
					oSBS.write(current.p.getCharacter(), 8);
					i++;
					current = tree.getRoot();
					break;
				} else {
					int num = iSBS.next(1);
					if (num == 0) {
						current = current.left;
					} else {
						current = current.right;
					}
				}
			}
		}
		oSBS.padToWord();
	}
}
