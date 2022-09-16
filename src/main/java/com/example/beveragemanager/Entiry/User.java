package com.example.beveragemanager.Entiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "account",uniqueConstraints=@UniqueConstraint(columnNames={"userid","username"}))
public class User {
    @Id
    private Integer userid;
    private String username;
    private String password;
    private String role;
    //private String token;
    //private Long initializationtokentime;
    private String dinnertableid;
    @JsonIgnore
    @OneToOne // Đánh dấu có mỗi quan hệ 1-1 với Person ở phía dưới
    @JoinColumn(name = "dinnertableid", insertable = false, updatable = false) // Liên kết với nhau qua khóa ngoại person_id
    private DinnerTable dinnerTable;
}
