package com.example.beveragemanager.Entiry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(name = "account",uniqueConstraints=@UniqueConstraint(columnNames={"userid","username"}))
public class User {
    @Id
    private Integer userid;
    private String username;
    private String password;
    private String role;
    private String token;
    private Long initializationtokentime;
    private String dinnertableid;
}
