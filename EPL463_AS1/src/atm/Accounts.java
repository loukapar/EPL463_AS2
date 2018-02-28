/**Copyright (c) 2018 Paraskevas Louka

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.**/
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