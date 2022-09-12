package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.BillDTO;
import com.example.beveragemanager.DTO.BillProductDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.Reponsitory.BillProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillProductService {
    @Autowired
    BillProductRepository billProductRepository;
    @Autowired
    UserService userService;
    @Autowired
    @Lazy
    BillService billService;
    @Autowired
    @Lazy
    ProductService productService;

    public ResponseEntity<BillProductDTO> findAll(String token, Integer page, Integer itemPerPage) {
        try {

            BillProductDTO billProductDTO = new BillProductDTO();
            List<BillProduct> billProductList = billProductRepository.findAll();
            List<Bill> billList = new ArrayList<>();
            List<Product> productList = new ArrayList<>();
            if (page != null && itemPerPage != null) {
                List<BillProduct> billProductListReturn = billProductRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("billproductid").ascending())).getContent();
                billProductDTO.setMaxPage(billProductList.size());
                billProductDTO.setBillProductList(billProductListReturn);
                for (BillProduct billProduct : billProductListReturn) {

                    Bill bill = billService.findByBillid(billProduct.getBillid());
                    if (!billList.contains(bill)) {
                        billList.add(bill);
                    }
                    Product product = productService.findByProductid(billProduct.getProductid());
                    if (!productList.contains(product)) {
                        productList.add(product);
                    }
                }
                billProductDTO.setBillList(billList);
                billProductDTO.setProductList(productList);
            } else {
                billProductDTO.setMaxPage(billProductList.size());
                billProductDTO.setBillList(billService.findAll());
                billProductDTO.setProductList(productService.findAll());
                billProductDTO.setBillProductList(billProductRepository.findAll());
            }


            return new ResponseEntity<>(billProductDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<BillProduct> findAllByBillid(Integer billID) {
        return billProductRepository.findAllByBillid(billID);
    }

    public List<BillProduct> findAllByProductid(String productID) {
        return billProductRepository.findAllByProductid(productID);
    }

    @Transactional
    public void deleteAll(List<BillProduct> billProductList) {
        billProductRepository.deleteAll(billProductList);
    }

    @Transactional
    public void saveAll(List<BillProduct> billProductList) {
        billProductRepository.saveAll(billProductList);
    }

    @Transactional
    public void save(BillProduct billProduct) {
        billProductRepository.save(billProduct);
    }
}
