package com.accounting.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "expenditure")
public class Expenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "日期", nullable = false)
    private LocalDate date; // 修改為 LocalDate

    @Column(name = "單號", nullable = true)
    private String orderNumber;

    @Column(name = "單位", nullable = false)
    private String unit;

    @Column(name = "公司抬頭", nullable = false)
    private String companyHeader;

    @Column(name = "品名", nullable = false)
    private String productName;

    @Column(name = "數量", nullable = false)
    private Integer quantity;

    @Column(name = "單價", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "合計", nullable = false)
    private BigDecimal total;

    @Column(name = "稅", nullable = true)
    private BigDecimal tax;

    @Column(name = "帳列月", nullable = false)
    private String accountingMonth;

    @Column(name = "憑證日期", nullable = true)
    private LocalDate voucherDate; // 修改為 LocalDate

    @Column(name = "憑證編號", nullable = true)
    private String voucherNumber;

    @Column(name = "憑證金額", nullable = true)
    private BigDecimal voucherAmount;

    @Column(name = "應付金額", nullable = false)
    private BigDecimal payableAmount;

    @Column(name = "已付金額", nullable = true)
    private BigDecimal paidAmount;

    @Column(name = "本月應付金額", nullable = true)
    private BigDecimal currentPayableAmount;

    @Column(name = "付款單位", nullable = false)
    private String paymentUnit;

    @Column(name = "備註", nullable = true)
    private String remarks;

    @CreationTimestamp
    @Column(name = "創建時間", updatable = false, nullable = false)
    private LocalDate createTime;

    @UpdateTimestamp
    @Column(name = "修改時間", nullable = false)
    private LocalDate updateTime;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCompanyHeader() {
        return companyHeader;
    }

    public void setCompanyHeader(String companyHeader) {
        this.companyHeader = companyHeader;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getAccountingMonth() {
        return accountingMonth;
    }

    public void setAccountingMonth(String accountingMonth) {
        this.accountingMonth = accountingMonth;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public BigDecimal getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(BigDecimal voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getCurrentPayableAmount() {
        return currentPayableAmount;
    }

    public void setCurrentPayableAmount(BigDecimal currentPayableAmount) {
        this.currentPayableAmount = currentPayableAmount;
    }

    public String getPaymentUnit() {
        return paymentUnit;
    }

    public void setPaymentUnit(String paymentUnit) {
        this.paymentUnit = paymentUnit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }
}
