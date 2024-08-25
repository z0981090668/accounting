package com.accounting.accounting.controller;

import com.accounting.accounting.model.Expenditure;
import com.accounting.accounting.service.ViewInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewDataController {

    @Autowired
    private ViewInformationService viewInformationService; // 使用 ViewInformationService

    @GetMapping("/viewinfo")
    public String viewinfo(Model model) {
        List<Expenditure> expenditures = viewInformationService.getAllExpenditures(); // 调用 ViewInformationService
        model.addAttribute("expenditures", expenditures);
        return "upload"; // 這裡對應 Thymeleaf 的模板名稱
    }
}
