package org.esgi.crypto.cesar;

import java.io.File;

import org.esgi.crypto.Application;
import org.esgi.crypto.FileHandler;
import org.esgi.crypto.ICipher;

public class CesarCipher implements ICipher {

	private FileHandler fileHandler;
	
	private Character gap = 'Z';
	
	public CesarCipher(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	@Override
	public void generateKey(File key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void encode(File message, File key, File encoded) {
		String messageText = fileHandler.readFile(message);
		String alphabetToEncode = getCesarAlphabet();
		
		String codedMessage = swap(messageText, Application.ROMAN_ALPHABET, alphabetToEncode);
		
		fileHandler.writeFile(encoded, codedMessage);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String encodedMessage = fileHandler.readFile(encoded);
		String alphabetToEncode = getCesarAlphabet();
		
		String realMessage = swap(encodedMessage, alphabetToEncode, Application.ROMAN_ALPHABET);
		
		fileHandler.writeFile(message, realMessage);
	}
	
	public String getCesarAlphabet() {
		String alphabetToEncode = "";
		String tmp = "";
		int start = 0;
		
		for (int i = 0; i < Application.ROMAN_ALPHABET.length(); i++) {
			Character c = Application.ROMAN_ALPHABET.charAt(i);
			if (c == gap) {
				start = i + 1;
				tmp += gap;
				break;
			}
			tmp += c;
		}
		
		for (int i = start; i < Application.ROMAN_ALPHABET.length(); i++) {
			Character c = Application.ROMAN_ALPHABET.charAt(i);
			alphabetToEncode += c;
		}
		alphabetToEncode += tmp;
		
		return alphabetToEncode;
	}

	public String swap(String messageText, String swapFrom, String swapTo) {
		/*
		messageText = messageText.toLowerCase();
		swapFrom = swapFrom.toLowerCase();
		swapTo = swapTo.toLowerCase();
		*/
		String result = "";
		for (int i = 0; i < messageText.length(); i++) {
			int index = swapFrom.indexOf(messageText.charAt(i));
			if (index != -1) {
				result += swapTo.charAt(index);
			}
		}
		return result;
	}
}
