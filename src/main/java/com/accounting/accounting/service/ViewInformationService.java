package com.accounting.accounting.service;

import com.accounting.accounting.model.Expenditure;
import com.accounting.accounting.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ViewInformationService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    
    public List<Expenditure> getAllExpenditures() {
        List<Expenditure> expenditures = expenditureRepository.findAll();
        return expenditures.isEmpty() ? Collections.emptyList() : expenditures;
    }
    
}
