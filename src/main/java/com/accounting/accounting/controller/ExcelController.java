package com.accounting.accounting.controller;

import com.accounting.accounting.service.ExpenditureService;
import com.accounting.accounting.service.TransactionUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel") // 定義與 Excel 檔案上傳相關的 API 請求
public class ExcelController {

    @Autowired
    private ExpenditureService expenditureService; // 自動注入 ExpenditureService，用於處理支出的 Excel 數據

    @Autowired
    private TransactionUploadService transactionUploadService; // 自動注入 TransactionUploadService，用於處理交易的 Excel 數據

    /**
     * 上傳並處理支出相關的 Excel 檔案。
     *
     * @param file 上傳的 Excel 檔案
     * @return 返回上傳結果的訊息
     */
    @PostMapping("/expenditures/upload")
    public ResponseEntity<String> uploadExpenditureFile(@RequestParam("file") MultipartFile file) {
        try {
            expenditureService.saveExcelData(file); // 調用 ExpenditureService 保存數據
            return ResponseEntity.ok("支出文件上傳並成功處理。"); // 返回成功訊息
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("處理支出文件時發生錯誤: " + e.getMessage()); // 返回錯誤訊息
        }
    }

    /**
     * 上傳並處理交易相關的 Excel 檔案。
     *
     * @param file 上傳的 Excel 檔案
     * @return 返回上傳結果的訊息
     */
    @PostMapping("/transactions/upload")
    public ResponseEntity<String> uploadTransactionFile(@RequestParam("file") MultipartFile file) {
        try {
            transactionUploadService.savePSTExcelData(file); // 調用 TransactionUploadService 保存數據
            return ResponseEntity.ok("交易文件上傳並成功處理。"); // 返回成功訊息
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("處理交易文件時發生錯誤: " + e.getMessage()); // 返回錯誤訊息
        }
    }
}
