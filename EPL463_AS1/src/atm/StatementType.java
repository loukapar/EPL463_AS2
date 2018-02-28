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
public abstract class StatementType {
	/**
	 * This function determines and returns the data that will be printed in the
	 * pdf file This abstract class is extended from classes: BalanceOnly where it
	 * prints only balance MiniStatement where it prints some of the account's
	 * data DetailedStatement where it prints all account's data
	 * 
	 * @return
	 */
	public String print() {
		return null;
	}
}
