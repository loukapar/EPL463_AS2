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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author EPL463
 */
public class Client implements ActionListener {

	@SuppressWarnings("unused")
	private static SecurityAlgorithm sa;

	private JFrame main;
	private JPanel contentPane;
	private JTextField txtAcc;
	private JPasswordField txtPin;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnGet;
	private JRadioButton detailed;
	private JRadioButton mini;
	private JRadioButton balance;
	private StatementFactory factory;

	public Client() {

		// build a containing JFrame for display
		main = new JFrame("EPL463 - Assignment 1");
		main.setLayout(new BorderLayout());
		main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		main.setSize(450, 250);

		// build a JPanel for display in above JFrame
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		// JLabels for ATM's GUI
		JLabel lblNewLabel = new JLabel("ATM Machine");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(170, 11, 127, 14);
		contentPane.add(lblNewLabel);

		JLabel lblEnterAccountNumber = new JLabel("Enter Account Number:");
		lblEnterAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterAccountNumber.setBounds(10, 60, 173, 14);
		contentPane.add(lblEnterAccountNumber);

		JLabel lblEnterSecretPin = new JLabel("Enter Secret Pin:");
		lblEnterSecretPin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterSecretPin.setBounds(10, 99, 127, 14);
		contentPane.add(lblEnterSecretPin);

		// JTextField for user's input
		txtAcc = new JTextField();
		txtAcc.setBounds(193, 59, 231, 20);
		contentPane.add(txtAcc);
		txtAcc.setColumns(10);

		// JPasswordField for password input
		txtPin = new JPasswordField();
		txtPin.setColumns(10);
		txtPin.setBounds(193, 98, 231, 20);
		contentPane.add(txtPin);

		// JButton
		btnGet = new JButton("Get Statement");
		btnGet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGet.setBounds(10, 130, 173, 32);
		contentPane.add(btnGet);

		// Radio buttons for statement type
		// add each radio button in group so only 1 be selected each time
		balance = new JRadioButton("Balance Only");
		buttonGroup.add(balance);
		balance.setBounds(10, 175, 109, 23);
		contentPane.add(balance);

		mini = new JRadioButton("Mini Statement");
		buttonGroup.add(mini);
		mini.setBounds(125, 175, 136, 23);
		contentPane.add(mini);

		detailed = new JRadioButton("Detailed Statement");
		buttonGroup.add(detailed);
		detailed.setBounds(259, 175, 153, 23);
		contentPane.add(detailed);

		// add jpanel on jframe, set frame visible, add action listener on
		// button
		btnGet.addActionListener(this);
		main.add(contentPane);
		main.setVisible(true);
	}

	/*
	 * Main where is creates an object type Client (user interface)
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Client c = new Client();
	}

	SecurityAlgorithm SA = new SecurityAlgorithm();

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	/**
	 * Action listener function for the button of user interface
	 */
	public void actionPerformed(ActionEvent e) {
		boolean selectionOK = true;
		String STMtype = null;
		// check which radio button is selected (statement type)
		if (balance.isSelected()) {
			STMtype = balance.getText();
		} else if (detailed.isSelected()) {
			STMtype = detailed.getText();
		} else if (mini.isSelected()) {
			STMtype = mini.getText();
		} else {
			selectionOK = false; // if no one of the above is selected then
									// selection false
		}
		// read user input
		String account = txtAcc.getText();
		String accPIN = txtPin.getText();
		// check if user input is not empty and user has select a statement type
		if (!account.isEmpty() && !accPIN.isEmpty() && selectionOK) {
			try {
				String[] data = new String[7];
				// read data for user account
				Accounts.readData(account, data);
				// get current customer object
				Accounts customer = Accounts.getObj();
				// create customer object if not exist or update it fields
				customer = Accounts.getInstance(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
				// check if account number and pin are correct
				if (SA.checkPIN(account, accPIN)) {
					// create a single instance of type factory
					factory = StatementFactory.getUniqueInstance();
					// Use factory pattern, use object factory and function
					// createStatement which determines
					// with the use of STMtype (type of statement - user input)
					// what statement should be created
					StatementType STselected = factory.createStatement(STMtype);
					// create an object type iTextPDFWriter where it creates the
					// PDF file based on user input
					new iTextPDFWriter(STselected.print());
					// create a new JPanel for the PDF

					//user input about pdf type
					String[] methods = { "Window", "IcePDF" };
					String choice = (String) JOptionPane.showInputDialog(main, "Choose pdf type..", "PDF TYPE",
							JOptionPane.QUESTION_MESSAGE, null, methods, methods[0]);
					switch (choice) {
					case "Window":
						//if user choose to display statement in window pdf
						IcePDFViewer.createWindowViewer("Statement.pdf");
						break;
					case "IcePDF":
						//if user choose to display pdf in icepdf 
						IcePDFViewer.createIcePDFViewer("Statement.pdf");
						break;
					}

					txtAcc.setText("");
					txtPin.setText("");
					buttonGroup.clearSelection();

				} else {
					JOptionPane.showMessageDialog(main, "Wrong combination of Account Number and Secret PIN!");
				}

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(main, "Account Number, Secret PIN and Statement type are required fields!");
		}
	}
}
