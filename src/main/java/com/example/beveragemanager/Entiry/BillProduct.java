package com.example.beveragemanager.Entiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "billid", insertable = false, updatable = false) // thông qua khóa ngoại
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Bill bill;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productid", insertable = false, updatable = false) // thông qua khóa ngoại
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;
}
