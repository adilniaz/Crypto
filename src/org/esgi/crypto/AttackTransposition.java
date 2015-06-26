package org.esgi.crypto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttackTransposition implements IExecute {
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
	
	public AttackTransposition() {
		this.mostUsedFrequency = 0;
		this.fH = new FileHandler();
		this.frequencyMap = new HashMap<>();
		this.list = new ArrayList<String>();
		this.dict = new ArrayList<String>();
	}
	
	@Override
	public void execute (File encoded, File key, File decoded) {
		String coded = fH.readFile(encoded);
		
		calculateCharacterFrequency(encoded);
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
		
		List<Integer[]> possibleKeys = new ArrayList<>();
		for (Character character : leastUsedCharacters) {
			for (int i = 0; i < coded.length(); i++) {
				if (character == coded.charAt(i)) {
					List<String> founds = possibleWords(coded, coded.indexOf(character), character);
					for (Integer integer : factors) {
						List<String> blocks = this.messageToBlocks(coded, integer);
						
					}
				}
			}
		}
	}

	private List<String> possibleWords(String coded, int index, Character character) {
		ArrayList<String> wordFound;
		System.out.println(character + " : " + coded.indexOf(character));
		
		String string = coded.substring(index-10, index+11);
		wordFound = comparison(character);
		System.out.println(wordFound.size());
		
		List<String> founds = comparisonList(string, wordFound);
		System.out.println(founds);
		return founds;
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
				if (!results.contains(word))
					results.add(word);
				//System.out.println("////" + word);
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


	private ArrayList<String> messageToBlocks(String coded, int size) {
		String tmpString = "";
		ArrayList<String> blocList = new ArrayList<String>();
		for (int i = 0; i < coded.length(); i++) {
			tmpString += coded.charAt(i);
			if (i>0 && (i+1)%size == 0) {
				blocList.add(tmpString);
				tmpString = "";
			}
		}
		return blocList;
	}
	
	private int[] foundedKey (List<String> blocks, String word, Character c) {
		int[] tab = null;
		return tab;
	}
}