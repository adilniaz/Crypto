package com.esgi.crypto;

import java.io.File;

public class Application {

	public static final String KEY_FILE = "resources/key.txt";
	public static final String MESSAGE_FILE = "resources/message.txt";
	public static final String ENCODED_FILE = "resources/encoded.txt";
	
	public static final String ROMAN_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	public static final String PONCTUATION = "_.,;:\"'";
	
	public static void main(String[] args) {
		MonoCipher monoCipher = new MonoCipher();
		monoCipher.generateKey(new File(KEY_FILE));
	}

}
