package com.accounting.accounting.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主鍵，交易的唯一標識

    @Column(nullable = false)
    private LocalDate transactionDate; // 交易日期

    @Column(precision = 15, scale = 2)
    private BigDecimal withdrawal; // 提款金額

    @Column(precision = 15, scale = 2)
    private BigDecimal deposit; // 存款金額

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance; // 交易後的餘額

    @Column(length = 255)
    private String description; // 交易描述或備註

    @Column(precision = 10, scale = 2)
    private BigDecimal fee; // 交易手續費

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount; // 關聯的銀行帳戶

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(BigDecimal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
