package com.example.beveragemanager.Entiry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    private String productid;
    private String productname;
    private Integer productprice;
    private Integer remainingamount;
    private Float discount; // %
    private String description;
    private String imagelink;
}
