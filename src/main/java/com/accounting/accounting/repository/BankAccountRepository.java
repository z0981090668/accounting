package com.accounting.accounting.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accounting.accounting.model.BankAccount;
import java.util.List;


@Repository
    public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
         List<BankAccount>findByBankName(String bankName );
     List<BankAccount> findByAccountType(String accountType);
    }
    

