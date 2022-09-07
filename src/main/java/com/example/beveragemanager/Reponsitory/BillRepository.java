package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.Bill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByBillid(Integer billID);
    List<Bill> findAllByStaffid(String staffID);
    Bill findByBillid(Integer billid);
    List<Bill> findAllByDinnertableid(String dinnerTableID);
    List<Bill> findAllByConfirmed(Boolean confirmed, Pageable pageable);
}
