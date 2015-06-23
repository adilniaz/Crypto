package com.esgi.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MonoCipher implements ICipher {
	
	private FileHandler fileHandler;
	
	public MonoCipher(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
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

	public void encode(File message, File key, File encoded) {
		String messageText = fileHandler.readFile(message);
		String keyText = fileHandler.readFile(key);
		
		String codedMessage = swap(messageText, Application.ROMAN_ALPHABET, keyText);
		
		fileHandler.writeFile(encoded, codedMessage);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String encodedMessage = fileHandler.readFile(encoded);
		String keyText = fileHandler.readFile(key);
		
		String realMessage = swap(encodedMessage, keyText, Application.ROMAN_ALPHABET);
		
		fileHandler.writeFile(message, realMessage);
	}
	
	public String swap(String messageText, String swapFrom, String swapTo) {
		/*
		messageText = messageText.toLowerCase();
		swapFrom = swapFrom.toLowerCase();
		swapTo = swapTo.toLowerCase();
		*/
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

	public String randomizeKey(String key) {
		Random random = new Random();
		int max = key.length();
		String result = "";
		StringBuilder sb = new StringBuilder(key);
		for (int i = 0; i < key.length(); i++) {
			max = max - 1;
			int randomNumber = random.nextInt(max + 1);
			result += sb.charAt(randomNumber);
			sb.deleteCharAt(randomNumber);
		}
		//result += Application.PONCTUATION;
		return result;
	}

}
