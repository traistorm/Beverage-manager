package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.Staff;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    List<Staff> findAllByStaffid(String staffID);
    Staff findByStaffid(String staffID);
}
