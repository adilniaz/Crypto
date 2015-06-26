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
		byte[] _key = RandomKeyGenerator(keySize);
		fileHandler.writeByteFile(key, _key);
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
	public void encode(File message, File key, File encoded) {
		String mess = fileHandler.readFile(message);
		byte[] _key = fileHandler.readByteFile(key.getPath());
		mess = rectifyCodedMessage(mess, _key);
		
		ArrayList<String> blocList = new ArrayList<String>();
		blocList = messageToBlocks(mess, _key);

		
		String codedMessage = "";
		for (String b : blocList) {
			codedMessage += encodeShuffle(b, _key);
		}
		
//		System.out.println("/ : " + codedMessage);
		fileHandler.writeFile(encoded, codedMessage);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String coded = fileHandler.readFile(encoded);
		byte[] _key = fileHandler.readByteFile(key.getPath());

		ArrayList<String> blocList = new ArrayList<String>();
		blocList = messageToBlocks(coded, _key);
		decodeShuffle(coded, _key);
		
		String decodedMessage = "";
		for (String b : blocList) {
			decodedMessage += decodeShuffle(b, _key);
		}
		
		fileHandler.writeFile(message, decodedMessage);
		
	}


	private String decodeShuffle(String bloc, byte[] _key) {
		char[] tmp = bloc.toCharArray();
		String[] res = new String[bloc.length()];
		for (int i = 0; i < _key.length; i++) {
			res[_key[i]] = "";
			res[_key[i]] += tmp[i];
		}
		String result = "";
		for (int i = 0; i < res.length; i++) {
			result += res[i];
		}
//		System.out.println(result.substring(0, _key.length));
		return result.substring(0, _key.length);
	}


	private String encodeShuffle(String bloc, byte[] _key) {
		char[] tmp = bloc.toCharArray();
		String[] res = new String[bloc.length()];
		for (int i = 0; i < _key.length; i++) {
			res[i] = "";
			res[i] += tmp[_key[i]];
		}
		String result = "";
		for (int i = 0; i < res.length; i++) {
			result += res[i];
		}
		return result;
	}


	private String rectifyCodedMessage(String coded, byte[] _key) {
		if (coded.length()%_key.length != 0) {
			int spacesToAdd = _key.length - coded.length()%_key.length;
			for (int i = 0; i < spacesToAdd; i++) {
				coded += " ";
			}
		}
		return coded;
	}


	private ArrayList<String> messageToBlocks(String coded, byte[] _key) {
		String tmpString = "";
		ArrayList<String> blocList = new ArrayList<String>();
		for (int i = 0; i < coded.length(); i++) {
			tmpString += coded.charAt(i);
			if (i>0 && (i+1)%_key.length == 0) {
				blocList.add(tmpString);
				tmpString = "";
			}
		}
		return blocList;
	}
}
