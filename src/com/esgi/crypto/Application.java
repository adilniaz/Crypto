package com.esgi.crypto;

import java.io.File;

public class Application {

	public static final String KEY_FILE = "resources/key.txt";
	public static final String MESSAGE_FILE = "resources/message.txt";
	public static final String ENCODED_FILE = "resources/encoded.txt";
	
	public static final String ROMAN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String PONCTUATION = " _.,;:\"'";
	
	public static final String KEY_BASE = ROMAN_ALPHABET/* + PONCTUATION*/;
	
	public static void main(String[] args) {
		System.out.println(PONCTUATION);
		MonoCipher monoCipher = new MonoCipher();
		monoCipher.generateKey(new File(KEY_FILE));
//		monoCipher.encode(new File(MESSAGE_FILE), new File(KEY_FILE), new File(ENCODED_FILE));
	}

}
