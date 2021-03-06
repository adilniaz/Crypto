package org.esgi.crypto.transposition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.crypto.Application;
import org.esgi.crypto.FileHandler;

public class AttackTransposition {
	FileHandler fH;
	ArrayList<String> list;
	ArrayList<Integer> factors;
	ArrayList<String> dict;
	HashMap<Character, Integer> frequencyMap;
	int mostUsedFrequency;
	
	String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public AttackTransposition(FileHandler fileHandler) {
		this.mostUsedFrequency = 0;
		this.fH = fileHandler;
		this.frequencyMap = new HashMap<>();
		this.list = new ArrayList<String>();
		this.dict = new ArrayList<String>();
	}
	
	public void execute (File encoded, File key, File decoded) {
		
	}

	public void attack() {
		String coded = fH.readFile(new File(Application.ENCODED_FILE));
		
		calculateCharacterFrequency(new File(Application.ENCODED_FILE));
		ArrayList<Character> leastUsedCharacters = new ArrayList<Character>();
		
		int min = Integer.MAX_VALUE;
		
		for (Character c : frequencyMap.keySet()) {
			int value = frequencyMap.get(c);
			if (value < min) {
				min = value;
				while(!leastUsedCharacters.isEmpty()) {
					leastUsedCharacters.remove(0);
				}
				if (alphabets.contains(c+"")) {
					leastUsedCharacters.add(c);
				}
			} else if (value == min) {
				if (alphabets.contains(c+"")) {
					leastUsedCharacters.add(c);
				}
			}
		}
		System.out.println(leastUsedCharacters);
		
		factors = findFactors(coded.length());
		factors.add(coded.length());
		
		
		
		
		System.out.println(factors + " ");
		loadDictionnary();
		
		for (Character character : leastUsedCharacters) {
			for (int i = 0; i < coded.length(); i++) {
				if (character == coded.charAt(i)) {
					possibleWords(coded, coded.indexOf(character), character);
				}
			}
		}
		
		
	}

	private void possibleWords(String coded, int index, Character character) {
		ArrayList<String> wordFound;
		System.out.println(character + " : " + coded.indexOf(character));
		
		String string = coded.substring(index-5, index+6);
		wordFound = comparison(character);
		System.out.println(wordFound.size());
		
		List<String> founds = comparisonList(string, wordFound);
		System.out.println(founds);
	}

	private void loadDictionnary() {
		File d = new File("resources/dict.txt");
		dict = fH.readFileToList(d);
	}

	private ArrayList<String> comparison(Character character) {
		ArrayList<String> wordFound = new ArrayList<String>();
		for (String word : dict) {
			if (word.contains(character+"")) {
				wordFound.add(word);
			}
		}
		return wordFound;
	}

	private List<String> comparisonList(String string, ArrayList<String> wF) {
		/*String result = "";
		System.out.println("////" + string);
		for (Character c : string.toCharArray()) {
				System.out.println("////" + c);
			for (String word : wF) {
				if (word.contains(c+"")) {
					result += c;
					System.out.println(result);
				}
			}
		}
		return result;*/
		
		List<String> results = new ArrayList<>();
		for (String word : wF) {
			int nbFound = 0;
			Map<Character, Integer> indexes = new HashMap<>();
			for (Character c : string.toCharArray()) {
				int index = 0;
				if (indexes.containsKey(c)) {
					index = indexes.get(c);
				}
				int foundIndex = word.indexOf(c, index);
				if (foundIndex != -1) {
					nbFound++;
					index = foundIndex+1;
					indexes.put(c, index);
					
				}
			}
			if (nbFound == word.length()) {
				results.add(word);
				System.out.println("////" + word);
			}
		}
		return results;
	}

	private ArrayList<Integer> findFactors(int number) {
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i < number; i++) {
			if (number%i == 0/* && isPrime(i)*/){
				factors.add(i);
			}
		}
		return factors;
	}

	public void permutation(String str) {
	    permutation("", str);
	}

	private void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) {
	    	list.add(prefix);
	    	
	    } else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
	
	public HashMap<Character, Integer> calculateCharacterFrequency(File file) {
		String encodedMessage = fH.readFile(file);
		
		for (int i = 0; i < encodedMessage.length(); i++) {
			char c = encodedMessage.charAt(i);
			Integer val = frequencyMap.get(new Character(c));
			if (val != null) {
				frequencyMap.put(c, val + 1);
			} else {
				frequencyMap.put(c, 1);
			}
		}
		return frequencyMap;
	}
}
