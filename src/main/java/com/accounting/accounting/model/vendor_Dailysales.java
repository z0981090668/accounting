package com.accounting.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "daily_sales")
public class vendor_Dailysales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "sales_foh")
    private BigDecimal salesFoh;

    @Column(name = "sales_hoh")
    private BigDecimal salesHoh;

}
