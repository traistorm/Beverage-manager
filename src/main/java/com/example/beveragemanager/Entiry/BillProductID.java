package com.example.beveragemanager.Entiry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Data
public class BillProductID implements Serializable {
    private String productid;
    private Integer billid;
    public BillProductID(String productid, Integer billid)
    {
        this.productid = productid;
        this.billid = billid;
    }
    public BillProductID(){};
}
