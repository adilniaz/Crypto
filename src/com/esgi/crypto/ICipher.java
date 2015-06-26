package com.esgi.crypto;

import java.io.File;

public interface ICipher {
	void generateKey(File keyFile);
	void encode(File messageFile, File keyFile, File encodedFile);
	void decode(File encodedFile, File keyFile, File messageFile);
}
