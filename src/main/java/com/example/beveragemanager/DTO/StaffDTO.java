package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.Staff;
import lombok.Data;

import java.util.List;

@Data
public class StaffDTO {
    private Integer maxPage;
    private List<Staff> staffList;
}
