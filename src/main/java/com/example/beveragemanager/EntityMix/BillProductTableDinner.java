package com.example.beveragemanager.EntityMix;

import com.example.beveragemanager.Entiry.*;
import lombok.Data;

import java.util.List;

@Data
public class BillProductTableDinner {
    private Bill bill;
    private List<Product> productList;
    private DinnerTable dinnerTable;
    private Staff staff;
}
