package com.accounting.accounting.repository;

import com.accounting.accounting.model.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {

    // 搜尋依照日期範圍
    List<Expenditure> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // 搜尋依照公司抬頭
    List<Expenditure> findByCompanyHeader(String companyHeader);

    // 搜尋依照單位
    List<Expenditure> findByUnit(String unit);

    // 綜合條件搜尋
    List<Expenditure> findByDateBetweenAndCompanyHeaderAndUnit(LocalDate startDate, LocalDate endDate, String companyHeader, String unit);

    // 其他組合條件
    List<Expenditure> findByDateBetweenAndCompanyHeader(LocalDate startDate, LocalDate endDate, String companyHeader);

    List<Expenditure> findByDateBetweenAndUnit(LocalDate startDate, LocalDate endDate, String unit);

    List<Expenditure> findByCompanyHeaderAndUnit(String companyHeader, String unit);
}
