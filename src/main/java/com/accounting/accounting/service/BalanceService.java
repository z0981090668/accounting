package com.accounting.accounting.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accounting.accounting.repository.TransactionRepository;
import com.accounting.accounting.model.BankAccount;
import com.accounting.accounting.model.Transaction;

@Service
public class BalanceService {

    // 使用 @Autowired 注解來自動注入 TransactionRepository
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * 獲取特定銀行帳戶的當前餘額。
     * @param bankAccount 要獲取餘額的銀行帳戶。
     * @return 最近的餘額，如果沒有交易則返回 BigDecimal.ZERO。
     */
    public BigDecimal fetchCurrentBalance(BankAccount bankAccount) {
        // 從 TransactionRepository 中查找與指定銀行帳戶相關的交易
        List<Transaction> transactions = transactionRepository.findByBankAccount(bankAccount);
        // 使用流來處理交易列表，並獲取最後一筆交易的餘額
        return transactions.stream()
                .map(Transaction::getBalance) // 將每筆交易映射到其餘額
                .reduce((first, second) -> second) // 遍歷流並保留最後一個餘額
                .orElse(BigDecimal.ZERO); // 如果沒有交易，則返回 BigDecimal.ZERO
    }
}
