package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.DinnerTable;
import lombok.Data;

@Data
public class LoginDTO {
    private String token = "";
    private String role = "";
    private String username = "";
    private DinnerTable dinnerTable = new DinnerTable();
}
