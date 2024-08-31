package com.accounting.accounting.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_order")
public class Procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主鍵，唯一標識每個請購單項目

    @Column(name = "店別", length = 50, nullable = false)
    private String store; // 店別

    @Column(name = "週期", length = 50)
    private String cycle; // 周期

    @Column(name = "項次", nullable = false)
    private Integer itemNumber; // 項次

    @Column(name = "儲位", length = 100)
    private String storageLocation; // 儲位

    @Column(name = "品名", length = 100, nullable = false)
    private String itemName; // 品名

    @Column(name = "說明", columnDefinition = "TEXT",nullable = true)
    private String description; // 說明

    @Column(name = "單價", precision = 10, scale = 2)
    private BigDecimal unitPrice; // 單價

    @Column(name = "請購數量", nullable = false)
    private Integer purchaseQuantity; // 請購數量

    @Column(name = "單位", length = 20)
    private String unit; // 單位

    @Column(name = "金額", precision = 10, scale = 2)
    private BigDecimal amount; // 金額

    @Column(name = "請購日期")
    private LocalDate purchaseDate; // 請購日期

    @Column(name = "請購人", length = 100)
    private String purchaser; // 請購人

    // 無參數構造方法
    public Procurement() {}

    // 全參數構造方法
    public Procurement(String store, String cycle, Integer itemNumber, String storageLocation, String itemName,
                       String description, BigDecimal unitPrice, Integer purchaseQuantity, String unit,
                       BigDecimal amount, LocalDate purchaseDate, String purchaser) {
        this.store = store;
        this.cycle = cycle;
        this.itemNumber = itemNumber;
        this.storageLocation = storageLocation;
        this.itemName = itemName;
        this.description = description;
        this.unitPrice = unitPrice;
        this.purchaseQuantity = purchaseQuantity;
        this.unit = unit;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.purchaser = purchaser;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }
}
