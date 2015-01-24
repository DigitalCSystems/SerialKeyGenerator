/*
*
*KeyGenerator - Generates a serial number for using in evaluation applications
*Copyright (C) 2015, DigitalCSystems.
*
*This program is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/

package org.digitalcsystems.util;

import java.security.SecureRandom;

public class KeyGenerator {

	private char keySeperator;
	public static final byte hexChars[] = new byte[] { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	private int keySize;
	private int subKeySize;
	private byte[] regCode;
	
	public KeyGenerator() throws Exception {
		this.keySize = 25;
		this.subKeySize = 5;
		this.keySeperator = '-';
	}
	
	public KeyGenerator(int keySize) {
		this.keySize = keySize;
		this.subKeySize = 5;
		this.keySeperator = '-';
	}
	
	public KeyGenerator(int keySize, char keySeperator) {
		this.keySize = keySize;
		this.subKeySize = 5;
		this.keySeperator = keySeperator;
	}
	
	public KeyGenerator(int keySize, int subKeySize) {
		this.keySize = keySize;
		this.subKeySize = subKeySize;
		this.keySeperator = '-';
	}
	
	public KeyGenerator(int keySize, int subKeySize, char keySeperator) {
		this.keySize = keySize;
		this.subKeySize = subKeySize;
		this.keySeperator = keySeperator;
	}
	
	private byte[] getRandomCode() throws Exception {

		if (keySize < 1  | subKeySize < 1) {
			throw new Exception("Key size should be atleast 1");
		}
		
		if (keySize % subKeySize != 0 ) {
			throw new Exception("Key size infeasible");
		}
		
		SecureRandom random = new SecureRandom();
		int randVal = 0;
		byte[] regCode = new byte[keySize + (keySize/subKeySize) - 1];
		
		for (int i = 0; i < keySize + (keySize/subKeySize) - 1; i++) {
			
			if ((i+1)%(subKeySize+1) == 0) {
				regCode[i] = (byte) this.keySeperator;
			} else {
				randVal = random.nextInt(16);
				regCode[i] = hexChars[randVal];
			}
			
		}
		return regCode;

	}
	
	public byte[] getRegCode() throws Exception {
		if (regCode == null) {
			this.regCode = getRandomCode();
			return regCode;
		}
		return regCode;
	}
	
	public byte[] next() throws Exception {
		this.regCode = getRandomCode();
		return regCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (regCode == null) {
			try {
				this.regCode = getRandomCode();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < regCode.length; i++) {
			builder.append((char) regCode[i]);
		}
		return builder.toString();

	}

	public static void main(String[] args) throws Exception {
		KeyGenerator generator = new KeyGenerator(25,5);
		System.out.println(generator);
		System.out.println(new String(generator.getRegCode()));
		System.out.println(new String(generator.next()));
	}
}
