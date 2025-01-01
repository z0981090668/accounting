package com.accounting.accounting.repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accounting.accounting.model.BankAccount;
import com.accounting.accounting.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {    // Search transactions by date range
List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

// Search transactions by bank account
List<Transaction> findByBankAccount(BankAccount bankAccount);

// Search transactions with balance greater than a specific amount
List<Transaction> findByBalanceGreaterThan(BigDecimal balance);
}

