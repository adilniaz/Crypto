package com.esgi.crypto.transposition;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.esgi.crypto.FileHandler;
import com.esgi.crypto.ICipher;

public class TranspositionCipher implements ICipher{
	
	FileHandler fileHandler;
	File message;
	
	public TranspositionCipher() {
		this.fileHandler = new FileHandler();
		this.message = null;
	}
	
	
	public TranspositionCipher(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
		this.message = null;
	}
	
	public TranspositionCipher(FileHandler fileHandler, File message) {
		this.fileHandler = fileHandler;
		this.message = message;
	}

	@Override
	public void generateKey(File key) {
		int keySize = randomKeySize();
		System.out.println("keySize : " + keySize + "\n");
		String _key = RandomKeyGenerator(keySize);
		System.out.println("_key : " + _key + "\n");
		fileHandler.writeFile(key, _key);
	}


	private int randomKeySize() {
		int keyMaxSize = 100;
		if (message != null) {
			keyMaxSize = fileHandler.readFile(message).length();
		}
		Random random = new Random();
		int min = 1;
		int max = keyMaxSize;
		return random.nextInt(max - min + 1) + min;
	}


	private String RandomKeyGenerator(int keySize) {
		Integer[] array = new Integer[keySize];
		for (int i = 0; i < keySize; i++) {
			array[i] = i;
		}
		RandomizeArray(array);
		
		String _key = "";
		for (int i = 0; i < array.length; i++) {
			_key += array[i];
		}
		
		return _key;
	}
	
	public static Integer[] RandomizeArray(Integer[] array){
		Random rgen = new Random();	
 
		for (int i=0; i<array.length; i++) {
			byte randomPosition = (byte) rgen.nextInt(array.length);
			Integer temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}

	@Override
	public void encode(File message, File key, File encoded) {
		String coded = fileHandler.readFile(message);
		String _key = fileHandler.readFile(key);
		
		coded = rectifyCodedMessage(coded, _key);
		

		ArrayList<String> blocList = new ArrayList<String>();
		blocList = messageToBlocks(coded, _key);
		
		System.out.println(blocList);
	}


	private String rectifyCodedMessage(String coded, String _key) {
		if (coded.length()%_key.length() != 0) {
			int spacesToAdd = _key.length() - coded.length()%_key.length();
			for (int i = 0; i < spacesToAdd; i++) {
				coded += " ";
			}
		}
		return coded;
	}


	private ArrayList<String> messageToBlocks(String coded, String _key) {
		String tmpString = "";
		ArrayList<String> blocList = new ArrayList<String>();
		for (int i = 0; i < coded.length(); i++) {
			tmpString += coded.charAt(i);
			if (i>0 && (i+1)%_key.length() == 0) {
				blocList.add(tmpString);
				tmpString = "";
			}
		}
		return blocList;
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String code = fileHandler.readFile(encoded);
		String _key = fileHandler.readFile(key);
		
	}

}
