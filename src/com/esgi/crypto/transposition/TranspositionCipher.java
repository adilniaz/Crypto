package com.esgi.crypto.transposition;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.esgi.crypto.FileHandler;
import com.esgi.crypto.ICipher;

public class TranspositionCipher implements ICipher{
	
	FileHandler fileHandler;
	File messageFile;
	
	public TranspositionCipher() {
		this.fileHandler = new FileHandler();
		this.messageFile = null;
	}
	
	
	public TranspositionCipher(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
		this.messageFile = null;
	}
	
	public TranspositionCipher(FileHandler fileHandler, File messageFile) {
		this.fileHandler = fileHandler;
		this.messageFile = messageFile;
	}

	@Override
	public void generateKey(File keyFile) {
		int keySize = randomKeySize();
		System.out.println("keySize : " + keySize + "\n");
		byte[] key = randomKeyGenerator(keySize);
		fileHandler.writeByteFile(keyFile, key);
	}


	private int randomKeySize() {
		int keyMaxSize = 100;
		if (messageFile != null) {
			String message = fileHandler.readFile(messageFile);
			keyMaxSize = message.length();
		}
		Random random = new Random();
		int min = 1;
		int max = keyMaxSize;
		return random.nextInt(max - min + 1) + min;
	}


	private byte[] randomKeyGenerator(int keySize) {
		byte[] array = new byte[keySize];
		
		for (int i = 0; i < array.length; i++) {
			array[i] = new Byte((byte) i);
		}
		
		randomizeArray(array);
		return array;
	}
	
	private byte[] randomizeArray(byte[] array){
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
		
        ArrayList<String> blockList = messageToBlocks(message, key);
		
		System.out.println("KEY");
		for (int i = 0; i < key.length; i++) {
			System.out.print(key[i] + " ");
        }
		blockList = messageToBlocks(message, key);

		String codedMessage = "";
		for (String blockText : blockList) {
			codedMessage += encodeShuffle(blockText, key);
		}
		
//		System.out.println("/ : " + codedMessage);
		fileHandler.writeFile(encodedFile, codedMessage);
	}

	@Override
	public void decode(File encodedFile, File keyFile, File messageFile) {
		String coded = fileHandler.readFile(encodedFile);
		byte[] key = fileHandler.readByteFile(keyFile.getPath());

		ArrayList<String> blockList = messageToBlocks(coded, key);
		decodeShuffle(coded, key);
		
		String decodedMessage = "";
		for (String blockText : blockList) {
			decodedMessage += decodeShuffle(blockText, key);
		}
		
		fileHandler.writeFile(messageFile, decodedMessage);
	}

	private String decodeShuffle(String blockText, byte[] key) {
		char[] tmp = blockText.toCharArray();
		String[] res = new String[blockText.length()];
		for (int i = 0; i < key.length; i++) {
			res[key[i]] = "";
			res[key[i]] += tmp[i];
		}
		String result = "";
		for (int i = 0; i < res.length; i++) {
			result += res[i];
		}
//		System.out.println(result.substring(0, _key.length));
		return result.substring(0, key.length);
	}


	private String encodeShuffle(String blockText, byte[] key) {
		char[] tmp = blockText.toCharArray();
		String[] res = new String[blockText.length()];
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
}
