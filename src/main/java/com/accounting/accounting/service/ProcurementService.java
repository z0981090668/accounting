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

import com.accounting.accounting.model.Procurement; // 引入對應的實體類
import com.accounting.accounting.repository.ProcurementRepository; // 引入對應的Repository

@Service
public class ProcurementService {

    @Autowired
    private ProcurementRepository procurementRepository; // 自動注入Repository，用於數據庫操作

    private static final Logger logger = Logger.getLogger(ProcurementService.class.getName());

    /**
     * 保存Excel檔案中的數據到資料庫中
     * @param file 上傳的Excel文件
     * @throws IOException 如果讀取文件出現異常，則拋出IOException
     */
    public void saveExcelData(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) { // 使用try-with-resources自動關閉資源
            XSSFSheet sheet = workbook.getSheetAt(0); // 獲取第一個工作表

            // 遍歷 Excel 中的每一行，跳過表頭行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                // 檢查行是否為空
                if (row == null || row.getCell(0) == null) {
                    logger.warning("Row " + rowIndex + " is empty or null, skipping.");
                    continue; // 如果當前行為空或無效，則跳過該行
                }

                try {
                    Procurement procurement = createProcurementFromRow(row); // 創建一個 Procurement 對象，並從當前行中填充數據

                    if (procurement != null) {
                        procurementRepository.save(procurement); // 將創建的對象保存到資料庫
                        logger.info("Successfully saved procurement with ID: " + procurement.getId());
                    } else {
                        logger.warning("Row " + rowIndex + " contained invalid data, skipping.");
                    }

                } catch (Exception e) {
                    // 如果處理當前行時出現異常，則記錄錯誤信息並繼續處理後續行
                    logger.severe("Error processing row " + rowIndex + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            // 捕獲並處理讀取 Excel 文件時的IO異常
            logger.severe("Error reading Excel file: " + e.getMessage());
            throw new IOException("Error processing Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * 將 java.util.Date 轉換為 java.time.LocalDate
     * @param date 需要轉換的日期
     * @return 對應的 LocalDate 對象
     */
    private LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // 使用系統默認時區將Date轉換為LocalDate
    }

    /**
     * 根據Excel行數據創建 Procurement 對象
     * @param row 當前處理的 Excel 行
     * @return 返回創建的 Procurement 對象，如果數據無效則返回 null
     */
    private Procurement createProcurementFromRow(XSSFRow row) {
        try {
            // 從Excel行中讀取每個欄位的數據
            String store = getStringCellValue(row, 0); // 店別
            String cycle = getStringCellValue(row, 1); // 周期
            Integer itemNumber = getIntCellValue(row, 2); // 項次
            String storageLocation = getStringCellValue(row, 3); // 儲位
            String itemName = getStringCellValue(row, 4); // 品名
            String description = getStringCellValue(row, 5); // 說明
            BigDecimal unitPrice = getBigDecimalCellValue(row, 6); // 單價
            Integer purchaseQuantity = getIntCellValue(row, 7); // 請購數量
            String unit = getStringCellValue(row, 8); // 單位
            BigDecimal amount = getBigDecimalCellValue(row, 9); // 金額
            LocalDate purchaseDate = row.getCell(10) != null ? convertToLocalDate(row.getCell(10).getDateCellValue()) : null; // 請購日期
            String purchaser = getStringCellValue(row, 11); // 請購人

            // 創建 Procurement 對象並設置其屬性
            Procurement procurement = new Procurement();
            procurement.setStore(store);
            procurement.setCycle(cycle);
            procurement.setItemNumber(itemNumber);
            procurement.setStorageLocation(storageLocation);
            procurement.setItemName(itemName);
            procurement.setDescription(description);
            procurement.setUnitPrice(unitPrice);
            procurement.setPurchaseQuantity(purchaseQuantity);
            procurement.setUnit(unit);
            procurement.setAmount(amount);
            procurement.setPurchaseDate(purchaseDate);
            procurement.setPurchaser(purchaser);

            return procurement; // 返回創建的 Procurement 對象

        } catch (Exception e) {
            // 捕獲創建 Procurement 對象時的任何異常，並記錄錯誤信息
            logger.severe("Error creating Procurement object: " + e.getMessage());
            return null;
        }
    }

    /**
     * 輔助方法：獲取單元格的字符串值
     * @param row 當前處理的 Excel 行
     * @param cellIndex 單元格索引
     * @return 返回單元格的字符串值，如果單元格為空則返回空字符串
     */
    private String getStringCellValue(XSSFRow row, int cellIndex) {
        return row.getCell(cellIndex) != null ? row.getCell(cellIndex).getStringCellValue() : "";
    }

    /**
     * 輔助方法：獲取單元格的整數值
     * @param row 當前處理的 Excel 行
     * @param cellIndex 單元格索引
     * @return 返回單元格的整數值，如果單元格為空則返回0
     */
    private int getIntCellValue(XSSFRow row, int cellIndex) {
        return row.getCell(cellIndex) != null ? (int) row.getCell(cellIndex).getNumericCellValue() : 0;
    }

    /**
     * 輔助方法：獲取單元格的 BigDecimal 值
     * @param row 當前處理的 Excel 行
     * @param cellIndex 單元格索引
     * @return 返回單元格的 BigDecimal 值，如果單元格為空則返回 BigDecimal.ZERO
     */
    private BigDecimal getBigDecimalCellValue(XSSFRow row, int cellIndex) {
        return row.getCell(cellIndex) != null ? BigDecimal.valueOf(row.getCell(cellIndex).getNumericCellValue()) : BigDecimal.ZERO;
    }
}
