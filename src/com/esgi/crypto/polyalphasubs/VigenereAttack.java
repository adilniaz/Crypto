package com.esgi.crypto.polyalphasubs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esgi.crypto.FileHandler;

public class VigenereAttack {
	
	private FileHandler handler;
	
	public VigenereAttack (FileHandler handler) {
		this.handler = handler;
	}
	
	public void attack (File encoded, File decripted) {
		/* On lit le fichier dans une chaine */
		
		String text = this.handler.readFile(encoded);
		
		/* on chercher la r�p�tition des s�quence */
		
		/* on d�termine la (les) taille de la cl� */
		
		/* On applique les tailles de cl�s trouver pour connaitre les fr�quences */
		
		/* On remplace les fr�quence par les mots probable */
	}
	
	private Map<String, Map<String, Integer>> findRepetition (int [] wordsLength, String text) {
		Map<String, Map<String, Integer>> map = new HashMap<>();
		return map;
	}
	
	private List<Integer> findPossibleKeys (Map<String, Map<String, Integer>> map) {
		List<Integer> list = new ArrayList<Integer>();
		return list;
	}

}
