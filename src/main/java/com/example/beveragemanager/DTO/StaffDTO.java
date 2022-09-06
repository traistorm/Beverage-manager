package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StaffDTO {
    private HeaderReturnMix info;
    private List<Staff> staffList = new ArrayList<>();
}
