package com.example.beveragemanager.Entiry;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "bill")
public class Bill {
    @Id
    private Integer billid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymenttime;
    private String staffid;
    private String dinnertableid;
}
