package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.DinnerTableDTO;
import com.example.beveragemanager.DTO.StaffDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.DinnerTable;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import com.example.beveragemanager.Reponsitory.DinnerTableReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DinnerTableService {
    @Autowired
    DinnerTableReponsitory dinnerTableReponsitory;
    @Autowired
    UserService userService;
    @Autowired
    @Lazy
    BillService billService;
    @Autowired
    @Lazy
    BillProductService billProductService;

    public ResponseEntity<DinnerTableDTO> findAll(String token, Integer page, Integer itemPerPage) {
        try {

            DinnerTableDTO dinnerTableDTO = new DinnerTableDTO();

            if (page != null && itemPerPage != null) {
                List<DinnerTable> dinnerTableListReturn = dinnerTableReponsitory.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("dinnertableid").ascending())).getContent();
                HeaderReturnMix info = new HeaderReturnMix();
                info.setMaxPage((int) ((dinnerTableReponsitory.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                info.setCurrentPage(page);
                info.setItemPerPage(itemPerPage);
                dinnerTableDTO.setInfo(info);
                dinnerTableDTO.setDinnerTableList(dinnerTableListReturn);
            } else {
                List<DinnerTable> dinnerTableList = dinnerTableReponsitory.findAll();
                HeaderReturnMix info = new HeaderReturnMix();
                info.setMaxPage(null);
                info.setCurrentPage(null);
                info.setItemPerPage(null);
                dinnerTableDTO.setInfo(info);
                dinnerTableDTO.setDinnerTableList(dinnerTableList);
            }
            return new ResponseEntity<>(dinnerTableDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public ResponseEntity<DinnerTableDTO> addDinnerTable(String token, DinnerTable dinnerTable) {
        try {

            DinnerTable dinnerTableFound = dinnerTableReponsitory.findByDinnertableid(dinnerTable.getDinnertableid());
            if (dinnerTableFound == null) {
                System.out.println("Check");
                save(dinnerTable);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DinnerTableDTO> updateDinnerTable(String token, DinnerTable dinnerTable, String dinnertableidold) {
        try {

            if (dinnertableidold.equals(dinnerTable.getDinnertableid())) {
                //System.out.println("TEST");
                save(dinnerTable);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                if (dinnerTableReponsitory.findByDinnertableid(dinnerTable.getDinnertableid()) == null) {
                    save(dinnerTable);
                    List<Bill> billList = billService.findAllByDinnertableid(dinnertableidold);
                    // Update staff id in bill list
                    for (Bill bill : billList) {
                        bill.setDinnertableid(dinnerTable.getDinnertableid());
                    }
                    billService.saveAll(billList);
                    // Delete old staff with old id
                    delete(dinnerTableReponsitory.findByDinnertableid(dinnertableidold));
                    return new ResponseEntity<>(null, HttpStatus.OK);
                } else // new id of staff is existed
                {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DinnerTableDTO> deleteDinnerTable(String token, String dinnertableid) {
        try {

            DinnerTable dinnerTableFound = dinnerTableReponsitory.findByDinnertableid(dinnertableid);
            List<Bill> billList = billService.findAllByDinnertableid(dinnertableid);
            for (Bill bill : billList) {
                List<BillProduct> billProductList = billProductService.findAllByBillid(bill.getBillid());
                billProductService.deleteAll(billProductList);
            }
            billService.deleteAll(billList);
            delete(dinnerTableFound);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DinnerTableDTO> findDinnerTableByDinnertableid(String token, String dinnertableid) {
        try {

            DinnerTableDTO dinnerTableDTO = new DinnerTableDTO();
            dinnerTableDTO.getDinnerTableList().add(dinnerTableReponsitory.findByDinnertableid(dinnertableid));
            return new ResponseEntity<>(dinnerTableDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public DinnerTable findByDinnertableid(String dinnerTableID) {
        return dinnerTableReponsitory.findByDinnertableid(dinnerTableID);
    }

    @Transactional
    public void save(DinnerTable dinnerTable) {
        dinnerTableReponsitory.save(dinnerTable);
    }

    @Transactional
    public void delete(DinnerTable dinnerTable) {
        dinnerTableReponsitory.delete(dinnerTable);
    }
}
