package com.accounting.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accounting.accounting.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {

}