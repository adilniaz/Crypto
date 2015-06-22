package com.esgi.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MonoCipher implements ICipher {
	
	@Override
	public void generateKey(File key) {
		FileWriter fw = null;
		try {
			String randomizedKey = randomizeKey(Application.KEY_BASE);
			
			fw = new FileWriter(key);
			fw.write(randomizedKey);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {}
		}
	}
	
	@Override
	public void encode(File message, File key, File encoded) {
		BufferedReader br = null;
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder keyBuilder = new StringBuilder();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(message));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				stringBuilder.append(sCurrentLine);
			}
			if (br != null)br.close();
		
			br = new BufferedReader(new FileReader(key));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				keyBuilder.append(sCurrentLine);
			}
			if (br != null)br.close();
			
			if (!encoded.exists()) {
				encoded.createNewFile();
			}
			String content = stringBuilder.toString();
			String keyString = keyBuilder.toString();
			StringBuilder result = new StringBuilder(content.length());
			
			String upperCaseAlphabet = Application.ROMAN_ALPHABET.toUpperCase();
			
			for (int i = 0 ; i < content.length() ; i++) {
				result.append(keyString.charAt(upperCaseAlphabet.indexOf(content.charAt(i))));
			}
			FileWriter fw = new FileWriter(encoded.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decode(File encoded, File key, File message) {
		
	}
	
	public String randomizeKey(String key) {
		// TODO Auto-generated method stub
		return key;
	}

}
