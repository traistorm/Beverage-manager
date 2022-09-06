package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.DinnerTable;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DinnerTableDTO {
    private HeaderReturnMix info;
    private List<DinnerTable> dinnerTableList = new ArrayList<>();
}
