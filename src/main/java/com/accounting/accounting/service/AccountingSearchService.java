package com.accounting.accounting.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accounting.accounting.model.BankAccount;
import com.accounting.accounting.model.Transaction;
import com.accounting.accounting.repository.BankAccountRepository;
import com.accounting.accounting.repository.TransactionRepository;

@Service
public class AccountingSearchService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Combined search logic
    public List<BankAccount> searchBankAccounts(String bankName, String accountType) {
        if (bankName != null && accountType != null) {
            return bankAccountRepository.findByBankName(bankName)
                .stream()
                .filter(account -> account.getAccountType().equals(accountType))
                .toList();
        } else if (bankName != null) {
            return bankAccountRepository.findByBankName(bankName);
        } else if (accountType != null) {
            return bankAccountRepository.findByAccountType(accountType);
        } else {
            return bankAccountRepository.findAll();
        }
    }

    public List<Transaction> searchTransactions(LocalDate startDate, LocalDate endDate, BigDecimal minBalance, BankAccount bankAccount) {
        if (startDate != null && endDate != null) {
            return transactionRepository.findByTransactionDateBetween(startDate, endDate)
                .stream()
                .filter(transaction -> (minBalance == null || transaction.getBalance().compareTo(minBalance) > 0) &&
                        (bankAccount == null || transaction.getBankAccount().equals(bankAccount)))
                .toList();
        } else if (minBalance != null) {
            return transactionRepository.findByBalanceGreaterThan(minBalance)
                .stream()
                .filter(transaction -> (bankAccount == null || transaction.getBankAccount().equals(bankAccount)))
                .toList();
        } else if (bankAccount != null) {
            return transactionRepository.findByBankAccount(bankAccount);
        } else {
            return transactionRepository.findAll();
        }
    }

    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }
}
