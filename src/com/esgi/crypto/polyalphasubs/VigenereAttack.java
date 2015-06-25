package com.esgi.crypto.polyalphasubs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.esgi.crypto.FileHandler;

public class VigenereAttack {
	
	private FileHandler handler;
	
	public static int [] wordsLength = {3, 4};
	
	public VigenereAttack (FileHandler handler) {
		this.handler = handler;
	}
	
	public void attack (File encoded, File decripted) {
		/* On lit le fichier dans une chaine */
		
		String text = this.handler.readFile(encoded);
		
		/* on chercher la répétition des séquence */
		Map<String, Map<String, Integer>> map;
		map = this.findRepetition(wordsLength, text);
		
		/* on détermine la (les) taille de la clé */
		
		Map<Integer, Integer> possibleKeys = this.findPossibleKeys(map);
		
		/* On applique les tailles de clés trouver pour connaitre les fréquences */
		
		int keySize = this.maxKeySize(possibleKeys);
		System.out.println("max : " + keySize);
		this.findFrequence(keySize, map, text);
		
		/* On remplace les fréquence par les mots probable */
	}
	
	private Map<String, Map<String, Integer>> findRepetition (int [] wordsLength, String text) {
		Map<String, Map<String, Integer>> map = new HashMap<>();
		for (int i = 0 ; i < text.length() ; i++) {
			for (int j = 0 ; j < wordsLength.length ; j++) {
				int length = wordsLength[j];
				if (i+length < text.length()) {
					String myString = text.substring(i, i+length);
					if (map.containsKey(myString)) {
						int repetition = map.get(myString).get("repetition");
						int espace = map.get(myString).get("espace");
						int lastOccurence = map.get(myString).get("lastOccurence");
						map.get(myString).put("repetition", repetition+1);
						int newSpace = i - lastOccurence;
						if (espace == 0 || (espace != 0 && espace > newSpace)) {
							map.get(myString).put("espace", newSpace);
						}
					} else {
						map.put(myString, new HashMap<String, Integer>());
						map.get(myString).put("repetition", 1);
						map.get(myString).put("espace", 0);
						map.get(myString).put("firstOccurence", i);
					}
					map.get(myString).put("lastOccurence", i);
				}
			}
		}
		Set<String> cles = map.keySet();
		Iterator<String> it = cles.iterator();
		List<String> removeKeys = new ArrayList<>();
		while (it.hasNext()){
		   String cle = it.next();
		   Map<String, Integer> valeur = map.get(cle);
		   if (valeur.get("repetition") == 1) {
			   removeKeys.add(cle);
		   }
		}
		for (String key : removeKeys) {
			map.remove(key);
		}
		cles = map.keySet();
		it = cles.iterator();
		while (it.hasNext()){
		   String cle = it.next();
		   Map<String, Integer> valeur = map.get(cle);
		   System.out.println(cle);
		   System.out.println("repetition : " + valeur.get("repetition"));
		   System.out.println("espace : " + valeur.get("espace"));
		   System.out.println("firstOccurence : " + valeur.get("firstOccurence"));
		   System.out.println("lastOccurence : " + valeur.get("lastOccurence"));
		}
		return map;
	}
	
	private Map<Integer, Integer> findPossibleKeys (Map<String, Map<String, Integer>> map) {
		Map<Integer, Integer> possibleKeys = new HashMap<>();
		Set<String> cles = map.keySet();
		Iterator<String> it = cles.iterator();
		while (it.hasNext()){
		   String cle = it.next();
		   Map<String, Integer> valeur = map.get(cle);
		   int espace = valeur.get("espace");
		   List<Integer> divisors = this.findCommonDivisor(espace);
		   for (Integer integer : divisors) {
			   if (possibleKeys.containsKey(integer)) {
				   int nb = possibleKeys.get(integer);
				   possibleKeys.put(integer, nb+1);
			   } else {
				   possibleKeys.put(integer, 1);
			   }
		   }
		}
		Set<Integer> keys = possibleKeys.keySet();
		Iterator<Integer> iterator = keys.iterator();
		while (iterator.hasNext()){
			Integer cle = iterator.next();
		   System.out.println(cle + " : " + possibleKeys.get(cle));
		}
		return possibleKeys;
	}
	
	private List<Integer> findCommonDivisor (int number) {
		List<Integer> divisors = new ArrayList<>();
		if (number == 2) {
			divisors.add(2);
		} else {
			for (int i = 2 ; i < (number / 2) ; i++) {
				if ((number % i) == 0) {
					divisors.add(i);
				}
			}
		}
		return divisors;
	}
	
	private int maxKeySize (Map<Integer, Integer> possibleKeys) {
		int key = 0;
		Set<Integer> keys = possibleKeys.keySet();
		Iterator<Integer> iterator = keys.iterator();
		int previous = 0;
		while (iterator.hasNext()){
			Integer cle = iterator.next();
			int current = possibleKeys.get(cle);
			if (previous < current) {
				key = cle;
				previous = current;
			}
		}
		return key;
	}
	
	private Map<Character, Double> findFrequence (int keySize, Map<String, Map<String, Integer>> map, String text) {
		Map<Character, Integer> occurences = new HashMap<>();
		Map<Character, Double> frequences = new HashMap<>();
		Set<String> cles = map.keySet();
		Iterator<String> it = cles.iterator();
		while (it.hasNext()){
		   String cle = it.next();
		   Map<String, Integer> valeur = map.get(cle);
		   int espace = valeur.get("espace");
		   if (espace % keySize == 0) {
			   for (int i = 0 ; i < cle.length() ; i++) {
				   char character = cle.charAt(i);
				   if (occurences.containsKey(character)) {
					   int nb = occurences.get(character);
					   occurences.put(character, nb+1);
				   } else {
					   occurences.put(character, 1);
				   }
			   }
		   }
		}
		Set<Character> occurence = occurences.keySet();
		Iterator<Character> iterator = occurence.iterator();
		int textLenght = text.length();
		while (iterator.hasNext()){
			Character cle = iterator.next();
			int occur = occurences.get(cle);
			double frequence = (double)occur / (double)textLenght;
			System.out.println(cle + " : " + frequence);
			System.out.println("occur : " + occur);
			frequences.put(cle, frequence);
		}
		return frequences;
	}

}
