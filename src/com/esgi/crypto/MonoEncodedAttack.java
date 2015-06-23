package com.esgi.crypto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
		
		String key = "";
		fileHandler.writeFile(foundKey, key);
		
		for (char c : map.keySet()) {
			System.out.println(c + " : " + map.get(c));
		}
	}


	private void frequencyComparison(HashMap<Character, Double> map,
			Map<String, Double> frequences2) {
		// TODO Auto-generated method stub
		
	}


	private HashMap<Character, Double> calculateCharacterFrequency(File file) {
		HashMap<Character,Double> map = new HashMap<Character,Double>();
		String s = fileHandler.readFile(file);
		for (int i = 0; i < Application.PONCTUATION.length(); i++) {
			s = s.replace(Application.PONCTUATION.charAt(i), ' ');
		}
		s = s.replaceAll(" ", "");
		for(int i = 0; i < s.length(); i++){
		   char c = s.charAt(i);
		   Double val = map.get(new Character(c));
		   if(val != null){
		     map.put(c, new Double(val + 1));
		   }else{
		     map.put(c,(double) 1);
		   }
		}
		for (char c : map.keySet()) {
		     map.put(c,(map.get(c)/s.length())*100);
		}
		return map;
	}
}
