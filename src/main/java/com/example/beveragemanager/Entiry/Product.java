package com.example.beveragemanager.Entiry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    private String productid;
    private String productname;
    private Integer productprice;
    private Integer itemstatus; // 0 : is not available, 1 : available
    private Float discount; // %
    private String description;
    private String imagelink;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    // MapopedBy trỏ tới tên biến Address ở trong Person.
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private List<BillProduct> billProducts;
}
