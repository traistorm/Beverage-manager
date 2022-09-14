package com.example.beveragemanager.Entiry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    // MapopedBy trỏ tới tên biến Address ở trong Person.
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private List<Bill> billList;

}
