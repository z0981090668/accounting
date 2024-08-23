package com.accounting.accounting.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accounting.accounting.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    boolean existsByOrderNumberAndDate(String orderNumber, LocalDate date);
}
