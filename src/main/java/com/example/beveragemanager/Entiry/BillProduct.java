package com.example.beveragemanager.Entiry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
}
