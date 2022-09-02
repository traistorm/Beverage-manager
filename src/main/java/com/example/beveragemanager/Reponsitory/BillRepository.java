package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByBillid(Integer billID);
    Bill findByBillid(Integer billid);
}
