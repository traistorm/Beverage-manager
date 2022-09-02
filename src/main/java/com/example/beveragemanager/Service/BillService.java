package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.BillDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.EntityMix.BillProductTableDinner;
import com.example.beveragemanager.Reponsitory.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
                if (page != null && itemPerPage != null)
                {
                    List<Bill> billList = billRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("billid").ascending())).getContent();
                    billDTO.setMaxPage(billList.size());
                    System.out.println(billList);
                    List<BillProductTableDinner> result = new ArrayList<>();
                    for (Bill bill : billList)
                    {
                        BillProductTableDinner billProductTableDinner = new BillProductTableDinner();
                        billProductList = billProductService.findAllByBillid(bill.getBillid());
                        List<Product> productList = new ArrayList<>();
                        for (BillProduct billProduct : billProductList)
                        {
                            productList.add(productService.findByProductid(billProduct.getProductid()));
                        }

                        billProductTableDinner.setBill(bill);
                        billProductTableDinner.setProductList(productList);
                        billProductTableDinner.setDinnerTable(dinnerTableService.findByDinnertableid(bill.getDinnertableid()));
                        billProductTableDinner.setStaff(staffService.findByStaffid(bill.getStaffid()));
                        result.add(billProductTableDinner);
                    }
                    System.out.println(result);
                    billDTO.setResult(result);

                }
                else
                {
                    /*billDTO.setMaxPage(billList.size());
                    billDTO.setBillList(billList);
                    billDTO.setBillProductList(billProductRepository.findAll());*/
                }


                return new ResponseEntity<>(billDTO, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
    List<Bill> findAll()
    {
        return billRepository.findAll();
    }
}
