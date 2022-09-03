package com.example.beveragemanager.Controller;

import com.example.beveragemanager.DTO.*;
import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class RestAPI {
    @Autowired
    BillService billService;
    @Autowired
    StaffService staffService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    DinnerTableService dinnerTableService;
    @Autowired
    BillProductService billProductService;

    @GetMapping("/test")

    public String test() {
        return "Test success";
    }
    @GetMapping("/bills")
    public ResponseEntity<BillDTO> getBills(@RequestParam(name = "token", required = false) String token,
                                            @RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billService.findAll(token, page, itemPerPage);
    }

    @GetMapping("/staffs")
    public ResponseEntity<StaffDTO> getStaffs(@RequestParam(name = "token", required = false) String token,
                                             @RequestParam(name = "page", required = false) Integer page,
                                             @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.findAll(token, page, itemPerPage);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductDTO> getProducts(@RequestParam(name = "token", required = false) String token,
                                                  @RequestParam(name = "page", required = false) Integer page,
                                                  @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage,
                                                  @RequestParam(name = "sortBy", required = false) String sortBy) {
        Date date = new Date();
        System.out.println(date.getTime());
        return productService.findAll(token, page, itemPerPage, sortBy);
    }

    @GetMapping("/products/search")
    public ResponseEntity<ProductDTO> searchProducts(@RequestParam(name = "token", required = false) String token,
                                                     @RequestParam(name = "page", required = false) Integer page,
                                                     @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage,
                                                     @RequestParam(name = "keyword", required = false) String keyWord,
                                                     @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                                     @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        Date date = new Date();
        System.out.println(date.getTime());
        return productService.searchProduct(token, page, itemPerPage, keyWord, minPrice, maxPrice);
    }

    @GetMapping("/users")
    public ResponseEntity<UserDTOReturnClinet> getUsers(@RequestParam(name = "token", required = false) String token,
                                            @RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return userService.findAll(token, page, itemPerPage);
    }

    @GetMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> getDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "page", required = false) Integer page,
                                                          @RequestParam(name = "v", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.findAll(token, page, itemPerPage);
    }

    @GetMapping("/bill-products")
    public ResponseEntity<BillProductDTO> getBillProducts(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "page", required = false) Integer page,
                                                          @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billProductService.findAll(token, page, itemPerPage);
    }
}
