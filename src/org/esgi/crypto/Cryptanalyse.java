package org.esgi.crypto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esgi.crypto.monocipher.MonoCipher;

public class Cryptanalyse {
	
	public void clairChoisi () {
		File keyFile = new File(Application.KEY_FILE);
		File messageFile = new File(Application.MESSAGE_FILE);
		File encodedFile = new File(Application.ENCODED_FILE);
		FileHandler handler = new FileHandler();
		MonoCipher monoCipher = new MonoCipher(handler);
		monoCipher.generateKey(keyFile);
		monoCipher.encode2(messageFile, keyFile, encodedFile);
		String message = handler.readFile(messageFile);
		String encodedMessage = handler.readFile(encodedFile);
		String key = handler.readFile(keyFile);
		int nbFindLetters = 0;
		int indice = 0;
		Map<Character, Character> matches = new HashMap<>();
		while (nbFindLetters < 26 && indice < message.length()) {
			char character = message.charAt(indice);
			if (!matches.containsKey(character)) {
				matches.put(character, encodedMessage.charAt(indice));
				nbFindLetters++;
			}
			indice++;
		}
		String alphabet = Application.ROMAN_ALPHABET;
		StringBuilder builder = new StringBuilder();
		for (int i = 0 ; i < alphabet.length() ; i++) {
			char character = alphabet.charAt(i);
			if (matches.containsKey(character)) {
				builder.append(matches.get(character));
			} else {
				builder.append("?");
			}
		}
		System.out.println("finded key : " + builder.toString());
		System.out.println("real key : " + key);
	}
	
	public void bruteForceCaesar (File encodedFile, File messageFile) {
		FileHandler handler = new FileHandler();
		String encodedMessage = handler.readFile(encodedFile);
		StringBuilder decodedMessage;
		String alphabet = Application.ROMAN_ALPHABET;
		int nbCharacter = alphabet.length();
		List<Integer> processIndices = new ArrayList<>();
		for (int i = 0 ; i < nbCharacter ; i++) {
			int decalage = i+1;
			decodedMessage = new StringBuilder(encodedMessage);
			for (int j = 0 ; j < nbCharacter ; j++) {
				char character = alphabet.charAt(j);
				char remplacement = alphabet.charAt((nbCharacter-decalage+j)%nbCharacter);
				for (int k = 0 ; k < encodedMessage.length() ; k++) {
					if (!processIndices.contains(k)) {
						if (decodedMessage.charAt(k) == character) {
							decodedMessage.setCharAt(k, remplacement);
							processIndices.add(k);
						}
					}
				}
			}
			System.out.println("key : " + alphabet.charAt(i));
			System.out.println(decodedMessage);
			while (!processIndices.isEmpty()) {
				processIndices.remove(0);
			}
		}
	}
	
	public void bruteForceCaesar2 (File encoded, File message) {
		FileHandler handler = new FileHandler();
		String encodedMessage = handler.readFile(encoded);
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int nbCharacter = alphabet.length();
		List<Integer> processIndices = new ArrayList<>();
		for (int i = 0 ; i < 26; i++) {
			generator(alphabet, i);

			String coded = swap(encodedMessage, generator(alphabet, i), alphabet);
			if (!wordChecker(coded)) {
				System.out.println(alphabet.charAt(i) + " : " + coded);
			}
		}
	}
	

	private String generator(String array, int index) {
		String arr = "";
		
		for (int i = index+1; i < array.length(); i++) {
			arr += array.charAt(i);
		}
		for (int i = 0; i < index+1; i++) {
			arr += array.charAt(i);
		}
		return arr;
	}

	private String swap(String messageText, String swapFrom, String swapTo) {
		String result = "";
		for(int i = 0; i < messageText.length(); i++) {
			int index = swapFrom.indexOf(messageText.charAt(i), 0);
			if(index != -1)
				result += swapTo.charAt(index);
			else
				result += messageText.charAt(i);
		}
		return result;
	}
	
	public boolean wordChecker(String string){
		String vowels = "[AEIOU]";
		String consonants = "[BCDFGHJKLMNPQRSTVWXYZ]";
	    String pattern= "^(.*)"+consonants+"{3}(.*)";
        if(string.matches(pattern)){
            return true;
        }
        return false;   
	}

}
