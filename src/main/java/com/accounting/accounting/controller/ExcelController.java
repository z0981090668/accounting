package com.accounting.accounting.controller;

import com.accounting.accounting.service.ProcurementService;
import com.accounting.accounting.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExpenditureService expenditureService; // 自動注入 ExpenditureService

    @Autowired
    private ProcurementService procurementService; // 自動注入 ProcurementService

    /**
     * 上傳和處理 Expenditure 的 Excel 文件
     * @param file 上傳的 Excel 文件
     * @return 返回操作結果的 ResponseEntity
     */
    @PostMapping("/expenditures/upload")
    public ResponseEntity<String> uploadExpenditureFile(@RequestParam("file") MultipartFile file) {
        try {
            expenditureService.saveExcelData(file);
            return ResponseEntity.ok("File uploaded and processed successfully for expenditures.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing expenditure file: " + e.getMessage());
        }
    }

    /**
     * 上傳和處理 Procurement 的 Excel 文件
     * @param file 上傳的 Excel 文件
     * @return 返回操作結果的 ResponseEntity
     */
    @PostMapping("/procurement/upload")
    public ResponseEntity<String> uploadProcurementFile(@RequestParam("file") MultipartFile file) {
        try {
            procurementService.saveExcelData(file);
            return ResponseEntity.ok("File uploaded and processed successfully for procurement.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing procurement file: " + e.getMessage());
        }
    }
}
