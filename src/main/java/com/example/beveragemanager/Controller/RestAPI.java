package com.example.beveragemanager.Controller;

import com.example.beveragemanager.DTO.*;
import com.example.beveragemanager.Entiry.DinnerTable;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

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
    @GetMapping("/bills/{id}")
    public ResponseEntity<BillDTO> getBills(@RequestParam(name = "token", required = false) String token,
                                            @PathVariable(name = "id") Integer id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billService.findBillByBillid(token, id);
    }
    @PostMapping("/bills/orders")
    public ResponseEntity<BillDTO> orderProducts(@RequestParam(name = "token", required = false) String token,
                                                 @RequestParam(name = "dinnertableid") String dinnertableid,
                                                 @RequestParam(name = "staffid") String staffid,
                                                 @RequestParam(name = "paymenttime") String paymenttime,
                                                 @RequestParam(required = false) Map<String, String> productIDMap) {

        return billService.orderProducts(token, dinnertableid, staffid, paymenttime, productIDMap);
    }
    @DeleteMapping("/bills")
    public ResponseEntity<BillDTO> deleteBills(@RequestParam(name = "token", required = false) String token,
                                            @RequestParam(name = "billid") Integer billid) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billService.deleteBill(token, billid);
    }

    @GetMapping("/staffs")
    public ResponseEntity<StaffDTO> getStaffs(@RequestParam(name = "token", required = false) String token,
                                             @RequestParam(name = "page", required = false) Integer page,
                                             @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.findAll(token, page, itemPerPage);
    }
    @GetMapping("/staffs/{id}")
    public ResponseEntity<StaffDTO> getStaffs(@RequestParam(name = "token", required = false) String token,
                                              @PathVariable(name = "id") String id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.findStaffByStaffid(token, id);
    }
    @PostMapping("/staffs")
    public ResponseEntity<StaffDTO> addStaffs(@RequestParam(name = "token", required = false) String token,
                                              Staff staff) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.addStaff(token, staff);
    }
    @PutMapping("/staffs")
    public ResponseEntity<StaffDTO> updateStaffs(@RequestParam(name = "token", required = false) String token,
                                                 @RequestParam(name = "staffidold", required = false) String staffidold,
                                              Staff staff) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.updateStaff(token, staff, staffidold);
    }
    @DeleteMapping("/staffs/{id}")
    public ResponseEntity<StaffDTO> deleteStaffs(@RequestParam(name = "token", required = false) String token,
                                                 @PathVariable(name = "id") String staffid) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.deleteStaff(token, staffid);
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
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProducts(@RequestParam(name = "token", required = false) String token,
                                                  @PathVariable(name = "id") String id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return productService.findProductByProductid(token, id);
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
    @PostMapping("/products")
    public ResponseEntity<Product> addProducts(@RequestParam(name = "token", required = false) String token,
                                                  Product product) {

        System.out.println(product);
        return productService.addProduct(token, product);
    }
    @PutMapping("/products")
    public ResponseEntity<Product> updateProducts(@RequestParam(name = "token", required = false) String token,
                                               @RequestParam(name = "productidold", required = false) String productidold,
                                               Product product) {

        System.out.println(product);
        return productService.updateProduct(token, product, productidold);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProducts(@RequestParam(name = "token", required = false) String token,
                                                  @PathVariable(name = "id", required = false) String productid) {

        return productService.delete(token, productid);
    }

    @GetMapping("/users")
    public ResponseEntity<UserDTOReturnClient> getUsers(@RequestParam(name = "token", required = false) String token,
                                                        @RequestParam(name = "page", required = false) Integer page,
                                                        @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return userService.findAll(token, page, itemPerPage);
    }

    @GetMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> getDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "page", required = false) Integer page,
                                                          @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.findAll(token, page, itemPerPage);
    }
    @GetMapping("/dinner-tables/{id}")
    public ResponseEntity<DinnerTableDTO> getDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @PathVariable(name = "id") String id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.findDinnerTableByDinnertableid(token, id);
    }
    @PostMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> addDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          DinnerTable dinnerTable) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.addDinnerTable(token, dinnerTable);
    }
    @PutMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> updateDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "dinnertableidold", required = false) String dinnerTableIDOLD,
                                                          DinnerTable dinnerTable) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.updateDinnerTable(token, dinnerTable, dinnerTableIDOLD);
    }
    @DeleteMapping("/dinner-tables/{id}")
    public ResponseEntity<DinnerTableDTO> updateDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                             @PathVariable(name = "id") String dinnertableid) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.deleteDinnerTable(token, dinnertableid);
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
