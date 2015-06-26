package org.esgi.crypto.monocipher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.esgi.crypto.Application;
import org.esgi.crypto.FileHandler;
import org.esgi.crypto.Symbol;

public class MonoAttack {
	
	private FileHandler fileHandler;
	
	private List<Symbol> frequenceRef;
	private List<Symbol> frequenceMessage;
	
	private Comparator<Symbol> comparatorFrequency = new Comparator<Symbol>() {

		@Override
		public int compare(Symbol symbol1, Symbol symbol2) {
			return symbol1.getFrequence() == symbol2.getFrequence() ? 
					0 : symbol1.getFrequence() > symbol2.getFrequence() ? -1 : 1;
		}
	};
	
	public MonoAttack(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
		
		frequenceRef = new ArrayList<Symbol>();
		frequenceMessage = new ArrayList<Symbol>();
		
		initFrequencyRef();
	}
	
	private void initFrequencyRef() {
		frequenceRef.add(new Symbol('A', 7.636f));
    	frequenceRef.add(new Symbol('B', 0.901f));
    	frequenceRef.add(new Symbol('C', 3.260f));
    	frequenceRef.add(new Symbol('D', 3.669f));
        frequenceRef.add(new Symbol('E', 14.715f));
        frequenceRef.add(new Symbol('F', 1.066f));
        frequenceRef.add(new Symbol('G', 0.866f));
        frequenceRef.add(new Symbol('H', 0.737f));
        frequenceRef.add(new Symbol('I', 7.529f));
        frequenceRef.add(new Symbol('J', 0.545f));
        frequenceRef.add(new Symbol('K', 0.049f));
        frequenceRef.add(new Symbol('L', 5.456f));
        frequenceRef.add(new Symbol('M', 2.968f));
        frequenceRef.add(new Symbol('N', 7.095f));
        frequenceRef.add(new Symbol('O', 5.378f));
        frequenceRef.add(new Symbol('P', 3.021f));
        frequenceRef.add(new Symbol('Q', 1.362f));
        frequenceRef.add(new Symbol('R', 6.553f));
        frequenceRef.add(new Symbol('S', 7.948f));
        frequenceRef.add(new Symbol('T', 7.244f));
        frequenceRef.add(new Symbol('U', 6.311f));
        frequenceRef.add(new Symbol('V', 1.628f));
        frequenceRef.add(new Symbol('W', 0.114f));
        frequenceRef.add(new Symbol('X', 0.387f));
        frequenceRef.add(new Symbol('Y', 0.308f));
        frequenceRef.add(new Symbol('Z', 0.136f));
	}
	
	public void findKey(File encoded, File foundKey) {		
		Collections.sort(frequenceRef, comparatorFrequency);
		
		//HashMap<Character, Double> frequence = calculateCharacterFrequency(encoded);
		//frequencyComparison(frequence, frequenceRef);
	}

	public HashMap<Character, Float> calculateCharacterFrequency(File encoded) {
		HashMap<Character,Float> frequenceMap = new HashMap<Character,Float>();
		String encodedMessage = fileHandler.readFile(encoded);
		encodedMessage = deleteCharacters(encodedMessage, Application.PONCTUATION);
		
		for (int i = 0; i < encodedMessage.length(); i++) {
			char c = encodedMessage.charAt(i);
			Float val = frequenceMap.get(c);
			if (val != null) {
				frequenceMap.put(c, val + 1);
			} else {
				frequenceMap.put(c, 1f);
			}
		}
		
		for (char c : frequenceMap.keySet()) {
			frequenceMap.put(c, (frequenceMap.get(c) / encodedMessage.length()) * 100);
		}
		
		String str = "";
		for (int i = 0; i < Application.ROMAN_ALPHABET.length(); i++) {
			if (!frequenceMap.containsKey(Application.ROMAN_ALPHABET.charAt(i))) {
				str += Application.ROMAN_ALPHABET.charAt(i);
			}
		}
		
		for (int i = 0; i < str.length(); i++) {
			frequenceMap.put(str.charAt(i), 0f);
		}
		return frequenceMap;
	}
	
	public void frequencyComparison(HashMap<Character, Double> frequence,
			HashMap<Character, Double> frequenceToCompare) {
		
		ArrayList<Double> list = new ArrayList<Double>();
		for (Character c : frequence.keySet()) {
			list.add(frequence.get(c));
			
		}
		Collections.sort(list);
		Collections.reverse(list);
		
		String key = "";
		
		for (int i = 0; i < list.size(); i++) {
			for (Character c : frequence.keySet()) {
				if (frequence.get(c) == list.get(i)) {
					frequence.put(c, (double) -1);
					key += c;
				}
			}
		}
		System.out.println("key : " + key);
	}
	
	public String deleteCharacters(String text, String charaters) {
		for (int i = 0; i < charaters.length(); i++) {
			text = text.replace(charaters.charAt(i), ' ');
		}
		text = text.replaceAll(" ", "");
		
		return text;
	}
}
