package com.accounting.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accounting.accounting.model.Procurement; // 引入對應的實體類

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Long> {
    // 查詢指定日期範圍內的所有採購
    List<Procurement> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    // 查詢指定采購人的所有採購
    List<Procurement> findByPurchaser(String purchaser);

    // 查詢指定店別的所有採購
    List<Procurement> findByStore(String store);

    // 查詢在指定日期範圍、采購人和店別的所有採購
    List<Procurement> findByPurchaseDateBetweenAndPurchaserAndStore(LocalDate startDate, LocalDate endDate, String purchaser, String store);

    // 查詢在指定日期範圍和采購人的所有採購
    List<Procurement> findByPurchaseDateBetweenAndPurchaser(LocalDate startDate, LocalDate endDate, String purchaser);

    // 查詢在指定日期範圍和店別的所有採購
    List<Procurement> findByPurchaseDateBetweenAndStore(LocalDate startDate, LocalDate endDate, String store);
}
