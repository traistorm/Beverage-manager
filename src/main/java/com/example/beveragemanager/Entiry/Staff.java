package com.example.beveragemanager.Entiry;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "staff")
public class Staff {
    @Id
    private String staffid;
    private String staffname;
    private LocalDate staffdateofbirth;
    private Integer sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workstarttime;

}
