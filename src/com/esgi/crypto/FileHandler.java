package com.esgi.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	
	public void writeFile(File file, String string) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void writeBytes(File file, byte b, boolean append) {
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(file, append);
			fos.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String readBytes(File file) {
		FileInputStream fis = null;
		String text = "";
		
		try {
			fis = new FileInputStream(file);
			int c = 0;
			
			while ((c = fis.read()) != -1) {
				int lastChar = 0;
				if (c == ';' && lastChar != ';') {
					text += ";\n";
				} else if (c == ',' && lastChar != ',') {
					text += ',';
				} else if (c == '=') {
					text += '=';
				} else {
					text += c;
				}
				lastChar = c;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return text;
	}

	public String readFile(File file) {
		BufferedReader br = null;
		String result = "";
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				result += sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
