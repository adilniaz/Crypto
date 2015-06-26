package com.esgi.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
			
			// int nbBytes = 0;
			// while ((nbBytes = fis.read()) != 0) {
			//	byte[] b = new Byte[nbBytes];
			//}
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
	
	public ArrayList<String> readFileToList(File file) {
		BufferedReader br = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				result.add(sCurrentLine);
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
	
	public void writeByteFile(File file, byte[] byteArray) {
		BufferedOutputStream bs = null;
		try {
		    FileOutputStream fs = new FileOutputStream(file);
		    bs = new BufferedOutputStream(fs);
		    bs.write(byteArray);
		    bs.close();
		    bs = null;
		} catch (Exception e) {
			System.out.println(e);
		}
		if (bs != null) {
			try { 
				bs.close(); 
			} catch (Exception e) {
				
			}
		}
	}
	
	public byte[] readByteFile(String aInputFileName){
		File file = new File(aInputFileName);
		byte[] result = new byte[(int)file.length()];
		InputStream input = null;
		int totalBytesRead = 0;

		try {
			input = new BufferedInputStream(new FileInputStream(file));
			while(totalBytesRead < result.length){
				int bytesRemaining = result.length - totalBytesRead;
				int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
				if (bytesRead > 0) {
					totalBytesRead = totalBytesRead + bytesRead;
				}
			}
			input.close();
		} catch (IOException e) {
			System.out.print(e);
		}
		return result;
	}

}
