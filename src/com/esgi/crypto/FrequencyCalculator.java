package com.esgi.crypto;

import java.io.File;
import java.util.HashMap;

public class FrequencyCalculator {
File file;
	
	HashMap<Character, Double> frequencyMap;
	
	FileHandler fileHandler;
	
	public FrequencyCalculator(File file) {
		this.fileHandler = new FileHandler();
		this.frequencyMap = new HashMap<Character, Double>();
		file = new File("resources/freqCalcFile.txt");
	}

	public HashMap<Character, Double> calculateCharacterFrequency() {
		String encodedMessage = fileHandler.readFile(file);
		
		for (int i = 0; i < encodedMessage.length(); i++) {
			char c = encodedMessage.charAt(i);
			Double val = frequencyMap.get(new Character(c));
			if (val != null) {
				frequencyMap.put(c, new Double(val + 1));
			} else {
				frequencyMap.put(c, (double) 1);
			}
		}
		
		for (char c : frequencyMap.keySet()) {
			frequencyMap.put(c, (frequencyMap.get(c) / encodedMessage.length()) * 100);
		}
		String str = "";
		for (int i = 0; i < Application.ALPHABET.length(); i++) {
			if (!frequencyMap.containsKey(Application.ALPHABET.charAt(i))) {
				str += Application.ALPHABET.charAt(i);
			}
		}
		for (int i = 0; i < str.length(); i++) {
			frequencyMap.put(str.charAt(i), (double) 0);
		}
		

		System.out.println("private static final Map<String, Double> frequencies = new HashMap<>();\nstatic {\n");
		for (Character c : frequencyMap.keySet()) {
			System.out.print("frequencies.put('"+c+ "', "+frequencyMap.get(c) +");\n");
		}
		System.out.println("}\n");
		return frequencyMap;
	}
			
}
