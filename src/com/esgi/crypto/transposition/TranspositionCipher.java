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
	public void generateKey(File keyFile) {
		int keySize = randomKeySize();
		System.out.println("keySize : " + keySize + "\n");
		byte[] key = RandomKeyGenerator(keySize);
		fileHandler.writeByteFile(keyFile, key);
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


	private byte[] RandomKeyGenerator(int keySize) {
		byte[] array = new byte[keySize];
		
		for (int i = 0; i < array.length; i++) {
			array[i] = new Byte((byte) i);
		}
		
		RandomizeArray(array);
		return array;
	}
	
	public byte[] RandomizeArray(byte[] array){
		Random rgen = new Random();	
 
		for (int i=0; i<array.length; i++) {
			byte randomPosition = (byte) rgen.nextInt(array.length);
			byte temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}

	@Override
	public void encode(File messageFile, File keyFile, File encodedFile) {
		String message = fileHandler.readFile(messageFile);
		byte[] key = fileHandler.readByteFile(keyFile.getPath());
		
		message = addFillingSpace(message, key);
		
		ArrayList<String> blocList = new ArrayList<String>();
		blocList = messageToBlocks(message, key);
		
		System.out.println("KEY");
		for (int i = 0; i < key.length; i++) {
			System.out.print(key[i] + " ");
		}
		System.out.println("\nKEY");
		
		
		System.out.println(blocList);

		String codedMessage = "";
		for (String b : blocList) {
			codedMessage += shuffleByKey(b, key);
		}
		
		fileHandler.writeFile(encodedFile, codedMessage);
	}


	private String shuffleByKey(String bloc, byte[] key) {
		char[] tmp = bloc.toCharArray();
		String[] res = new String[bloc.length()];
		for (int i = 0; i < key.length; i++) {
			res[i] = "";
			res[i] += tmp[key[i]];
		}
		String result = "";
		for (int i = 0; i < res.length; i++) {
			result += res[i];
		}
		return result;
	}


	private String addFillingSpace(String coded, byte[] key) {
		if (coded.length() % key.length != 0) {
			int spacesToAdd = key.length - coded.length() % key.length;
			for (int i = 0; i < spacesToAdd; i++) {
				coded += " ";
			}
		}
		return coded;
	}


	private ArrayList<String> messageToBlocks(String message, byte[] key) {
		String tmpString = "";
		ArrayList<String> blockList = new ArrayList<String>();
		for (int i = 0; i < message.length(); i++) {
			tmpString += message.charAt(i);
			if (i > 0 && (i + 1) % key.length == 0) {
				blockList.add(tmpString);
				tmpString = "";
			}
		}
		return blockList;
	}

	@Override
	public void decode(File encodedFile, File keyFile, File messageFile) {
		String code = fileHandler.readFile(encodedFile);
		String key = fileHandler.readFile(keyFile);
		
	}

}
