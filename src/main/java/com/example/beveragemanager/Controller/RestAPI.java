package com.example.beveragemanager.Controller;

import com.example.beveragemanager.SpringSecurity.CustomUserDetails;
import com.example.beveragemanager.SpringSecurity.JwtTokenProvider;
import com.example.beveragemanager.DTO.*;
import com.example.beveragemanager.Entiry.DinnerTable;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.Reponsitory.ProductRepository;
import com.example.beveragemanager.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;


    @GetMapping("/test")

    public String test() {
        return productRepository.findAll().toString();
    }
    /*@PostMapping("login")
    public ResponseEntity<User> login(@RequestParam(name = "username", required = false) String username,
                                      @RequestParam(name = "password", required = false) String password,
                                      @RequestParam(name = "token", required = false) String token)
    {
        UserDTO userDTO = userService.login(username, password, token);
        if (userDTO.getResult().equals("Token is valid") || userDTO.getResult().equals("Login success"))
        {
            return new ResponseEntity<>(userDTO.getUser(), HttpStatus.OK);
        }
        else if (!userDTO.getResult().equals("500"))
        {
            return new ResponseEntity<>(userDTO.getUser(), HttpStatus.UNAUTHORIZED);
        }
        else
        {
            return new ResponseEntity<>(userDTO.getUser(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "username", required = false) String username,
                                      @RequestParam(name = "password", required = false) String password)
    {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        System.out.println(username + password);
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new ResponseEntity<>(jwt,HttpStatus.OK);
    }
    @PostMapping("/login-test")
    public ResponseEntity<String> loginTest()
    {
        return new ResponseEntity<>("Test success", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/login-test-user")
    public ResponseEntity<String> loginTestUser()
    {
        return new ResponseEntity<>("Test success", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/login-test-admin")
    public ResponseEntity<String> loginTestAdmin()
    {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>("Test success", HttpStatus.OK);
    }
    @PostMapping("logout")
    public ResponseEntity<UserDTO> logout(@RequestParam(value = "token") String token)
    {
        return userService.logout(token);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bills")
    public ResponseEntity<BillDTO> getBills(@RequestParam(name = "token", required = false) String token,
                                            @RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage,
                                            @RequestParam(name = "type", required = false) Integer type) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billService.findAll(token, page, itemPerPage, type);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bills/{id}")
    public ResponseEntity<BillDTO> getBills(@RequestParam(name = "token", required = false) String token,
                                            @PathVariable(name = "id") Integer id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billService.findBillByBillid(token, id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/bills/orders")
    public ResponseEntity<BillDTO> orderProducts(@RequestParam(name = "token", required = false) String token,
                                                 @RequestParam(name = "dinnertableid") String dinnertableid,
                                                 @RequestParam(name = "staffid") String staffid,
                                                 @RequestParam(required = false) Map<String, String> productIDMap) {
        return billService.orderProducts(token, dinnertableid, staffid, productIDMap);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/bills")
    public ResponseEntity<BillDTO> deleteBills(@RequestParam(name = "token", required = false) String token,
                                            @RequestParam(name = "billid") Integer billid) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billService.deleteBill(token, billid);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/staffs")
    public ResponseEntity<StaffDTO> getStaffs(@RequestParam(name = "token", required = false) String token,
                                             @RequestParam(name = "page", required = false) Integer page,
                                             @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.findAll(token, page, itemPerPage);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/staffs/{id}")
    public ResponseEntity<StaffDTO> getStaffs(@RequestParam(name = "token", required = false) String token,
                                              @PathVariable(name = "id") String id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.findStaffByStaffid(token, id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/staffs")
    public ResponseEntity<StaffDTO> addStaffs(@RequestParam(name = "token", required = false) String token,
                                              Staff staff) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.addStaff(token, staff);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/staffs")
    public ResponseEntity<StaffDTO> updateStaffs(@RequestParam(name = "token", required = false) String token,
                                                 @RequestParam(name = "staffidold", required = false) String staffidold,
                                              Staff staff) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.updateStaff(token, staff, staffidold);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/staffs/{id}")
    public ResponseEntity<StaffDTO> deleteStaffs(@RequestParam(name = "token", required = false) String token,
                                                 @PathVariable(name = "id") String staffid) {
        Date date = new Date();
        System.out.println(date.getTime());
        return staffService.deleteStaff(token, staffid);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/products")
    public ResponseEntity<ProductDTO> getProducts(@RequestParam(name = "token", required = false) String token,
                                                  @RequestParam(name = "page", required = false) Integer page,
                                                  @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage,
                                                  @RequestParam(name = "sortBy", required = false) String sortBy) {
        Date date = new Date();
        System.out.println(date.getTime());
        return productService.findAll(page, itemPerPage, sortBy);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProducts(@RequestParam(name = "token", required = false) String token,
                                                  @PathVariable(name = "id") String id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return productService.findProductByProductid(token, id);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/products/search")
    public ResponseEntity<ProductDTO> searchProducts(@RequestParam(name = "page", required = false) Integer page,
                                                     @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage,
                                                     @RequestParam(name = "keyword", required = false) String keyWord,
                                                     @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                                     @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        Date date = new Date();
        System.out.println(date.getTime());
        return productService.searchProduct(page, itemPerPage, keyWord, minPrice, maxPrice);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products")
    public ResponseEntity<Product> addProducts(@RequestParam(name = "token", required = false) String token,
                                                  Product product) {

        System.out.println(product);
        return productService.addProduct( product);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/products")
    public ResponseEntity<Product> updateProducts(@RequestParam(name = "token", required = false) String token,
                                               @RequestParam(name = "productidold", required = false) String productidold,
                                               Product product) {

        System.out.println(product);
        return productService.updateProduct(token, product, productidold);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProducts(@RequestParam(name = "token", required = false) String token,
                                                  @PathVariable(name = "id", required = false) String productid) {

        return productService.delete(token, productid);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<UserDTOReturnClient> getUsers(@RequestParam(name = "token", required = false) String token,
                                                        @RequestParam(name = "page", required = false) Integer page,
                                                        @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return userService.findAll(token, page, itemPerPage);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> getDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "page", required = false) Integer page,
                                                          @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.findAll(token, page, itemPerPage);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/dinner-tables/{id}")
    public ResponseEntity<DinnerTableDTO> getDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @PathVariable(name = "id") String id) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.findDinnerTableByDinnertableid(token, id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> addDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          DinnerTable dinnerTable) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.addDinnerTable(token, dinnerTable);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/dinner-tables")
    public ResponseEntity<DinnerTableDTO> updateDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "dinnertableidold", required = false) String dinnerTableIDOLD,
                                                          DinnerTable dinnerTable) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.updateDinnerTable(token, dinnerTable, dinnerTableIDOLD);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/dinner-tables/{id}")
    public ResponseEntity<DinnerTableDTO> updateDinnerTables(@RequestParam(name = "token", required = false) String token,
                                                             @PathVariable(name = "id") String dinnertableid) {
        Date date = new Date();
        System.out.println(date.getTime());
        return dinnerTableService.deleteDinnerTable(token, dinnertableid);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bill-products")
    public ResponseEntity<BillProductDTO> getBillProducts(@RequestParam(name = "token", required = false) String token,
                                                          @RequestParam(name = "page", required = false) Integer page,
                                                          @RequestParam(name = "itemPerPage", required = false) Integer itemPerPage) {
        Date date = new Date();
        System.out.println(date.getTime());
        return billProductService.findAll(token, page, itemPerPage);
    }
}
