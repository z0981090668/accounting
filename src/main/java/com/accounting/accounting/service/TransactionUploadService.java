package com.accounting.accounting.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
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

import com.accounting.accounting.model.Transaction;
import com.accounting.accounting.model.BankAccount;
import com.accounting.accounting.repository.TransactionRepository;

@Service
public class TransactionUploadService {

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Logger logger = Logger.getLogger(TransactionUploadService.class.getName());

    /**
     * 保存來自 Excel 文件的 PST 資料到數據庫。
     * @param file 上傳的 Excel 文件
     * @throws IOException 文件處理過程中的異常
     */
    public void savePSTExcelData(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            // 獲取 Excel 文件的第一個工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<Transaction> transactions = new ArrayList<>();

            // 遍歷工作表的每一行（從第二行開始）
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null || row.getCell(0) == null) {
                    // 如果行為空或第一個單元格為空，跳過該行
                    logger.warning("Row " + rowIndex + " is empty or null, skipping.");
                    continue;
                }

                try {
                    // 創建 Transaction 對象
                    Transaction transaction = createTransactionFromRow(row);
                    if (transaction != null) {
                        // 如果創建成功，加入列表
                        transactions.add(transaction);
                    } else {
                        logger.warning("Row " + rowIndex + " contained invalid data, skipping.");
                    }

                } catch (Exception e) {
                    // 捕捉處理行中的異常
                    logger.severe("Error processing row " + rowIndex + ": " + e.getMessage());
                }
            }

            // 如果有有效的交易，批量保存到數據庫
            if (!transactions.isEmpty()) {
                transactionRepository.saveAll(transactions);
                logger.info("Successfully saved " + transactions.size() + " transactions.");
            }

        } catch (IOException e) {
            // 處理文件讀取異常
            logger.severe("Error reading Excel file: " + e.getMessage());
            throw new IOException("Error processing Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * 根據 Excel 行創建 Transaction 對象。
     * @param row Excel 行
     * @return Transaction 對象，若數據無效返回 null
     */
    private Transaction createTransactionFromRow(XSSFRow row) {
        try {
            // 獲取交易日期
            LocalDate transactionDate = getLocalDateFromCell(row.getCell(0));
            // 獲取提款金額
            BigDecimal withdrawal = BigDecimal.valueOf(getNumericCellValue(row.getCell(1)));
            // 獲取存款金額
            BigDecimal deposit = BigDecimal.valueOf(getNumericCellValue(row.getCell(2)));
            // 獲取交易後餘額
            BigDecimal balance = BigDecimal.valueOf(getNumericCellValue(row.getCell(3)));
            // 獲取描述
            String description = getStringCellValue(row.getCell(4));
            // 獲取手續費
            BigDecimal fee = BigDecimal.valueOf(getNumericCellValue(row.getCell(5)));

            // 驗證必要字段是否有效
            if (transactionDate == null || balance.compareTo(BigDecimal.ZERO) < 0) {
                logger.warning("Invalid data in row " + row.getRowNum());
                return null;
            }

            // 創建 Transaction 對象並設置屬性
            Transaction transaction = new Transaction();
            transaction.setTransactionDate(transactionDate);
            transaction.setWithdrawal(withdrawal);
            transaction.setDeposit(deposit);
            transaction.setBalance(balance);
            transaction.setDescription(description);
            transaction.setFee(fee);

            // 設置默認的 BankAccount（此處假設 ID 為 1 的帳戶）
            BankAccount defaultBankAccount = new BankAccount();
            defaultBankAccount.setId(1L);
            transaction.setBankAccount(defaultBankAccount);

            return transaction;

        } catch (Exception e) {
            // 捕捉創建 Transaction 對象時的異常
            logger.severe("Error creating Transaction object: " + e.getMessage());
            return null;
        }
    }

    /**
     * 從單元格中提取 LocalDate。
     * @param cell 單元格
     * @return LocalDate 對象，若無法解析返回 null
     */
    private LocalDate getLocalDateFromCell(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 若單元格為日期格式，轉換為 LocalDate
                    return cell.getDateCellValue().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                }
            }
        }
        return null;
    }

    /**
     * 從單元格中提取字符串值。
     * @param cell 單元格
     * @return 字符串值，若無法解析返回空字符串
     */
    private String getStringCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.STRING) ? cell.getStringCellValue().trim() : "";
    }

    /**
     * 從單元格中提取數值。
     * @param cell 單元格
     * @return 數值，若無法解析返回 0
     */
    private double getNumericCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? cell.getNumericCellValue() : 0;
    }

}
