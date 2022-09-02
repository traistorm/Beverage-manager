package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.BillProductID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct, Integer> {
    List<BillProduct> findAllByBillid(Integer billid);

}
