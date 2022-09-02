package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private HeaderReturnMix info;
    private List<Product> productList;
}
