package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.DinnerTable;
import lombok.Data;

import java.util.List;

@Data
public class DinnerTableDTO {
    private Integer maxPage;
    private List<DinnerTable> dinnerTableList;
}
