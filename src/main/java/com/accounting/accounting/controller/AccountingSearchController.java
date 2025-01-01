package com.accounting.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.accounting.accounting.model.BankAccount;
import com.accounting.accounting.model.Transaction;
import com.accounting.accounting.service.AccountingSearchService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounting") // 定義此控制器處理與 "accounting" 相關的 REST API 請求
public class AccountingSearchController {

    @Autowired
    private AccountingSearchService accountingSearchService; // 自動注入 AccountingSearchService，用於處理搜尋邏輯

    /**
     * 根據銀行名稱及/或帳戶類型搜尋銀行帳戶。
     *
     * @param bankName    銀行名稱（可選）。
     * @param accountType 帳戶類型（可選）。
     * @return 符合條件的銀行帳戶列表。
     */
    @GetMapping("/bank-accounts")
    public List<BankAccount> searchBankAccounts(
            @RequestParam(required = false) String bankName,
            @RequestParam(required = false) String accountType) {
        return accountingSearchService.searchBankAccounts(bankName, accountType); // 調用服務層方法搜尋銀行帳戶
    }

    /**
     * 根據多種條件搜尋交易記錄。
     *
     * @param startDate     交易範圍的開始日期（可選）。
     * @param endDate       交易範圍的結束日期（可選）。
     * @param minBalance    篩選交易的最低餘額（可選）。
     * @param bankAccountId 篩選的銀行帳戶 ID（可選）。
     * @return 符合條件的交易記錄列表。
     */
    @GetMapping("/transactions")
    public List<Transaction> searchTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) BigDecimal minBalance,
            @RequestParam(required = false) Long bankAccountId) {

        BankAccount bankAccount = null; // 初始化 BankAccount 為 null
        if (bankAccountId != null) {
            // 如果提供了 bankAccountId，調用服務層方法獲取對應的 BankAccount
            bankAccount = accountingSearchService.getBankAccountById(bankAccountId);
            if (bankAccount == null) {
                // 如果未找到對應的 BankAccount，拋出 404 錯誤
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BankAccount not found for ID: " + bankAccountId);
            }
        }

        // 調用服務層方法搜尋交易記錄
        return accountingSearchService.searchTransactions(startDate, endDate, minBalance, bankAccount);
    }
}
