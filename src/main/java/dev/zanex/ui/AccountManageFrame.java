package dev.zanex.ui;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.CreditAccount;
import dev.zanex.program.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AccountManageFrame extends JDialog {
    private JComboBox selectAccount;
    private JPanel contentPane;
    private JComboBox selectAction;
    private JButton submitButton;

    private List<String> accountNamesByIndex = new ArrayList<>();

    public static BankAccount account;
    public static CreditAccount creditAccount;
    
    public AccountManageFrame(JFrame parent) {
        super(parent, "Account Management", true);

        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLocationRelativeTo(parent);
        setSize(320, 150);
        setResizable(false);

        Main.getAccountHandler().getAccounts().forEach((s, account) -> {
            selectAccount.addItem(account.getAccountHolder().getFullName() + " - " + account.getAccountType().name());
            accountNamesByIndex.add(account.getAccountHolder().getFullName());
        });

        Main.getCreditAccountHandler().getCreditAccounts().forEach((creditAccount) -> {
            if(creditAccount.getHolder() != null) {
                selectAccount.addItem(creditAccount.getHolder().getAccountHolder().getFullName() + " - CREDIT");
                accountNamesByIndex.add(creditAccount.getHolder().getAccountHolder().getFullName());
            }
        });

        selectAction.addItem("Deposit");
        selectAction.addItem("Withdraw");
        selectAction.addItem("Account Statement");
        selectAction.addItem("Manage Credit");
        selectAction.addItem("Close Account");

        selectAccount.addActionListener(e -> {
            if(selectAction.getItemAt(0) != "Deposit") {
                selectAction.addItem("Deposit");
            }

            if(selectAction.getItemAt(1) != "Withdraw") {
                selectAction.addItem("Withdraw");
            }

            if(selectAccount.getSelectedItem().toString().contains("CREDIT")) {
                selectAction.removeItem("Deposit");
                selectAction.removeItem("Withdraw");

                String firstName = accountNamesByIndex.get(selectAccount.getSelectedIndex()).split(" ")[0];
                String lastName = accountNamesByIndex.get(selectAccount.getSelectedIndex()).split(" ")[1];

                account = Main.getAccountHandler().getAccountByName(firstName, lastName);
                creditAccount = Main.getCreditAccountHandler().getCreditByHolder(account);
                creditAccount.printStatement();
            } else {
                selectAction.removeItem("Manage Credit");

                String firstName = accountNamesByIndex.get(selectAccount.getSelectedIndex()).split(" ")[0];
                String lastName = accountNamesByIndex.get(selectAccount.getSelectedIndex()).split(" ")[1];

                account = Main.getAccountHandler().getAccountByName(firstName, lastName);
                account.printStatement();
            }
        });

        submitButton.addActionListener(e -> {
            String action = selectAction.getSelectedItem().toString();

            if(account != null && (selectAccount.getSelectedItem().toString().contains("GIRO") || selectAccount.getSelectedItem().toString().contains("SAVINGS"))) {
                switch(action) {
                    case "Deposit": {
                        JDialog depositDialog = new JDialog(AccountManageFrame.this, "Deposit", true);
                        depositDialog.setSize(200, 100);
                        depositDialog.setLayout(new BorderLayout());
                        depositDialog.setResizable(false);

                        JPanel depositPanel = new JPanel();
                        depositPanel.setLayout(new GridLayout(2, 2));

                        JLabel amountLabel = new JLabel("Amount:");
                        JTextField amountField = new JTextField();
                        JButton confirmDepositButton = new JButton("Deposit");

                        confirmDepositButton.addActionListener(depositEvent -> {
                            try {
                                double amount = Double.parseDouble(amountField.getText());
                                account.deposit(amount);
                                JOptionPane.showMessageDialog(depositDialog, "Deposited €" + amount + " successfully.", "Deposit", JOptionPane.INFORMATION_MESSAGE);
                                depositDialog.dispose();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(depositDialog, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        });

                        depositPanel.add(amountLabel);
                        depositPanel.add(amountField);
                        depositPanel.add(confirmDepositButton);

                        depositDialog.add(depositPanel, BorderLayout.CENTER);
                        depositDialog.setLocationRelativeTo(AccountManageFrame.this);
                        depositDialog.setVisible(true);
                        break;
                    }

                    case "Withdraw": {
                        JDialog withdrawDialog = new JDialog(AccountManageFrame.this, "Withdraw", true);
                        withdrawDialog.setSize(200, 100);
                        withdrawDialog.setLayout(new BorderLayout());
                        withdrawDialog.setResizable(false);

                        JPanel withdrawPanel = new JPanel();
                        withdrawPanel.setLayout(new GridLayout(2, 2));

                        JLabel amountLabel = new JLabel("Amount:");
                        JTextField amountField = new JTextField();
                        JButton confirmDepositButton = new JButton("Withdraw");

                        confirmDepositButton.addActionListener(depositEvent -> {
                            try {
                                double amount = Double.parseDouble(amountField.getText());
                                account.withdraw(amount);
                                JOptionPane.showMessageDialog(withdrawDialog, "Withdrew €" + amount + " successfully.", "Deposit", JOptionPane.INFORMATION_MESSAGE);
                                withdrawDialog.dispose();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(withdrawDialog, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        });

                        withdrawPanel.add(amountLabel);
                        withdrawPanel.add(amountField);
                        withdrawPanel.add(confirmDepositButton);

                        withdrawDialog.add(withdrawPanel, BorderLayout.CENTER);
                        withdrawDialog.setLocationRelativeTo(AccountManageFrame.this);
                        withdrawDialog.setVisible(true);
                        break;
                    }

                    case "Close Account": {
                        Main.getAccountHandler().closeAccount(account.getAccountNumber());
                        JOptionPane.showMessageDialog(AccountManageFrame.this, "Account closed successfully.", "Close Account", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    case "Account Statement": {
                        JOptionPane.showMessageDialog(AccountManageFrame.this, "Account Statement:\n" + account.getStatement(), "Account Statement", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }

                this.dispose();
            } else if(creditAccount != null && selectAccount.getSelectedItem().toString().contains("CREDIT")) {
                switch(action) {
                    case "Manage Credit": {
                        JDialog manageDialog = new JDialog(AccountManageFrame.this, "Manage Credit", true);
                        manageDialog.setSize(200, 100);
                        manageDialog.setLayout(new BorderLayout());
                        manageDialog.setResizable(false);

                        JPanel panel = new JPanel();
                        panel.setLayout(new GridLayout(3, 2));

                        JButton payBackButton = new JButton("Pay back credit");
                        JButton depositButton = new JButton("Deposit into credit");
                        JButton statementButton = new JButton("Credit statement");

                        payBackButton.addActionListener(event -> {
                            creditAccount.deposit(creditAccount.getAmount());
                            manageDialog.dispose();

                            JOptionPane.showMessageDialog(manageDialog, "Paid back credit successfully.", "Pay Back Credit", JOptionPane.INFORMATION_MESSAGE);
                        });

                        depositButton.addActionListener(event -> {
                            JDialog depositDialog = new JDialog(manageDialog, "Deposit into Credit", true);
                            depositDialog.setSize(200, 100);
                            depositDialog.setLayout(new BorderLayout());
                            depositDialog.setResizable(false);

                            JPanel depositPanel = new JPanel();
                            depositPanel.setLayout(new GridLayout(2, 2));

                            JLabel amountLabel = new JLabel("Amount:");
                            JTextField amountField = new JTextField();
                            JButton confirmDepositButton = new JButton("Deposit");

                            confirmDepositButton.addActionListener(depositEvent -> {
                                try {
                                    double amount = Double.parseDouble(amountField.getText());
                                    creditAccount.deposit(amount);
                                    JOptionPane.showMessageDialog(depositDialog, "Deposited €" + amount + " into credit successfully.", "Deposit", JOptionPane.INFORMATION_MESSAGE);
                                    depositDialog.dispose();
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(depositDialog, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                }
                            });

                            depositPanel.add(amountLabel);
                            depositPanel.add(amountField);
                            depositPanel.add(confirmDepositButton);

                            depositDialog.add(depositPanel, BorderLayout.CENTER);
                            depositDialog.setLocationRelativeTo(manageDialog);
                            depositDialog.setVisible(true);
                        });

                        statementButton.addActionListener(event -> {
                            JOptionPane.showMessageDialog(manageDialog, "Credit Statement:\n" + creditAccount.getStatement(), "Credit Statement", JOptionPane.INFORMATION_MESSAGE);
                        });

                        panel.add(payBackButton);
                        panel.add(depositButton);
                        panel.add(statementButton);

                        manageDialog.add(panel, BorderLayout.CENTER);
                        manageDialog.setLocationRelativeTo(AccountManageFrame.this);
                        manageDialog.setVisible(true);

                        break;
                    }

                    case "Account Statement": {
                        JOptionPane.showMessageDialog(AccountManageFrame.this, "Credit Statement:\n" + creditAccount.getStatement(), "Credit Statement", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }

                this.dispose();
            }
        });
    }
}
