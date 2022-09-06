package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.User;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTOReturnClient {
    private HeaderReturnMix info;
    private List<User> userList = new ArrayList<>();
}
