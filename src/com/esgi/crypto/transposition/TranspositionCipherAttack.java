package com.esgi.crypto.transposition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.esgi.crypto.FileHandler;

public class TranspositionCipherAttack {
	String[] commonWords = {"le", "de", "un", "�tre", "et", "�", "il", "avoir", "ne", "je", "son", "que", "se", "qui", "ce", "dans", "en", "du",
			"elle", "au", "de", "ce", "le", "pour", "pas", "que", "vous", "par", "sur", "faire", "plus", "dire", "me", "on", "mon", "lui", "nous",
			"comme", "mais", "pouvoir", "avec", "tout", "y", "aller", "voir", "en", "bien", "o�", "sans", "tu", "ou", "leur", "homme", "si", "deux",
			"mari", "moi", "vouloir", "te", "femme", "venir", "quand", "grand", "celui", "si", "notre", "devoir", "l�", "jour", "prendre", "m�me", "votre",
			"tout", "rien", "petit", "encore", "aussi", "quelque", "dont", "tout", "mer", "trouver", "donner", "temps", "�a", "peu", "m�me", "falloir",
			"sous", "parler", "alors", "main", "chose", "ton", "mettre", "vie", "savoir", "yeux", "passer", "autre", "apr�s", "regarder", "toujours",
			"puis", "jamais", "cela", "aimer", "non", "heure", "croire", "cent"};
	
	String[] commonKWZWords = {"wifi", "de"};
	
	FileHandler handler;
	
	public TranspositionCipherAttack(FileHandler fileHandler) {
		this.handler = fileHandler;
	}
	
	public void attack (File encodedFile, File key, File decodedFile) {
		
		String encodedText = this.handler.readFile(encodedFile);
		
		/* On compte le nombre de charact�re de la chaine. La cl� est forcement un diviseur de ce nombre */
		List<Integer> divisor = this.findDivisor(encodedText);
		
		/* On cherche les lettres � basse fr�quence (Z, K, w) */
		char [] charToFind = {'z', 'k', 'w'};
		Map<Character, List<Integer>> map = this.getCharactersPosition(charToFind, encodedText);
		
		/* Si le texte en contient on cherche les n lettres autous que l'on compare avec un dictionnaire */
		
		/* Si on trouve une correspondance, on match le n avec les longueurs de cl� trouver */
		
		/* On retrouve le switch */
	}
	
	public List<Integer> findDivisor (String texte) {
		List<Integer> list = new ArrayList<>();
		int textLength = texte.length();
		if (textLength == 2) {
			list.add(2);
		} else {
			for (int i = 2 ; i < (textLength / 2) ; i++) {
				if ((textLength % i) == 0) {
					list.add(i);
				}
			}
		}
		for (Integer integer : list) {
			System.out.println(integer);
		}
		return list;
	}
	
	public Map<Character, List<Integer>> getCharactersPosition (char[] charToFind, String texte) {
		Map<Character, List<Integer>> map = new HashMap<>();
		for (int i = 0 ; i < charToFind.length ; i++) {
			map.put(charToFind[i], new ArrayList<Integer>());
		}
		for (int i = 0 ; i < texte.length() ; i++) {
			for (int j = 0 ; j < charToFind.length ; j++) {
				if (texte.charAt(i) == charToFind[j]) {
					map.get(charToFind[j]).add(i);
				}
			}
		}
		Set<Character> keys = map.keySet();
		Iterator<Character> iterator = keys.iterator();
		while (iterator.hasNext()){
			Character cle = iterator.next();
			System.out.println(cle);
			List<Integer> values = map.get(cle);
			for (Integer integer : values) {
				System.out.println(integer);
			}
		}
		return map;
	}
}
