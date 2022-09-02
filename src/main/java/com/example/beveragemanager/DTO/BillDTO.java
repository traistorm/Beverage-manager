package com.example.beveragemanager.DTO;

import com.example.beveragemanager.EntityMix.BillProductTableDinner;
import lombok.Data;

import java.util.List;

@Data
public class BillDTO {
    private Integer maxPage;
    private List<BillProductTableDinner> result;
}
