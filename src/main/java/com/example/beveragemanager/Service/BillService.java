package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.BillDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.EntityMix.BillMix;
import com.example.beveragemanager.EntityMix.BillProductTableDinner;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import com.example.beveragemanager.Reponsitory.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    @Lazy
    UserService userService;
    @Autowired
    BillProductService billProductService;
    @Autowired
    @Lazy
    ProductService productService;
    @Autowired
    @Lazy
    DinnerTableService dinnerTableService;
    @Autowired
    @Lazy
    StaffService staffService;
    List<Bill> findAllByBillid(Integer billID)
    {
        return billRepository.findAllByBillid(billID);
    }
    Bill findByBillid(Integer billID)
    {
        return billRepository.findByBillid(billID);
    }
    public ResponseEntity<BillDTO> findAll(String token, Integer page, Integer itemPerPage)
    {
        try
        {
            UserDTO userDTO = userService.login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                //System.out.println("Check");
                BillDTO billDTO = new BillDTO();
                List<BillProduct> billProductList = new ArrayList<>();
                billDTO.setBillList(new ArrayList<>());
                if (page != null && itemPerPage != null)
                {
                    List<Bill> billList = billRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("billid").ascending())).getContent();
                    HeaderReturnMix info = new HeaderReturnMix();
                    info.setMaxPage((int) ((billRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                    info.setCurrentPage(page);
                    info.setItemPerPage(itemPerPage);
                    billDTO.setInfo(info);
                    //System.out.println(billList);
                    for (Bill bill : billList)
                    {
                        BillMix billMix = new BillMix();
                        billMix.setBill(bill);

                        BillProductTableDinner billProductTableDinner = new BillProductTableDinner();
                        billProductList = billProductService.findAllByBillid(bill.getBillid());
                        List<Product> productList = new ArrayList<>();
                        for (BillProduct billProduct : billProductList)
                        {
                            productList.add(productService.findByProductid(billProduct.getProductid()));
                        }
                        billMix.setProductList(productList);
                        billMix.setDinnerTable(dinnerTableService.findByDinnertableid(bill.getDinnertableid()));
                        billDTO.getBillList().add(billMix);
                    }
                    return new ResponseEntity<>(billDTO, HttpStatus.OK);

                }
                else
                {
                    List<Bill> billList = billRepository.findAll(Pageable.unpaged()).getContent();
                    /*PageRequest.of(0, Integer.MAX_VALUE);*/ // Một cách khác lấy full giá trị
                    HeaderReturnMix info = new HeaderReturnMix();
                    info.setMaxPage(null);
                    info.setCurrentPage(null);
                    info.setItemPerPage(null);
                    billDTO.setInfo(info);
                    //System.out.println(billList);
                    for (Bill bill : billList)
                    {
                        BillMix billMix = new BillMix();
                        billMix.setBill(bill);

                        BillProductTableDinner billProductTableDinner = new BillProductTableDinner();
                        billProductList = billProductService.findAllByBillid(bill.getBillid());
                        List<Product> productList = new ArrayList<>();
                        for (BillProduct billProduct : billProductList)
                        {
                            productList.add(productService.findByProductid(billProduct.getProductid()));
                        }
                        billMix.setProductList(productList);
                        billMix.setDinnerTable(dinnerTableService.findByDinnertableid(bill.getDinnertableid()));
                        billDTO.getBillList().add(billMix);
                    }
                    return new ResponseEntity<>(billDTO, HttpStatus.OK);
                }
            }
            else if (userDTO.getResult().equals("Token timeout"))
            {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            else
            {

                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    List<Bill> findAll()
    {
        return billRepository.findAll();
    }
    List<Bill> findAllByStaffid(String staffID)
    {
        return billRepository.findAllByStaffid(staffID);
    }
    List<Bill> findAllByDinnertableid(String staffID)
    {
        return billRepository.findAllByStaffid(staffID);
    }
    @Transactional
    public void saveAll(List<Bill> billList)
    {
        billRepository.saveAll(billList);
    }
    @Transactional
    public void deleteAll(List<Bill> billList)
    {
        billRepository.deleteAll(billList);
    }
}
