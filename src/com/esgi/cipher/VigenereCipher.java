package com.esgi.cipher;

import java.io.File;
import java.util.Random;

import org.esgi.crypto.Application;
import org.esgi.crypto.ICipher;

public class VigenereCipher implements ICipher {

	@Override
	public void generateKey(File key) {
		generateRandom();
	}

	@Override
	public void encode(File message, File key, File encoded) {
		
	}

	@Override
	public void decode(File encoded, File key, File message) {
		
	}
	
	public int generateRandom() {
		Random random = new Random();
		int max = Application.ROMAN_ALPHABET.length();
		int randomNumber = random.nextInt(max + 1);
		System.out.println("randNumber = " + randomNumber);
		return randomNumber;
	}

}
