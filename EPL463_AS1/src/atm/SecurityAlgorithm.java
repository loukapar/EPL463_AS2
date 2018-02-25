/**
 * Copyright (c) 2018 Paraskevas Louka.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author EPL463
 */
public class SecurityAlgorithm {

	protected byte[] salt;
	protected Random rnd;
	//private String pin;

	public SecurityAlgorithm() {
		salt = new byte[16];
		rnd = new Random(123);
		rnd.nextBytes(salt);

	}

	public void createPIN(String key) {
		try {
			KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 128);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = f.generateSecret(spec).getEncoded();

			//pin = new BigInteger(1, hash).toString(16);
			System.out.println("hash: " + new BigInteger(1, hash).toString(16));

		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			Logger.getLogger(SecurityAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	// this function should be implemented for authenticating the entered user
	// PIN (last 4 digits of the bank account) using the hash key that encrypts
	// the PIN. Use the above API for creating the hash based on the key and
	// comparing with the hashed PIN stored in the "accounts.txt" file.
	public boolean checkPIN(String accountNumber, String key) {
		String last4 = accountNumber.substring(accountNumber.length() - 4);
		
		//redirect console to consoleStorage in order to take above function's output
	    ByteArrayOutputStream consoleStorage = new ByteArrayOutputStream();
	    PrintStream newConsole = new PrintStream(consoleStorage);
	    PrintStream old = System.out;
	    System.setOut(newConsole);
		
		createPIN(last4);
		
		System.out.flush();
		System.setOut(old);
		
		
		String pin = (consoleStorage.toString().split(": ")[1]).replaceAll("[^A-Za-z0-9]", "");
		String CPin = Accounts.getObj().getPIN();
		//check if the pin of file is the same with hash pin
		if (pin.equals(CPin)) {
			//check if the given pin is true
			if (CPin.substring(CPin.length() - 4).equals(key)) {
				return true;
			}
		}
		return false;
	}

}
