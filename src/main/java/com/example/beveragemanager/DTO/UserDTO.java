package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private User user;
    private String result = "";
    private List<User> userList;
    private Integer maxPage;
}
