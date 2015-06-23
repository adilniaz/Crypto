package com.esgi.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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

	public void encode2(File message, File key, File encoded) {
		String _message = readFile(message);
		String _key = readFile(key);
		
		String codedMessage = swap(_message, Application.ROMAN_ALPHABET, _key);
		
		writeFile(encoded, codedMessage);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String encodedMessage = readFile(encoded);
		String _key = readFile(key);
		
		String realMessage = swap(encodedMessage, _key, Application.ROMAN_ALPHABET);
		
		writeFile(message, realMessage);
	}
	
	private String swap(String string, String swapFrom, String swapTo) {
		String result = "";
		for(int i = 0; i < string.length();i++) {
			int index = swapFrom.indexOf(string.charAt(i), 0);
			if(index != -1)
				result += swapTo.charAt(index);
			else
				result += string.charAt(i);
		}
		
		return result;
	}
	

	private void writeFile(File file, String string) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private String readFile(File file) {
		BufferedReader br = null;
		String string = "";
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				string += sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return string;
	}

	public String randomizeKey(String key) {
		Random random = new Random();
		int min = 0;
		int max = key.length();
		String result = "";
		StringBuilder sb = new StringBuilder(key);
		for (int i = 0; i < 26; i++) {
			max = max-1;
			int randomNumber = random.nextInt(max - min + 1) + min;
			result += sb.charAt(randomNumber);
			sb.deleteCharAt(randomNumber);	
		}
		result += Application.PONCTUATION;
		return result;
	}

}
