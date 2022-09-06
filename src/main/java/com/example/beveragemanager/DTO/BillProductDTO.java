package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BillProductDTO {
    private Integer maxPage;
    private List<BillProduct> billProductList = new ArrayList<>();
    private List<Bill> billList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
}
