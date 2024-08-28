package com.accounting.accounting.service;

import com.accounting.accounting.model.Expenditure;
import com.accounting.accounting.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    public List<Expenditure> searchByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenditureRepository.findByDateBetween(startDate, endDate);
    }

    public List<Expenditure> searchByCompanyHeader(String companyHeader) {
        if (companyHeader == null || companyHeader.isEmpty()) {
            return List.of(); // 返回空列表
        }
        return expenditureRepository.findByCompanyHeader(companyHeader);
    }

    public List<Expenditure> searchByUnit(String unit) {
        if (unit == null || unit.isEmpty()) {
            return List.of(); // 返回空列表
        }
        return expenditureRepository.findByUnit(unit);
    }

    public List<Expenditure> searchByMultipleCriteria(LocalDate startDate, LocalDate endDate, String companyHeader, String unit) {
        // 檢查並處理空參數
        if ((companyHeader == null || companyHeader.isEmpty()) && (unit == null || unit.isEmpty())) {
            return expenditureRepository.findByDateBetween(startDate, endDate);
        } else if (companyHeader == null || companyHeader.isEmpty()) {
            return expenditureRepository.findByDateBetweenAndUnit(startDate, endDate, unit);
        } else if (unit == null || unit.isEmpty()) {
            return expenditureRepository.findByDateBetweenAndCompanyHeader(startDate, endDate, companyHeader);
        } else {
            return expenditureRepository.findByDateBetweenAndCompanyHeaderAndUnit(startDate, endDate, companyHeader, unit);
        }
    }
}
