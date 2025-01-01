package com.accounting.accounting.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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

    public void saveExcelData(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0); // 獲取第一個工作表
            List<Expenditure> expenditures = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null || row.getCell(0) == null) {
                    logger.warning("Row " + rowIndex + " is empty or null, skipping.");
                    continue;
                }

                try {
                    Expenditure expenditure = createExpenditureFromRow(row);
                    if (expenditure != null) {
                        expenditures.add(expenditure);
                    } else {
                        logger.warning("Row " + rowIndex + " contained invalid data, skipping.");
                    }

                } catch (Exception e) {
                    logger.severe("Error processing row " + rowIndex + ": " + e.getMessage());
                }
            }

            if (!expenditures.isEmpty()) {
                expenditureRepository.saveAll(expenditures);
                logger.info("Successfully saved " + expenditures.size() + " expenditures.");
            }

        } catch (IOException e) {
            logger.severe("Error reading Excel file: " + e.getMessage());
            throw new IOException("Error processing Excel file: " + e.getMessage(), e);
        }
    }

    private LocalDate convertToLocalDate(java.util.Date date) {
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null; // 避免 NullPointerException
    }

    private Expenditure createExpenditureFromRow(XSSFRow row) {
        try {
            LocalDate date = getLocalDateFromCell(row.getCell(0));
            if (date == null) {
                logger.warning("Date is missing or invalid in row " + row.getRowNum());
                return null; // 跳過此行
            }

            String orderNumber = getStringCellValue(row.getCell(1));
            String unit = getStringCellValue(row.getCell(2));
            String companyHeader = getStringCellValue(row.getCell(3));
            String productName = getStringCellValue(row.getCell(4));
            int quantity = (int) getNumericCellValue(row.getCell(5));
            BigDecimal unitPrice = BigDecimal.valueOf(getNumericCellValue(row.getCell(6)));
            BigDecimal total = BigDecimal.valueOf(getNumericCellValue(row.getCell(7)));
            BigDecimal tax = BigDecimal.valueOf(getNumericCellValue(row.getCell(8)));
            String accountingMonth = getStringCellValue(row.getCell(9));
            LocalDate voucherDate = getLocalDateFromCell(row.getCell(10));
            String voucherNumber = getStringCellValue(row.getCell(11));
            BigDecimal voucherAmount = BigDecimal.valueOf(getNumericCellValue(row.getCell(12)));
            BigDecimal payableAmount = BigDecimal.valueOf(getNumericCellValue(row.getCell(13)));
            BigDecimal paidAmount = BigDecimal.valueOf(getNumericCellValue(row.getCell(14)));
            BigDecimal currentPayableAmount = BigDecimal.valueOf(getNumericCellValue(row.getCell(15)));
            String paymentUnit = getStringCellValue(row.getCell(16));
            String remarks = getStringCellValue(row.getCell(17));

            // 檢查必填字段是否為空
            if (unit == null || unit.trim().isEmpty() || quantity < 0 || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
                logger.warning("Invalid data in row " + row.getRowNum());
                return null;
            }

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

    private LocalDate getLocalDateFromCell(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果數字被格式化為日期，進行正常的日期轉換
                    return convertToLocalDate(cell.getDateCellValue());
                } else {
                    // 如果數字未被格式化為日期，將其視為 Excel 日期數字進行處理
                    logger.warning("Cell is numeric but not formatted as a date: " + cell.toString());
                    return convertExcelNumberToLocalDate(cell.getNumericCellValue());
                }
            } else if (cell.getCellType() == CellType.STRING) {
                try {
                    // 處理字符串格式的日期
                    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                            .parseCaseInsensitive()
                            .appendPattern("[yyyy-MM-dd][dd/MM/yyyy][MM/dd/yyyy]")
                            .toFormatter(Locale.ENGLISH);
                    return LocalDate.parse(cell.getStringCellValue().trim(), formatter);
                } catch (DateTimeParseException e) {
                    logger.warning("Unable to parse date from cell: " + e.getMessage());
                }
            }
        }
        return null; // 日期處理失敗時返回 null
    }

    // 將 Excel 的數字日期轉換為 LocalDate
    private LocalDate convertExcelNumberToLocalDate(double excelDateNumber) {
        // 將 Excel 數字日期轉換為 Java LocalDate
        return LocalDate.of(1900, 1, 1).plusDays((long) excelDateNumber - 2); // Excel 日期的偏移處理
    }

    private String getStringCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.STRING) ? cell.getStringCellValue().trim() : "";
    }

    private double getNumericCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? cell.getNumericCellValue() : 0;
    }
}
