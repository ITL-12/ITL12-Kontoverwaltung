package dev.zanex.handler;

import dev.zanex.objects.BankAccount;
import dev.zanex.program.Main;
import dev.zanex.types.BankAccountType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BankAccountHandler {
    private final Map<String, BankAccount> accounts = new HashMap<>();

    public void createAccount(String firstName, String lastName, BankAccountType accountType) {
        String accountNumber = generateAccountNumber(firstName, lastName);
        String bankCode = generateBankCode();

        double overdraftLimit;
        double accountFees = switch (accountType) {
            case GIRO -> {
                overdraftLimit = -1000.00;
                yield -15.00;
            }
            case SAVINGS -> {
                overdraftLimit = 0.00;
                yield -5.00;
            }
            default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
        };

        BankAccount account = new BankAccount(accountNumber, firstName, lastName, accountType, bankCode, overdraftLimit, accountFees);
        accounts.put(accountNumber, account);

        Main.getOutputHandler().println("&7[&bACCOUNT&7] &aAccount created successfully.");
        Main.getOutputHandler().println("&f- &9Number: &f" + accountNumber);
        Main.getOutputHandler().println("&f- &9Bank Code: &f" + bankCode);
        Main.getOutputHandler().println("&f- &9Name: &f" + account.getAccountHolder().getFullName());
        Main.getOutputHandler().println("&f- &9Type: &f" + accountType);
    }

    public void closeAccount(String accountNumber) {
        accounts.remove(accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else if(account.getAccountType().equals(BankAccountType.CREDIT)) {
            System.out.println("Credit accounts cannot be deposited into.");
        }else {
            System.out.println("Account not found.");
        }
    }

    public Map<String, BankAccount> getAccounts() {
        return this.accounts;
    }

    public void withdraw(String accountNumber, double amount) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void printStatement(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.printStatement();
        } else {
            System.out.println("Account not found.");
        }
    }

    private String generateBankCode() {
        Random random = new Random();
        int bankCode = random.nextInt(10000);
        return String.format("%04d", bankCode);
    }

    private String generateAccountNumber(String firstName, String lastName) {
        try {
            /* -- Get an instance of the MD5 message digest -- */
            MessageDigest md = MessageDigest.getInstance("MD5");
            /* -- Update the digest with the bytes of the concatenated first and last name -- */
            md.update((firstName + lastName).getBytes());

            /* -- Perform the hash computation -- */
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            /* -- Convert the hash bytes to a hexadecimal string -- */
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            String hash = sb.toString();
            StringBuilder formatted = new StringBuilder();
            int count = 0;
            /* -- Extract digits from the hash and format them into groups of 4 -- */
            for (char c : hash.toCharArray()) {
                if (Character.isDigit(c)) {
                    formatted.append(c);
                    count++;
                    if (count % 4 == 0 && count < 16) {
                        formatted.append(" ");
                    }
                }
            }
            /* -- Pad with zeros if there are not enough digits -- */
            while (count < 16) {
                formatted.append("0");
                count++;
                if (count % 4 == 0 && count < 16) {
                    formatted.append(" ");
                }
            }

            if(formatted.length() > 19) {
                formatted.delete(19, formatted.length());
            }

            /* -- Return the formatted account number -- */
            return formatted.toString();
        } catch (NoSuchAlgorithmException e) {
            /* -- Handle the exception if MD5 algorithm is not available -- */
            throw new RuntimeException(e);
        }
    }

    public BankAccount getAccountByName(String firstName, String lastName) {
        return accounts.values().stream().filter(account ->
            account.getAccountHolder().getFullName().equals(firstName + " " + lastName)
        ).findFirst().orElse(null);
    }
}