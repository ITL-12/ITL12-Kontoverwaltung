package dev.zanex.handler;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.CreditAccount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditAccountHandler {
    private final List<CreditAccount> creditAccounts = new ArrayList<>();

    public CreditAccountHandler() {
        new Thread(this::runCreditCheck).start();
    }

    public List<CreditAccount> getCreditAccounts() {
        return creditAccounts;
    }

    public CreditAccount getCreditByHolder(BankAccount holder) {
        for(CreditAccount creditAccount : creditAccounts) {
            if(creditAccount.getHolder().equals(holder)) {
                return creditAccount;
            }
        }

        return null;
    }

    public void createCreditAccount(CreditAccount creditAccount) {
        creditAccounts.add(creditAccount);
    }

    private void runCreditCheck() {
        creditAccounts.forEach(
            creditAccount -> {
                if (LocalDate.now().isAfter(creditAccount.getDueDate())) {
                    creditAccount.addOverdriveFee();
                }
            }
        );
    }
}
