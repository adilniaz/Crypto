package com.esgi.crypto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MonoEncodedAttack {
	
	private static final Map<String, Double> frequences = new HashMap<>();
    static {
        frequences.put("e", 14.715);
        frequences.put("s", 7.948);
        frequences.put("a", 7.636);
        frequences.put("i", 7.529);
        frequences.put("t", 7.244);
        frequences.put("n", 7.095);
        frequences.put("r", 6.553);
        frequences.put("u", 6.311);
        frequences.put("l", 5.456);
        frequences.put("o", 5.378);
        frequences.put("d", 3.669);
        frequences.put("c", 3.260);
        frequences.put("p", 3.021);
        frequences.put("m", 2.968);
        frequences.put("v", 1.628);
        frequences.put("q", 1.362);
        frequences.put("f", 1.066);
        frequences.put("b", 0.901);
        frequences.put("g", 0.866);
        frequences.put("h", 0.737);
        frequences.put("j", 0.545);
        frequences.put("x", 0.387);
        frequences.put("y", 0.308);
        frequences.put("z", 0.136);
        frequences.put("w", 0.114);
        frequences.put("k", 0.049);
    }
	
	private void findKey(File encoded, File foundKey) {
		// TODO Auto-generated method stub
		
	}
}
