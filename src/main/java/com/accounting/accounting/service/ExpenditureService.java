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
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0); // 獲取第一個工作表

            // 遍歷 Excel 中的每一行，跳過表頭行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null || row.getCell(0) == null) {
                    logger.warning("Row " + rowIndex + " is empty or null, skipping.");
                    continue;
                }

                try {
                    Expenditure expenditure = createExpenditureFromRow(row);

                    if (expenditure != null) {
                        expenditureRepository.save(expenditure);
                        logger.info("Successfully saved expenditure for order number: " + expenditure.getOrderNumber());
                    } else {
                        logger.warning("Row " + rowIndex + " contained invalid data, skipping.");
                    }

                } catch (Exception e) {
                    logger.severe("Error processing row " + rowIndex + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
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
            LocalDate date = convertToLocalDate(row.getCell(0).getDateCellValue());
            String orderNumber = getStringCellValue(row, 1);
            String unit = getStringCellValue(row, 2);
            String companyHeader = getStringCellValue(row, 3);
            String productName = getStringCellValue(row, 4);
            int quantity = getIntCellValue(row, 5);
            BigDecimal unitPrice = getBigDecimalCellValue(row, 6);
            BigDecimal total = getBigDecimalCellValue(row, 7);
            BigDecimal tax = getBigDecimalCellValue(row, 8);
            String accountingMonth = getStringCellValue(row, 9);
            LocalDate voucherDate = row.getCell(10) != null ? convertToLocalDate(row.getCell(10).getDateCellValue()) : null;
            String voucherNumber = getStringCellValue(row, 11);
            BigDecimal voucherAmount = getBigDecimalCellValue(row, 12);
            BigDecimal payableAmount = getBigDecimalCellValue(row, 13);
            BigDecimal paidAmount = getBigDecimalCellValue(row, 14);
            BigDecimal currentPayableAmount = getBigDecimalCellValue(row, 15);
            String paymentUnit = getStringCellValue(row, 16);
            String remarks = getStringCellValue(row, 17);

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
            logger.severe("Error creating Expenditure object: " + e.getMessage());
            return null;
        }
    }

    // Helper methods for reading cell values
    private String getStringCellValue(XSSFRow row, int cellIndex) {
        return row.getCell(cellIndex) != null ? row.getCell(cellIndex).getStringCellValue() : "";
    }

    private int getIntCellValue(XSSFRow row, int cellIndex) {
        return row.getCell(cellIndex) != null ? (int) row.getCell(cellIndex).getNumericCellValue() : 0;
    }

    private BigDecimal getBigDecimalCellValue(XSSFRow row, int cellIndex) {
        return row.getCell(cellIndex) != null ? BigDecimal.valueOf(row.getCell(cellIndex).getNumericCellValue()) : BigDecimal.ZERO;
    }
}
