package com.util;

import java.util.Random;

public class Hash {

	public String getHash() {

		int left_lim = 65;
		int right_lim = 122;
		int lenght = 16;

		Random random = new Random();
		StringBuilder buffer = new StringBuilder(lenght);

		for (int i = 0; i < lenght; i++) {
			int lim = left_lim + (int)
				(random.nextFloat() * (right_lim - left_lim + 1));
			buffer.append((char) lim);
		}
		String hash = buffer.toString();
		return hash;
	}
	
	public String getPin() {

		int left_lim = 48;
		int right_lim = 57;
		int lenght = 4;

		Random random = new Random();
		StringBuilder buffer = new StringBuilder(lenght);

		for (int i = 0; i < lenght; i++) {
			int lim = left_lim + (int)
				(random.nextFloat() * (right_lim - left_lim + 1));
			buffer.append((char) lim);
		}
		String hash = buffer.toString();
		return hash;
	}
}
