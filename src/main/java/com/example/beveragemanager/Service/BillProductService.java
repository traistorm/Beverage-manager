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
    public ResponseEntity<BillProductDTO> findAll(String token, Integer page, Integer itemPerPage)
    {
        try
        {
            UserDTO userDTO = userService.login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                BillProductDTO billProductDTO = new BillProductDTO();
                List<BillProduct> billProductList = billProductRepository.findAll();
                List<Bill> billList = new ArrayList<>();
                List<Product> productList = new ArrayList<>();
                if (page != null && itemPerPage != null)
                {
                    List<BillProduct> billProductListReturn = billProductRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("billproductid").ascending())).getContent();
                    billProductDTO.setMaxPage(billProductList.size());
                    billProductDTO.setBillProductList(billProductListReturn);
                    for (BillProduct billProduct : billProductListReturn)
                    {

                        Bill bill = billService.findByBillid(billProduct.getBillid());
                        if (!billList.contains(bill))
                        {
                            billList.add(bill);
                        }
                        Product product = productService.findByProductid(billProduct.getProductid());
                        if (!productList.contains(product))
                        {
                            productList.add(product);
                        }
                    }
                    billProductDTO.setBillList(billList);
                    billProductDTO.setProductList(productList);
                }
                else
                {
                    billProductDTO.setMaxPage(billProductList.size());
                    billProductDTO.setBillList(billService.findAll());
                    billProductDTO.setProductList(productService.findAll());
                    billProductDTO.setBillProductList(billProductRepository.findAll());
                }


                return new ResponseEntity<>(billProductDTO, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
    public List<BillProduct> findAllByBillid(Integer billID)
    {
        return billProductRepository.findAllByBillid(billID);
    }
}
