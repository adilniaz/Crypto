package org.esgi.crypto.caesar;

import java.io.File;
import java.util.Random;

import org.esgi.crypto.FileHandler;
import org.esgi.crypto.ICipher;

public class CaesarCipher implements ICipher{

	private FileHandler fileHandler;
	
	public String array = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public String swapper = "";
	
	public CaesarCipher(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	@Override
	public void encode(File message, File key, File encoded) {
		String mess = fileHandler.readFile(message);
		String _key = fileHandler.readFile(key);
		
		generator(_key);
		
		String coded = swap(mess, array, swapper);
		fileHandler.writeFile(encoded, coded);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		String code = fileHandler.readFile(encoded);
		String _key = fileHandler.readFile(key);
		
		generator(_key);
		
		String decoded = swap(code, swapper, array);
		fileHandler.writeFile(message, decoded);
	}

	private void generator(String _key) {
		int size = array.length();
		int keyIndex = array.indexOf(_key);
		int index = 0;
		
		int i;
		
		while (index < size) {
			i = (keyIndex+index+1)%26;
			swapper += array.charAt(i);
			index++;
		}
	}
	
	@Override
	public void generateKey(File key) {
		fileHandler.writeFile(key, RandomKeyGenerator());
	}

	private String RandomKeyGenerator() {
		Random random = new Random();
		int max = array.length()-1;
		int randomNumber = random.nextInt(max + 1);
		return array.charAt(randomNumber)+"";
	}

	private String swap(String messageText, String swapFrom, String swapTo) {
		String result = "";
		for (Character c : messageText.toCharArray()) {
			result += swapTo.charAt(swapFrom.indexOf(c));
		}
		return result;
	}
}
