package com.esgi.crypto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MonoCipher implements ICipher {
	
	@Override
	public void generateKey(File key) {
		FileWriter fw = null;
		try {
			String randomizedKey = randomizeKey(Application.KEY_BASE);
			
			fw = new FileWriter(key);
			fw.write(randomizedKey);
			
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
		// TODO Auto-generated method stub
	}
	
	public String randomizeKey(String key) {
		// TODO Auto-generated method stub
		return key;
	}

}
