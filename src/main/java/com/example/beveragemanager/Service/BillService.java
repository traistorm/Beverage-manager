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
import java.time.LocalDate;
import java.util.*;

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

    List<Bill> findAllByBillid(Integer billID) {
        return billRepository.findAllByBillid(billID);
    }

    Bill findByBillid(Integer billID) {
        return billRepository.findByBillid(billID);
    }

    public ResponseEntity<BillDTO> findAll(String token, Integer page, Integer itemPerPage, Integer type) {
        try {
            BillDTO billDTO = new BillDTO();
            List<BillProduct> billProductList = new ArrayList<>();
            billDTO.setBillList(new ArrayList<>());
            if (page != null && itemPerPage != null) {
                List<Bill> billList = new ArrayList<>();
                if (type != null) {
                    if (type == 0) // Lấy order
                    {
                        billList = billRepository.findAllByConfirmed(false, PageRequest.of(page - 1, itemPerPage, Sort.by("billid").ascending()));
                    } else if (type == 1) // Lấy bill
                    {
                        billList = billRepository.findAllByConfirmed(true, PageRequest.of(page - 1, itemPerPage, Sort.by("billid").ascending()));
                    } else // lất tất cả
                    {
                        billList = billRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("billid").ascending())).getContent();
                    }
                } else {
                    billList = billRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("billid").ascending())).getContent();

                }

                HeaderReturnMix info = new HeaderReturnMix();
                info.setMaxPage((int) ((billRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                info.setCurrentPage(page);
                info.setItemPerPage(itemPerPage);
                billDTO.setInfo(info);
                //System.out.println(billList);
                for (Bill bill : billList) {
                    BillMix billMix = new BillMix();
                    billMix.setBill(bill);

                    BillProductTableDinner billProductTableDinner = new BillProductTableDinner();
                    billProductList = billProductService.findAllByBillid(bill.getBillid());
                    List<Product> productList = new ArrayList<>();
                    for (BillProduct billProduct : billProductList) {
                        productList.add(productService.findByProductid(billProduct.getProductid()));
                    }
                    billMix.setProductList(productList);
                    billMix.setDinnerTable(dinnerTableService.findByDinnertableid(bill.getDinnertableid()));
                    billDTO.getBillList().add(billMix);
                }
                return new ResponseEntity<>(billDTO, HttpStatus.OK);

            } else {
                List<Bill> billList = billRepository.findAll(Pageable.unpaged()).getContent();
                /*PageRequest.of(0, Integer.MAX_VALUE);*/ // Một cách khác lấy full giá trị
                HeaderReturnMix info = new HeaderReturnMix();
                info.setMaxPage(null);
                info.setCurrentPage(null);
                info.setItemPerPage(null);
                billDTO.setInfo(info);
                //System.out.println(billList);
                for (Bill bill : billList) {
                    BillMix billMix = new BillMix();
                    billMix.setBill(bill);

                    BillProductTableDinner billProductTableDinner = new BillProductTableDinner();
                    billProductList = billProductService.findAllByBillid(bill.getBillid());
                    List<Product> productList = new ArrayList<>();
                    for (BillProduct billProduct : billProductList) {
                        productList.add(productService.findByProductid(billProduct.getProductid()));
                    }
                    billMix.setProductList(productList);
                    billMix.setDinnerTable(dinnerTableService.findByDinnertableid(bill.getDinnertableid()));
                    billDTO.getBillList().add(billMix);
                }
                return new ResponseEntity<>(billDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<BillDTO> findBillByBillid(String token, Integer billid) {
        try {

            BillDTO billDTO = new BillDTO();
            Bill bill = billRepository.findByBillid(billid);
            HeaderReturnMix info = new HeaderReturnMix();
            //info.setMaxPage((int) ((billRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
            //info.setCurrentPage(page);
            //info.setItemPerPage(itemPerPage);
            billDTO.setInfo(info);
            //System.out.println(billList);

            BillMix billMix = new BillMix();
            billMix.setBill(bill);

            List<BillProduct> billProductList = billProductService.findAllByBillid(bill.getBillid());
            List<Product> productList = new ArrayList<>();
            for (BillProduct billProduct : billProductList) {
                productList.add(productService.findByProductid(billProduct.getProductid()));
            }
            billMix.setProductList(productList);
            billMix.setDinnerTable(dinnerTableService.findByDinnertableid(bill.getDinnertableid()));
            billDTO.getBillList().add(billMix);

            return new ResponseEntity<>(billDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<BillDTO> deleteBill(String token, Integer billid) {
        try {

            List<BillProduct> billProductList = billProductService.findAllByBillid(billid);
            billProductService.deleteAll(billProductList);
            delete(billRepository.findByBillid(billid));
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<BillDTO> orderProducts(String productID, String productAmount, String dinnertableid) {
        try {
            Bill billNew = new Bill();
            Date date = new Date();
            billNew.setDinnertableid(dinnertableid);
            //billNew.setStaffid(staffid);
            billNew.setPaymenttime(date.getTime());
            billNew.setConfirmed(0);
            billNew = save(billNew);

            /*int productIndex = 0;
            while (productMap.get("product" + productIndex + "id") != null && productMap.get("product" + productIndex + "amount") != null) {
                BillProduct billProductNew = new BillProduct();
                billProductNew.setBillid(billNew.getBillid());
                billProductNew.setProductid(productMap.get("product" + productIndex + "id"));
                billProductNew.setAmount(Integer.parseInt(productMap.get("product" + productIndex + "amount")));
                productIndex++;
                billProductService.save(billProductNew);

            }*/
            String[] productIDList = productID.split("-");
            String[] productAmountList = productAmount.split("-");
            for (int i = 0; i < productIDList.length; i++)
            {
                BillProduct billProductNew = new BillProduct();
                billProductNew.setBillid(billNew.getBillid());
                billProductNew.setProductid(productIDList[i]);
                billProductNew.setAmount(Integer.parseInt(productAmountList[i]));
                billProductService.save(billProductNew);
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    List<Bill> findAll() {
        return billRepository.findAll();
    }

    List<Bill> findAllByStaffid(String staffID) {
        return billRepository.findAllByStaffid(staffID);
    }

    List<Bill> findAllByDinnertableid(String staffID) {
        return billRepository.findAllByStaffid(staffID);
    }

    @Transactional
    public void saveAll(List<Bill> billList) {
        billRepository.saveAll(billList);
    }

    @Transactional
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Transactional
    public void deleteAll(List<Bill> billList) {
        billRepository.deleteAll(billList);
    }

    @Transactional
    public void delete(Bill bill) {
        billRepository.delete(bill);
    }
}
