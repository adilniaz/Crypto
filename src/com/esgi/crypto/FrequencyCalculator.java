package com.esgi.crypto;

import java.io.File;
import java.util.HashMap;

public class FrequencyCalculator {
	File file;
	FileHandler fileHandler;
	
	public FrequencyCalculator() {
		fileHandler = new FileHandler();
		file = new File("resources/freqCalcFile.txt");
	}
			
}
