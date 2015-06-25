package com.esgi.crypto.polyalphasubs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.esgi.crypto.FileHandler;
import com.esgi.crypto.ICipher;

public class PolyAlphaSubstitution implements ICipher{
	private FileHandler fileHandler;
	
	public String array = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public List<String> swappers;
	
	public PolyAlphaSubstitution(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
		swappers = new ArrayList<String>();
	}
	
	@Override
	public void generateKey(File key) {
		fileHandler.writeFile(key, RandomKeyGenerator());
	}
	
	@Override
	public void encode(File message, File key, File encoded) {
		String mess = fileHandler.readFile(message);
		String _key = fileHandler.readFile(key);
		
		generator(_key);
		
		String codedMessage = swap(mess, true);
		
		fileHandler.writeFile(encoded, codedMessage);
	}
	
	@Override
	public void decode(File encoded, File key, File message) {
		String code = fileHandler.readFile(encoded);
		String _key = fileHandler.readFile(key);
		
		generator(_key);
		
		String decodedMessage = swap(code, false);
		
		fileHandler.writeFile(message, decodedMessage);
	}

	private String RandomKeyGenerator() {
		String _key = "";
		
		Random rand1 = new Random();
		int max1 = 20;
		int numberOfAlphabets = rand1.nextInt(max1 + 1);
		System.out.println(numberOfAlphabets);

		Random random = new Random();
		int max2 = array.length()-1;
		for (int i = 0; i < numberOfAlphabets; i++) {
			int characIndex = random.nextInt(max2 + 1);
			_key += array.charAt(characIndex);
		}
		
		return _key;
	}
	
	private void generator(String _key) {
		for (Character k : _key.toCharArray()) {
			int size = array.length();
			int keyIndex = array.indexOf(k);
			int index = 0;
			
			int i;
			String swapper = "";
			while (index < size) {
				i = (keyIndex+index)%26;
				swapper += array.charAt(i);
				index++;
			}
			swappers.add(swapper);
		}
	}
	
	private String swap(String string, boolean choice) {
		String result = "";
		int swapperIndex = 0;
		for (Character c : string.toCharArray()) {
			swapperIndex %= swappers.size();
			String swapper = swappers.get(swapperIndex);
			result += choice ? swapper.charAt(array.indexOf(c)) : array.charAt(swapper.indexOf(c));
			swapperIndex++;
		}
		return result;
	}
}
