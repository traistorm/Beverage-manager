package com.example.beveragemanager.DTO;

import com.example.beveragemanager.EntityMix.BillMix;
import com.example.beveragemanager.EntityMix.BillProductTableDinner;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BillDTO {
    private HeaderReturnMix info;
    private List<BillMix> billList = new ArrayList<>();
}
