package org.esgi.crypto.monocipher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.esgi.crypto.Application;
import org.esgi.crypto.FileHandler;

public class MonoEncodedAttack {
	
	private FileHandler fileHandler;
	
	public MonoEncodedAttack(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	private static final Map<String, Double> frequences = new HashMap<>();
    static {
        frequences.put("E", 14.715);
        frequences.put("S", 7.948);
        frequences.put("A", 7.636);
        frequences.put("I", 7.529); 
        frequences.put("T", 7.244);
        frequences.put("N", 7.095);
        frequences.put("R", 6.553);
        frequences.put("U", 6.311);
        frequences.put("L", 5.456);
        frequences.put("O", 5.378);
        frequences.put("D", 3.669);
        frequences.put("C", 3.260);
        frequences.put("P", 3.021);
        frequences.put("M", 2.968);
        frequences.put("V", 1.628);
        frequences.put("Q", 1.362);
        frequences.put("F", 1.066);
        frequences.put("B", 0.901);
        frequences.put("G", 0.866);
        frequences.put("H", 0.737);
        frequences.put("J", 0.545);
        frequences.put("X", 0.387);
        frequences.put("Y", 0.308);
        frequences.put("Z", 0.136);
        frequences.put("W", 0.114);
        frequences.put("K", 0.049);
    }
	
	public void findKey(File encoded, File foundKey) {
		HashMap<Character, Double> map = calculateCharacterFrequency(encoded);
		frequencyComparison(map, frequences);
		String key = frequencyComparison(map, frequences);

		fileHandler.writeFile(foundKey, key);
		
		MonoCipher m = new MonoCipher(fileHandler);
		m.decode(encoded, foundKey, new File("resources/test.txt"));
	}


	public String frequencyComparison(HashMap<Character, Double> frequenceMap,
			Map<String, Double> frequenceToCompare) {
		
		ArrayList<Double> list = new ArrayList<Double>();
		for (Character c : frequenceMap.keySet()) {
			list.add(frequenceMap.get(c));
		}
		Collections.sort(list);
		Collections.reverse(list);
		String key = "";
		String res = "";
		
		for (int i = 0; i < list.size(); i++) {
			for (Character c : frequenceMap.keySet()) {
				res += c;
				if (frequenceMap.get(c) == list.get(i)) {
					frequenceMap.put(c, (double) -1);
					key += c;
				}
			}
		}
		
		return key;
	}


	public HashMap<Character, Double> calculateCharacterFrequency(File encoded) {
		HashMap<Character,Double> frequenceMap = new HashMap<Character,Double>();
		String encodedMessage = fileHandler.readFile(encoded);
		encodedMessage = deleteCharacters(encodedMessage, Application.PONCTUATION);
		
		for (int i = 0; i < encodedMessage.length(); i++) {
			char c = encodedMessage.charAt(i);
			Double val = frequenceMap.get(new Character(c));
			if (val != null) {
				frequenceMap.put(c, new Double(val + 1));
			} else {
				frequenceMap.put(c, (double) 1);
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
			frequenceMap.put(str.charAt(i), (double) 0);
		}
		
		return frequenceMap;
	}
	
	public String deleteCharacters(String text, String charaters) {
		for (int i = 0; i < charaters.length(); i++) {
			text = text.replace(charaters.charAt(i), ' ');
		}
		text = text.replaceAll(" ", "");
		
		return text;
	}
}
