package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.ProductDTO;
import com.example.beveragemanager.DTO.StaffDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.BillProduct;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import com.example.beveragemanager.Reponsitory.ProductRepository;
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
public class ProductService {
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    @Lazy
    BillProductService billProductService;
    @Autowired
    @Lazy
    BillService billService;
    @Autowired
    @Lazy
    ProductService productService;

    public ResponseEntity<ProductDTO> findAll(Integer page, Integer itemPerPage, String sortBy) {
        try {

            ProductDTO productDTO = new ProductDTO();
            if (page != null && itemPerPage != null) {
                if (sortBy != null) {
                    if (sortBy.equals("priceAsc")) {
                        List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productprice").ascending())).getContent();
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    } else if (sortBy.equals("priceDes")) {
                        List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productprice").descending())).getContent();
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    } else if (sortBy.equals("discount")) {
                        List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("discount").ascending())).getContent();
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    } else {
                        List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending())).getContent();
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    }

                } else {
                    List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending())).getContent();
                    //productDTO.setMaxPage(productList.size());
                    HeaderReturnMix info = new HeaderReturnMix();
                    info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                    info.setCurrentPage(page);
                    info.setItemPerPage(itemPerPage);
                    productDTO.setInfo(info);
                    productDTO.setProductList(productListReturn);
                }

            } else {
                //productDTO.setMaxPage(productList.size());
                List<Product> productList = productRepository.findAll();
                HeaderReturnMix info = new HeaderReturnMix();
                info.setMaxPage(null);
                info.setCurrentPage(null);
                info.setItemPerPage(null);
                productDTO.setInfo(info);
                productDTO.setProductList(productList);
            }
            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    Product findByProductid(String productID) {
        return productRepository.findByProductid(productID);
    }

    List<Product> findAll() {
        return productRepository.findAll();
    }

    public ResponseEntity<ProductDTO> searchProduct(Integer page, Integer itemPerPage, String keyWord, Integer minPrice, Integer maxPrice) {
        try {
            ProductDTO productDTO = new ProductDTO();

            if (page != null && itemPerPage != null) {
                if (keyWord != null) {
                    if (minPrice != null && maxPrice != null) {
                        List<Product> productListReturn = productRepository.findAllByProductnameContainingIgnoreCaseAndProductpriceGreaterThanEqualAndProductpriceLessThanEqual(keyWord, minPrice, maxPrice, PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending()));
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAllByProductnameContainingIgnoreCaseAndProductpriceGreaterThanEqualAndProductpriceLessThanEqual(keyWord, minPrice, maxPrice, Pageable.unpaged()).size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    } else {
                        List<Product> productListReturn = productRepository.findAllByProductnameContainingIgnoreCase(keyWord, PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending()));
                        System.out.println(productListReturn.size());
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAllByProductnameContainingIgnoreCase(keyWord, Pageable.unpaged()).size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    }


                } else {
                    if (minPrice != null && maxPrice != null) {
                        List<Product> productListReturn = productRepository.findAllByProductpriceGreaterThanEqualAndProductpriceLessThanEqual(minPrice, maxPrice, PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending()));
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAllByProductpriceGreaterThanEqualAndProductpriceLessThanEqual(minPrice, maxPrice, Pageable.unpaged()).size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    } else {
                        List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending())).getContent();
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    }

                }

            } else {
                //productDTO.setMaxPage(productList.size());
                List<Product> productList = productRepository.findAll();
                HeaderReturnMix info = new HeaderReturnMix();
                info.setMaxPage(null);
                info.setCurrentPage(null);
                info.setItemPerPage(null);
                productDTO.setInfo(info);
                productDTO.setProductList(productList);
            }
            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Product> addProduct(Product product) {
        try {

            Product productFound = productRepository.findByProductid(product.getProductid());
            if (productFound == null) {
                System.out.println("Check");
                save(product);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> updateProduct(String token, Product product, String productidold) {
        try {

            if (productidold.equals(product.getProductid())) {
                productRepository.save(product);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                if (productRepository.findByProductid(product.getProductid()) == null) {
                    Product productFound = productRepository.findByProductid(productidold);
                    List<BillProduct> billProductList = billProductService.findAllByProductid(productidold);
                    billProductService.deleteAll(billProductList);
                    delete(productFound);

                    //Set new id

                    save(product);
                    for (BillProduct billProduct : billProductList) {
                        billProduct.setProductid(product.getProductid());
                    }
                    System.out.println(billProductList);
                    billProductService.saveAll(billProductList);
                    return new ResponseEntity<>(null, HttpStatus.OK);
                } else // new id of product is existed
                {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);
    }

    public ResponseEntity<Product> delete(String token, String productID) {
        try {

            delete(productRepository.findByProductid(productID));
            billProductService.deleteAll(billProductService.findAllByProductid(productID));
            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<ProductDTO> findProductByProductid(String token, String productID) {
        try {

            System.out.println();
            ProductDTO productDTO = new ProductDTO();
            productDTO.getProductList().add(productService.findByProductid(productID));
            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }
}
