package com.esgi.crypto;

import java.io.File;

public class Application {

	public static final String KEY_FILE = "resources/key.txt";
	public static final String MESSAGE_FILE = "resources/message.txt";
	public static final String ENCODED_FILE = "resources/encoded.txt";
	public static final String FOUND_KEY_FILE = "resources/foundKey.txt";
	
	public static final String ROMAN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String PONCTUATION = " _.,;:\"'";
	
	public static final String KEY_BASE = ROMAN_ALPHABET/* + PONCTUATION*/;
	
	private FileHandler fileHandler;
	
	public Application() {
		fileHandler = new FileHandler();
		MonoCipher monoCipher = new MonoCipher(fileHandler);
		
		File key = new File(KEY_FILE);
		File message = new File(MESSAGE_FILE);
		File encoded = new File(ENCODED_FILE);
		File foundKey = new File(FOUND_KEY_FILE);
		
//		monoCipher.generateKey(key);
//		monoCipher.encode2(message, key, encoded);
		
		MonoEncodedAttack m = new MonoEncodedAttack(fileHandler);
		m.findKey(encoded, foundKey);
	}
	
	public static void main(String[] args) {
		Application app = new Application();
	}

}
