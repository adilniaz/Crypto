package com.esgi.crypto;

import java.io.File;

public interface ICipher {
	void generateKey(File key);
	void encode(File message, File key, File encoded);
	void decode(File encoded, File key, File message);
}
