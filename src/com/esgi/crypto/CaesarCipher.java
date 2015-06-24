package com.esgi.crypto;

import java.io.File;
import java.util.Random;

public class CaesarCipher implements ICipher{

	private FileHandler fileHandler;
	
	public char gap;
	public String key = "";
	public String array = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public CaesarCipher(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	@Override
	public void encode(File message, File key, File encoded) {
		String mess = fileHandler.readFile(message);
		String _key = fileHandler.readFile(key);
		
		String coded = swap(mess, array, _key);
		fileHandler.writeFile(encoded, coded);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String code = fileHandler.readFile(encoded);
		String _key = fileHandler.readFile(key);
		
		String coded = swap(code, _key, array);
		fileHandler.writeFile(message, coded);
	}
	
	private char GenerateRandomGap() {
		Random random = new Random();
		int max = array.length();
		int randomNumber = random.nextInt(max + 1);
		return array.charAt(randomNumber);
	}
	
	public void GenerateCode(File file) {
		int index = array.indexOf(GenerateRandomGap());
		generator(file, index);
	}
	
	public void GenerateCode(File file, char gap) {
		int index = array.indexOf(gap);
		generator(file, index);
	}

	private void generator(File file, int index) {
		String arr = "";
		
		for (int i = index+1; i < array.length(); i++) {
			arr += array.charAt(i);
		}
		for (int i = 0; i < index+1; i++) {
			arr += array.charAt(i);
		}
		fileHandler.writeFile(file, arr);
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

	@Override
	public void generateKey(File key) {
//		fileHandler.writeFile(key, RandomKeyGenerator());
	}

	private char RandomKeyGenerator() {
		Random random = new Random();
		int max = array.length();
		int randomNumber = random.nextInt(max + 1);
		return array.charAt(randomNumber);
	}
}
