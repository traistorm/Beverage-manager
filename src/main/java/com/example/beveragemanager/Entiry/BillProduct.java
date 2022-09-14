package com.example.beveragemanager.Entiry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "billproduct")
@IdClass(BillProductID.class)
public class BillProduct {
    @Id
    private String productid;
    @Id
    private Integer billid;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "billid", insertable = false, updatable = false) // thông qua khóa ngoại
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "productid", insertable = false, updatable = false) // thông qua khóa ngoại
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;
}
