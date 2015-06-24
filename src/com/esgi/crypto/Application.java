package com.esgi.crypto;

import java.io.File;
import java.util.HashMap;

import com.esgi.crypto.homophonic.HomophonicCipher;

public class Application {

	public static final String KEY_FILE = "resources/key.txt";
	public static final String MESSAGE_FILE = "resources/message.txt";
	public static final String ENCODED_FILE = "resources/encoded.txt";
	public static final String FOUND_KEY_FILE = "resources/foundKey.txt";
	
	public static final String ROMAN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String PONCTUATION = " _.,;:\"'";
	
	public static final String HOMONOPHONIC = " !\"'(),-./0123456789:;<=>?@ÀABÂCDEFGÇHÈIÉÊJKËLMNÎOÏPQRŒSTÔUVWXYÙZÛ_`";
	
	public static final String KEY_BASE = ROMAN_ALPHABET/* + PONCTUATION*/;
	
	private FileHandler fileHandler;
	
	public static final HashMap<Character, Double> frequencies = new HashMap<>();
	static {
		frequencies.put(' ', 16.8375296162997);
		frequencies.put('!', 0.1472756971297603);
		frequencies.put('"', 0.004562075466645774);
		frequencies.put('\'', 1.0504674639720009);
		frequencies.put('(', 4.958777681136711E-4);
		frequencies.put(')', 7.934044289818737E-4);
		frequencies.put(',', 2.1259271674569304);
		frequencies.put('-', 0.36744542617223025);
		frequencies.put('.', 0.9798544697926139);
		frequencies.put('/', 6.942288753591395E-4);
		frequencies.put('0', 6.942288753591395E-4);
		frequencies.put('1', 0.0020826866260774185);
		frequencies.put('2', 3.9670221449093686E-4);
		frequencies.put('3', 9.917555362273422E-5);
		frequencies.put('4', 4.958777681136711E-4);
		frequencies.put('5', 3.9670221449093686E-4);
		frequencies.put('6', 5.950533217364053E-4);
		frequencies.put('7', 1.9835110724546843E-4);
		frequencies.put('8', 9.917555362273422E-4);
		frequencies.put('9', 9.917555362273422E-4);
		frequencies.put(':', 0.05881110329828138);
		frequencies.put(';', 0.0904481049039336);
		frequencies.put('<', 0.0011901066434728106);
		frequencies.put('=', 4.958777681136711E-4);
		frequencies.put('>', 0.0011901066434728106);
		frequencies.put('?', 0.07279485635908692);
		frequencies.put('@', 9.917555362273422E-5);
		frequencies.put('À', 0.35435425309402935);
		frequencies.put('A', 6.770318343609574);
		frequencies.put('B', 0.8106609753122295);
		frequencies.put('Â', 0.07924126734456463);
		frequencies.put('C', 2.2826245421808506);
		frequencies.put('D', 2.938174951627124);
		frequencies.put('E', 11.621986426833733);
		frequencies.put('F', 0.8576701877294054);
		frequencies.put('G', 0.8300993838222853);
		frequencies.put('Ç', 0.08211735839962393);
		frequencies.put('H', 0.8387276569874633);
		frequencies.put('È', 0.2739228791059919);
		frequencies.put('I', 5.843026917237009);
		frequencies.put('É', 1.3580108557560997);
		frequencies.put('Ê', 0.18674756747160853);
		frequencies.put('J', 0.29921264527978914);
		frequencies.put('K', 0.005454655449250382);
		frequencies.put('Ë', 5.950533217364053E-4);
		frequencies.put('L', 4.798311635375127);
		frequencies.put('M', 2.0722731929470313);
		frequencies.put('N', 5.5019621883284255);
		frequencies.put('Î', 0.03282710824912503);
		frequencies.put('O', 3.813498387901376);
		frequencies.put('Ï', 0.0029752666086820265);
		frequencies.put('P', 1.8550787305132432);
		frequencies.put('Q', 0.7946937111789691);
		frequencies.put('R', 5.172005121425589);
		frequencies.put('Œ', 1.9835110724546843E-4);
		frequencies.put('S', 6.113577827519827);
		frequencies.put('T', 5.77271144971849);
		frequencies.put('Ô', 0.0342155659998433);
		frequencies.put('U', 4.896297082354388);
		frequencies.put('V', 1.2871003349158445);
		frequencies.put('W', 8.925799826046078E-4);
		frequencies.put('X', 0.3192461071115814);
		frequencies.put('Y', 0.1797061031643944);
		frequencies.put('Ù', 0.035604023750561584);
		frequencies.put('Z', 0.09629946256767492);
		frequencies.put('Û', 0.04026527477083009);
		frequencies.put('_', 1.9835110724546843E-4);
		frequencies.put('`', 9.917555362273422E-5);
	}

	
	public Application() {
		fileHandler = new FileHandler();
//		MonoCipher monoCipher = new MonoCipher(fileHandler);
//		
		File key = new File(KEY_FILE);
		File message = new File(MESSAGE_FILE);
		File encoded = new File(ENCODED_FILE);
		
//		FrequencyCalculator fC = new FrequencyCalculator();
//		fC.calculateCharacterFrequency();
		
		
//		monoCipher.generateKey(key);
//		monoCipher.encode(message, key, encoded);
//		
//		MonoEncodedAttack m = new MonoEncodedAttack(fileHandler);
//		m.findKey(encoded, foundKey);
		
//		CesarCipher cesarCipher = new CesarCipher(fileHandler);
//		cesarCipher.encode(message, key, encoded);
//		cesarCipher.decode(encoded, key, message);
		
		HomophonicCipher hC = new HomophonicCipher(fileHandler);
//		hC.encode(message, key, encoded);
		hC.decode(encoded, key, message);
	}
	
	public static void main(String[] args) {
		Application app = new Application();
	}

}
