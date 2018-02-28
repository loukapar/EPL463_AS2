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

/**
 * 
 * @author vas
 *
 */
public class DetailedStatement extends StatementType {

	@Override
	/**
	 * Determines the informations that will be printed in statement for detailed statements
	 */
	public String print() {
		return "Account Number: " + Accounts.getObj().getAccNum() + "\nName: " + Accounts.getObj().getName()
				+ "\nSurname: " + Accounts.getObj().getSurname() + "\nBalance: " + Accounts.getObj().getBalance()
				+ "\nAccountType: " + Accounts.getObj().getType() + "\nTransactions History: "
				+ Accounts.getObj().getHistory();
	}

}
