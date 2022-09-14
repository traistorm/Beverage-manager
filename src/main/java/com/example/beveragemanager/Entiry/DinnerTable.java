package com.example.beveragemanager.Entiry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "dinnertable")
public class DinnerTable {
    @Id
    private String dinnertableid;
    private String dinnertablename;

    @OneToMany(mappedBy = "dinnerTable", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    // MapopedBy trỏ tới tên biến Address ở trong Person.
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoog sử dụng trong toString()
    private List<Bill> billList;
}
