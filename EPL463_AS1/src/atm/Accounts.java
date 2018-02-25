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
package atm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * 
 * @author vas
 *
 */
public class Accounts {
	private String accNum, name, surname, PIN, type;
	private String balance, history;
	private static Accounts customer;
	
	//Below are the getters and setters for private fields
	public static Accounts getObj(){
		return customer;
	}

	public String getAccNum() {
		return accNum;
	}

	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public static Accounts getCustomer() {
		return customer;
	}

	public static void setCustomer(Accounts customer) {
		Accounts.customer = customer;
	}

	/**
	 * Constructor for the object.. Use of Singleton Pattern
	 * getInstance function creates a single object of type Accounts if it's not already created
	 * @param num
	 * @param AccNAME
	 * @param AccSURNAME
	 * @param AccPIN
	 * @param AccTYPE
	 * @param AccBALANCE
	 * @param AccHISTORY
	 */
	private Accounts(String num, String AccNAME, String AccSURNAME, String AccPIN, String AccTYPE, String AccBALANCE,
			String AccHISTORY) {
		accNum = num;
		name = AccNAME;
		surname = AccSURNAME;
		PIN = AccPIN;
		type = AccTYPE;
		balance = AccBALANCE;
		history = AccHISTORY;
	}

	public static Accounts getInstance(String num, String AccNAME, String AccSURNAME, String AccPIN, String AccTYPE,
			String AccBALANCE, String AccHISTORY) {
		//check if object is already created
		if (customer == null) {
			//create object
			customer = new Accounts(num, AccNAME, AccSURNAME, AccPIN, AccTYPE, AccBALANCE, AccHISTORY);
		} else {
			//update object's values
			customer.accNum = num;
			customer.name = AccNAME;
			customer.surname = AccSURNAME;
			customer.PIN = AccPIN;
			customer.type = AccTYPE;
			customer.balance = AccBALANCE;
			customer.history = AccHISTORY;
		}
		return customer;
	}

	/**
	 * Read data from file for a specific user specified by account and store
	 * in array data[]
	 * @param account
	 * @param data
	 * @throws FileNotFoundException
	 */
	public static void readData(String account, String[] data) throws FileNotFoundException {
		boolean found = false;
		File file = new File("accounts.txt");
		Scanner scanner = new Scanner(file);
		String lineFromFile;
		while (scanner.hasNextLine()) {
			lineFromFile = scanner.nextLine();
			if (lineFromFile.contains(account)) {
				found = true;
				data[0] = lineFromFile.split(":")[1];
				break;
			}
		}
		if (found) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					break;
				} else {

					String[] array = line.split(":");
					switch (array[0]) {
					case "Name":
						data[1] = array[1];
						break;
					case "Surname":
						data[2] = array[1];
						break;
					case "PIN":
						data[3] = array[1];
						break;
					case "Account Type":
						data[4] = array[1];
						break;
					case "Balance":
						data[5] = array[1];
						break;
					case "Transactions History":
						data[6] = line.split(":", 2)[1];
						break;

					}
				}
			}
		}
		scanner.close();
	}
}