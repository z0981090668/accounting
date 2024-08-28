package com.accounting.accounting.controller;

import com.accounting.accounting.model.Expenditure;
import com.accounting.accounting.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
public class Searchcontroller {

    @Autowired
    private SearchService viewInformationService;

    @GetMapping("/searchByDateRange")
    public List<Expenditure> searchByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return viewInformationService.searchByDateRange(startDate, endDate);
    }

    @GetMapping("/searchByCompanyHeader")
    public List<Expenditure> searchByCompanyHeader(@RequestParam("companyHeader") String companyHeader) {
        return viewInformationService.searchByCompanyHeader(companyHeader);
    }

    @GetMapping("/searchByUnit")
    public List<Expenditure> searchByUnit(@RequestParam("unit") String unit) {
        return viewInformationService.searchByUnit(unit);
    }

    @GetMapping("/searchByMultipleCriteria")
    public List<Expenditure> searchByMultipleCriteria(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("companyHeader") String companyHeader,
            @RequestParam("unit") String unit) {
        return viewInformationService.searchByMultipleCriteria(startDate, endDate, companyHeader, unit);
    }
}
