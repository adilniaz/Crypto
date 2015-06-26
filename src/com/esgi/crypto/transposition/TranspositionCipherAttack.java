package com.esgi.crypto.transposition;

import java.io.File;
import java.util.ArrayList;

import com.esgi.crypto.Application;
import com.esgi.crypto.FileHandler;

public class TranspositionCipherAttack {
	FileHandler fH;
	ArrayList<String> list;
	ArrayList<Integer> factors;
	ArrayList<String> dict;
	
	public TranspositionCipherAttack(FileHandler fileHandler) {
		this.fH = fileHandler;
		this.list = new ArrayList<String>();
		this.dict = new ArrayList<String>();
	}

	public void attack() {
		String coded = fH.readFile(new File(Application.ENCODED_FILE));
		factors = findFactors(coded.length());
		factors.add(coded.length());
		
		loadDictionnary();
		String tester = "";
		for (Integer i : factors) {
			if (i < 10) {
				System.out.println(i + " - start :");
				list = new ArrayList<String>();
				System.out.println(list);
				permutation(coded.substring(0, i));
				comparison();
				System.out.println(i + " - end");
			}
		}
		
		
		dict = new ArrayList<String>();
		
//		System.out.println(list.size());
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
	}

	private void loadDictionnary() {
		File d = new File("resources/dict.txt");
		dict = fH.readFileToList(d);
	}

	private void comparison() {
		System.out.println("Comp Start : " + dict.size() + " " + list.size());
		for (String d : dict) {
			for (String l : list) {
				if (l.startsWith(d + " ")) {
					System.out.println(l);
				}
			}
		}
		System.out.println("Comp End");
	}

	private ArrayList<Integer> findFactors(int number) {
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i < number; i++) {
			if (number%i == 0/* && isPrime(i)*/){
				factors.add(i);
			}
		}
		return factors;
	}

	public void permutation(String str) {
	    permutation("", str);
	}

	private void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) {
	    	list.add(prefix);
	    	
	    } else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
}
