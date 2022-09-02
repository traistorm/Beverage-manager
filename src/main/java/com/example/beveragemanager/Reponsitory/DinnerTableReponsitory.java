package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.DinnerTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinnerTableReponsitory extends JpaRepository<DinnerTable, String> {
    DinnerTable findByDinnertableid(String dinnerTableID);
}
