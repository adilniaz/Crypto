package org.esgi.crypto.homophonique;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.esgi.crypto.FileHandler;
import org.esgi.crypto.ICipher;

public class CypherHomophonique implements ICipher {

	private FileHandler fileHandler;
	
	public CypherHomophonique(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	private static final Map<String, Float> frequences = new HashMap<>();
    static {
        frequences.put("E", 14.715f);
        frequences.put("S", 7.948f);
        frequences.put("A", 7.636f);
        frequences.put("I", 7.529f); 
        frequences.put("T", 7.244f);
        frequences.put("N", 7.095f);
        frequences.put("R", 6.553f);
        frequences.put("U", 6.311f);
        frequences.put("L", 5.456f);
        frequences.put("O", 5.378f);
        frequences.put("D", 3.669f);
        frequences.put("C", 3.260f);
        frequences.put("P", 3.021f);
        frequences.put("M", 2.968f);
        frequences.put("V", 1.628f);
        frequences.put("Q", 1.362f);
        frequences.put("F", 1.066f);
        frequences.put("B", 0.901f);
        frequences.put("G", 0.866f);
        frequences.put("H", 0.737f);
        frequences.put("J", 0.545f);
        frequences.put("X", 0.387f);
        frequences.put("Y", 0.308f);
        frequences.put("Z", 0.136f);
        frequences.put("W", 0.114f);
        frequences.put("K", 0.049f);
    }
	
	@Override
	public void generateKey(File keyFile) {
		Random random = new Random();
		List<Integer> valueUsed = new ArrayList<>();
		
		HashMap<String, Byte[]> values = new HashMap<>();
		
		for (String character : frequences.keySet()) {
			int valFrequence = (int) Math.floor(frequences.get(character));
			valFrequence = valFrequence == 0 ? 1 : valFrequence;
			//System.out.println("char " + character + ": val frequence = " + valFrequence);
			Byte[] byteArray = new Byte[valFrequence];
			
			int count = 0;
			
			while (count < byteArray.length) {
				int value = random.nextInt(255 + 1);
				
				if (valueUsed.size() == 255) {
					//System.out.println("255 values registered !");
					break;
				}
				
				if (valueUsed.contains(value)) continue;
				
				byteArray[count] = new Byte((byte)value);
				//System.out.println("count = " + count + ": val = " + value);
				
				valueUsed.add(value);
				count++;
			}
			values.put(character, byteArray);
		}
		
		for (String keyToDisplay : values.keySet()) {			
			System.out.println("key: " + keyToDisplay + " = " + Arrays.toString(values.get(keyToDisplay)));
			Byte[] byteArray = values.get(keyToDisplay);
			for (int i = 0; i < byteArray.length; i++) {
				
				if (i == 0) {
					fileHandler.writeBytes(keyFile, new Byte((byte) (keyToDisplay.toCharArray()[0])), true);
					fileHandler.writeBytes(keyFile, new Byte((byte) ','), true);
				}
				fileHandler.writeBytes(keyFile, byteArray[i], true);
				
				if (i < byteArray.length - 1) {
					fileHandler.writeBytes(keyFile, new Byte((byte) ','), true);
				}
			}
			fileHandler.writeBytes(keyFile, new Byte((byte) ';'), true);
		}
		fileHandler.writeBytes(keyFile, new Byte((byte) '\0'), true);
	}

	@Override
	public void encode(File messageFile, File keyFile, File encodedFile) {
		HashMap<Integer, String> asciiToString = new HashMap<>();
		
		String message = fileHandler.readFile(messageFile);
		String text = fileHandler.readBytes(keyFile);
		//System.out.println(text);
		String[] lines = text.split(";");
		for (String line : lines) {
			String[] values = line.split(",");
			for (int i = 0; i < values.length; i++) {
				if (i == 0) {
					int ascii = Integer.parseInt(values[i].trim());
					System.out.println(ascii);
				}
			}
		}
		//String coded = "";
		//fileHandler.writeFile(encodedFile, coded);
	}

	@Override
	public void decode(File encoded, File key, File message) {
		// TODO Auto-generated method stub
		
	}
	
	
}
