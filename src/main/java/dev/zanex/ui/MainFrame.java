package dev.zanex.ui;

import dev.zanex.objects.CreditAccount;
import dev.zanex.program.Main;
import dev.zanex.types.BankAccountType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class MainFrame extends JFrame {
    private JPanel panel1;
    private JButton AccountCreation;
    private JLabel AccountingLabel;
    private JButton AccountManage;

    public MainFrame() {
        AccountingLabel.setFont(new Font("Arial", Font.BOLD, 20));

        AccountCreation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new AccountCreationFrame(MainFrame.this).setVisible(true);
            }
        });

        AccountManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new AccountManageFrame(MainFrame.this).setVisible(true);
            }
        });
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Accounting");

        frame.setContentPane(new MainFrame().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();

        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setSize(200, 300);

        // create a few accounts for testing
        Main.getAccountHandler().createAccount("John", "Doe", BankAccountType.GIRO);
        Main.getAccountHandler().createAccount("Jane", "Doe", BankAccountType.SAVINGS);
        new CreditAccount(Main.getAccountHandler().getAccountByName("John", "Doe"), 1000.00, LocalDate.now().plusDays(360));
    }
}