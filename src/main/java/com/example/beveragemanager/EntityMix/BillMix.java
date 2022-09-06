package com.example.beveragemanager.EntityMix;

import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.DinnerTable;
import com.example.beveragemanager.Entiry.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BillMix {
    private Bill bill;
    private DinnerTable dinnerTable;
    private List<Product> productList = new ArrayList<>();
}
