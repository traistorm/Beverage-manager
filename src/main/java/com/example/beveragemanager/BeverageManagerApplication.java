package com.example.beveragemanager;

import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.Reponsitory.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeverageManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeverageManagerApplication.class, args);
    }

}
