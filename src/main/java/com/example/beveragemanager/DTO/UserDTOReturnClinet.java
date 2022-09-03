package com.example.beveragemanager.DTO;

import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.Entiry.User;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import lombok.Data;

import java.util.List;

@Data
public class UserDTOReturnClinet {
    private HeaderReturnMix info;
    private List<User> userList;
}
