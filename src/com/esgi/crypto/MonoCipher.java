package com.esgi.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MonoCipher implements ICipher {
	
	@Override
	public void generateKey(File key) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(key);
			fw.write(Application.ROMAN_ALPHABET + Application.PONCTUATION);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {}
		}
	}
	
	@Override
	public void encode(File message, File key, File encoded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decode(File encoded, File key, File message) {

		BufferedReader brCode = null, brKey = null;
		String encodedMessage = "";
		String _key = "";
		 
		try {
			String sCurrentLine;
			brCode = new BufferedReader(new FileReader("resources\\encoded.txt"));
			brKey = new BufferedReader(new FileReader("resources\\key.txt"));

			while ((sCurrentLine = brCode.readLine()) != null) {
				encodedMessage += sCurrentLine;
			}
			while ((sCurrentLine = brKey.readLine()) != null) {
				_key += sCurrentLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (brCode != null) brCode.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(encodedMessage);
		System.out.println(_key);
	}



}
