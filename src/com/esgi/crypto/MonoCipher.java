package com.esgi.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
		BufferedReader br = null;
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder keyBuilder = new StringBuilder();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(message));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				stringBuilder.append(sCurrentLine);
			}
			if (br != null)br.close();
		
			br = new BufferedReader(new FileReader(key));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				keyBuilder.append(sCurrentLine);
			}
			if (br != null)br.close();
			
			if (!encoded.exists()) {
				encoded.createNewFile();
			}
			String content = stringBuilder.toString();
			String keyString = keyBuilder.toString();
			StringBuilder result = new StringBuilder(content.length());
			
			for (int i = 0 ; i < content.length() ; i++) {
				result.append(keyString.charAt(Application.ROMAN_ALPHABET.toUpperCase().indexOf(content.charAt(i))));
			}
			FileWriter fw = new FileWriter(encoded.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
