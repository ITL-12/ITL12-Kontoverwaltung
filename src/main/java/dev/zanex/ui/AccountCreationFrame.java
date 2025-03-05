package dev.zanex.ui;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.CreditAccount;
import dev.zanex.program.Main;
import dev.zanex.types.BankAccountType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AccountCreationFrame extends JDialog {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JFormattedTextField firstNameInput;
    private JFormattedTextField lastNameInput;
    private JRadioButton giro;
    private JRadioButton credit;
    private JRadioButton savings;
    private JFormattedTextField formattedTextField1;
    private JPanel creditAmount;
    private JButton createAccountButton;
    private ButtonGroup accountTypeGroup;

    public AccountCreationFrame(JFrame parent) {
        super(parent, "Account Creation", true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLocationRelativeTo(parent);
        setSize(250, 400);
        setResizable(false);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        creditAmount.setVisible(false);
        createAccountButton.setEnabled(false);

        accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(giro);
        accountTypeGroup.add(credit);
        accountTypeGroup.add(savings);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }
        };

        firstNameInput.getDocument().addDocumentListener(documentListener);
        lastNameInput.getDocument().addDocumentListener(documentListener);
        formattedTextField1.getDocument().addDocumentListener(documentListener);

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (credit.isSelected()) {
                    creditAmount.setVisible(true);
                } else {
                    creditAmount.setVisible(false);
                }
                checkFields();
                pack();
            }
        };

        giro.addActionListener(radioButtonListener);
        credit.addActionListener(radioButtonListener);
        savings.addActionListener(radioButtonListener);

        pack();

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameInput.getText();
                String lastName = lastNameInput.getText();
                BankAccountType accountType;

                if (giro.isSelected()) {
                    accountType = BankAccountType.GIRO;
                } else if (credit.isSelected()) {
                    accountType = BankAccountType.CREDIT;
                } else if (savings.isSelected()) {
                    accountType = BankAccountType.SAVINGS;
                } else {
                    accountType = null;
                }

                if (accountType.equals(BankAccountType.CREDIT)) {
                    BankAccount account = Main.getAccountHandler().getAccountByName(firstName, lastName);

                    if(account == null) {
                        JOptionPane.showMessageDialog(contentPane, "Account not found.", "Account Creation", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String creditLimitText = formattedTextField1.getText();
                    double creditLimit = 0.0;

                    try {
                        creditLimit = Double.parseDouble(creditLimitText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(contentPane, "Please enter a valid numeric credit limit.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    assert account != null;
                    new CreditAccount(account, creditLimit, LocalDate.now().plusDays(360));
                    JOptionPane.showMessageDialog(contentPane, "A credit of € " + creditLimit + " has been granted to: \n - " + account.getAccountNumber() + " \n - " + account.getAccountHolder().getFullName(), "Account Creation", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Main.getAccountHandler().createAccount(firstName, lastName, accountType);

                    BankAccount account = Main.getAccountHandler().getAccountByName(firstName, lastName);
                    JOptionPane.showMessageDialog(contentPane, "Account created successfully: \n - Number: " + account.getAccountNumber() + " \n - Bank Code: " + account.getBankCode() + " \n - Name: " + account.getAccountHolder().getFullName() + " \n - Type: " + account.getAccountType(), "Account Creation", JOptionPane.INFORMATION_MESSAGE);
                }

                dispose();
            }
        });
    }

    private void checkFields() {
        boolean allFieldsFilled = !firstNameInput.getText().trim().isEmpty() &&
                !lastNameInput.getText().trim().isEmpty() &&
                (giro.isSelected() || savings.isSelected() ||
                        (credit.isSelected() && !formattedTextField1.getText().trim().isEmpty()));

        createAccountButton.setEnabled(allFieldsFilled);
    }
}