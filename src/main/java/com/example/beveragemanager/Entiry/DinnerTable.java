package com.example.beveragemanager.Entiry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "dinnertable")
public class DinnerTable {
    @Id
    private String dinnertableid;
    private String dinnertablename;
}
