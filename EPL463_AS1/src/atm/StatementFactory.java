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
public class StatementFactory {

	private static StatementFactory uniqueInstance = null;

	private StatementFactory() {

	}

	/**
	 * By using this function i implement Singleton pattern where only 1 instance
	 * of object type StatementFactory will be created
	 * @return
	 */
	public static StatementFactory getUniqueInstance() {
		if (uniqueInstance == null) {
			return new StatementFactory();
		}
		return uniqueInstance;
	}

	@SuppressWarnings("unused")
	public StatementType createStatement(String type) {
		iTextPDFWriter PDFw;
		//According to type we define the type of the statement as shown below
		//return an object according to input
		switch (type) {
		case "Detailed Statement":
			return new DetailedStatement(); 
		case "Mini Statement":
			return new MiniStatement();
		case "Balance Only":
			return new BalanceOnly();
		}
		return null;
	}

}
