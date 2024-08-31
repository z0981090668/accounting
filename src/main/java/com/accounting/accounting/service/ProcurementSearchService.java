package com.accounting.accounting.service;

import com.accounting.accounting.model.Procurement;
import com.accounting.accounting.repository.ProcurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProcurementSearchService {

    @Autowired
    private ProcurementRepository procurementRepository;

    /**
     * 根據日期範圍查詢
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @return 符合條件的 Procurement 列表
     */
    public List<Procurement> searchByDateRange(LocalDate startDate, LocalDate endDate) {
        return procurementRepository.findByPurchaseDateBetween(startDate, endDate);
    }

    /**
     * 根據采購人查詢
     * @param purchaser 采購人
     * @return 符合條件的 Procurement 列表
     */
    public List<Procurement> searchByPurchaser(String purchaser) {
        if (purchaser == null || purchaser.isEmpty()) {
            return List.of(); // 返回空列表
        }
        return procurementRepository.findByPurchaser(purchaser);
    }

    /**
     * 根據店別查詢
     * @param store 店別
     * @return 符合條件的 Procurement 列表
     */
    public List<Procurement> searchByStore(String store) {
        if (store == null || store.isEmpty()) {
            return List.of(); // 返回空列表
        }
        return procurementRepository.findByStore(store);
    }

    /**
     * 根據日期範圍、采購人和店別進行多條件查詢
     * @param startDate 開始日期
     * @param endDate 結束日期
     * @param purchaser 采購人
     * @param store 店別
     * @return 符合條件的 Procurement 列表
     */
    public List<Procurement> searchByMultipleCriteria(LocalDate startDate, LocalDate endDate, String purchaser, String store) {
        // 根據不同的條件組合進行查詢
        if (startDate != null && endDate != null && (purchaser != null && !purchaser.isEmpty()) && (store != null && !store.isEmpty())) {
            // 查詢指定日期範圍、采購人和店別的所有採購
            return procurementRepository.findByPurchaseDateBetweenAndPurchaserAndStore(startDate, endDate, purchaser, store);
        } else if (startDate != null && endDate != null && (purchaser != null && !purchaser.isEmpty())) {
            // 查詢指定日期範圍和采購人的所有採購
            return procurementRepository.findByPurchaseDateBetweenAndPurchaser(startDate, endDate, purchaser);
        } else if (startDate != null && endDate != null && (store != null && !store.isEmpty())) {
            // 查詢指定日期範圍和店別的所有採購
            return procurementRepository.findByPurchaseDateBetweenAndStore(startDate, endDate, store);
        } else if (startDate != null && endDate != null) {
            // 只查詢指定日期範圍的所有採購
            return procurementRepository.findByPurchaseDateBetween(startDate, endDate);
        } else if (purchaser != null && !purchaser.isEmpty()) {
            // 只查詢指定采購人的所有採購
            return procurementRepository.findByPurchaser(purchaser);
        } else if (store != null && !store.isEmpty()) {
            // 只查詢指定店別的所有採購
            return procurementRepository.findByStore(store);
        } else {
            // 如果沒有任何條件，返回空列表
            return List.of();
        }
    }
}
