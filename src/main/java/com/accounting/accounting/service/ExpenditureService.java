package com.accounting.accounting.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accounting.accounting.model.Expenditure;
import com.accounting.accounting.repository.ExpenditureRepository;

@Service
public class ExpenditureService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    private static final Logger logger = Logger.getLogger(ExpenditureService.class.getName());

    // 保存Excel檔案中的數據到資料庫
    public void saveExcelData(MultipartFile file) throws IOException {
        // 使用 try-with-resources 自動關閉工作簿資源
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0); // 獲取第一個工作表

            // 遍歷 Excel 中的每一行，跳過表頭行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                // 檢查 row 是否為空
                if (row == null || row.getCell(0) == null) {
                    logger.warning("Row " + rowIndex + " is empty or null, skipping.");
                    continue;
                }

                try {
                    // 創建 Expenditure 對象並設置數據
                    Expenditure expenditure = createExpenditureFromRow(row);

                    if (expenditure != null) {
                        // 保存到資料庫
                        expenditureRepository.save(expenditure);
                        logger.info("Successfully saved expenditure for order number: " + expenditure.getOrderNumber());
                    } else {
                        logger.warning("Row " + rowIndex + " contained invalid data, skipping.");
                    }

                } catch (Exception e) {
                    // 記錄錯誤並繼續處理後續行
                    logger.severe("Error processing row " + rowIndex + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            // 捕獲並重新拋出 IO 異常
            logger.severe("Error reading Excel file: " + e.getMessage());
            throw new IOException("Error processing Excel file: " + e.getMessage(), e);
        }
    }

    // 將 java.util.Date 轉換為 java.time.LocalDate
    private LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // 根據行數據創建 Expenditure 對象
    private Expenditure createExpenditureFromRow(XSSFRow row) {
        try {
            // 轉換每列數據，並進行空值和類型檢查
            LocalDate date = convertToLocalDate(row.getCell(0).getDateCellValue());
            String orderNumber = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
            String unit = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
            String companyHeader = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : "";
            String productName = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "";
            int quantity = row.getCell(5) != null ? (int) row.getCell(5).getNumericCellValue() : 0;
            BigDecimal unitPrice = row.getCell(6) != null ? BigDecimal.valueOf(row.getCell(6).getNumericCellValue()) : BigDecimal.ZERO;
            BigDecimal total = row.getCell(7) != null ? BigDecimal.valueOf(row.getCell(7).getNumericCellValue()) : BigDecimal.ZERO;
            BigDecimal tax = row.getCell(8) != null ? BigDecimal.valueOf(row.getCell(8).getNumericCellValue()) : BigDecimal.ZERO;
            String accountingMonth = row.getCell(9) != null ? row.getCell(9).getStringCellValue() : "";
            LocalDate voucherDate = row.getCell(10) != null ? convertToLocalDate(row.getCell(10).getDateCellValue()) : null;
            String voucherNumber = row.getCell(11) != null ? row.getCell(11).getStringCellValue() : "";
            BigDecimal voucherAmount = row.getCell(12) != null ? BigDecimal.valueOf(row.getCell(12).getNumericCellValue()) : BigDecimal.ZERO;
            BigDecimal payableAmount = row.getCell(13) != null ? BigDecimal.valueOf(row.getCell(13).getNumericCellValue()) : BigDecimal.ZERO;
            BigDecimal paidAmount = row.getCell(14) != null ? BigDecimal.valueOf(row.getCell(14).getNumericCellValue()) : BigDecimal.ZERO;
            BigDecimal currentPayableAmount = row.getCell(15) != null ? BigDecimal.valueOf(row.getCell(15).getNumericCellValue()) : BigDecimal.ZERO;
            String paymentUnit = row.getCell(16) != null ? row.getCell(16).getStringCellValue() : "";
            String remarks = row.getCell(17) != null ? row.getCell(17).getStringCellValue() : "";

            // 構建 Expenditure 對象並設置屬性
            Expenditure expenditure = new Expenditure();
            expenditure.setDate(date);
            expenditure.setOrderNumber(orderNumber);
            expenditure.setUnit(unit);
            expenditure.setCompanyHeader(companyHeader);
            expenditure.setProductName(productName);
            expenditure.setQuantity(quantity);
            expenditure.setUnitPrice(unitPrice);
            expenditure.setTotal(total);
            expenditure.setTax(tax);
            expenditure.setAccountingMonth(accountingMonth);
            expenditure.setVoucherDate(voucherDate);
            expenditure.setVoucherNumber(voucherNumber);
            expenditure.setVoucherAmount(voucherAmount);
            expenditure.setPayableAmount(payableAmount);
            expenditure.setPaidAmount(paidAmount);
            expenditure.setCurrentPayableAmount(currentPayableAmount);
            expenditure.setPaymentUnit(paymentUnit);
            expenditure.setRemarks(remarks);

            return expenditure;

        } catch (Exception e) {
            // 捕獲並記錄創建 Expenditure 對象的異常
            logger.severe("Error creating Expenditure object: " + e.getMessage());
            return null;
        }
    }
}
