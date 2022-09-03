package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.ProductDTO;
import com.example.beveragemanager.DTO.StaffDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.Product;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.EntityMix.HeaderReturnMix;
import com.example.beveragemanager.Reponsitory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    public ResponseEntity<ProductDTO> findAll(String token, Integer page, Integer itemPerPage, String sortBy)
    {
        try
        {
            UserDTO userDTO = userService.login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                ProductDTO productDTO = new ProductDTO();

                if (page != null && itemPerPage != null)
                {
                    if (sortBy != null)
                    {
                        if (sortBy.equals("priceAsc"))
                        {
                            List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productprice").ascending())).getContent();
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                            info.setCurrentPage(page);
                            info.setItemPerPage(itemPerPage);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }
                        else if (sortBy.equals("priceDes"))
                        {
                            List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productprice").descending())).getContent();
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage(productListReturn.size());
                            info.setCurrentPage(page);
                            info.setItemPerPage(itemPerPage);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }
                        else if (sortBy.equals("discount"))
                        {
                            List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("discount").ascending())).getContent();
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage((int) ((productRepository.findAll(Pageable.unpaged()).getContent().size() / itemPerPage) + 1));
                            info.setCurrentPage(page);
                            info.setItemPerPage(itemPerPage);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }
                        else
                        {
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
                    else
                    {
                        List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending())).getContent();
                        //productDTO.setMaxPage(productList.size());
                        HeaderReturnMix info = new HeaderReturnMix();
                        info.setMaxPage((int) ((productRepository.findAll().size() / itemPerPage) + 1));
                        info.setCurrentPage(page);
                        info.setItemPerPage(itemPerPage);
                        productDTO.setInfo(info);
                        productDTO.setProductList(productListReturn);
                    }

                }
                else
                {
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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
    Product findByProductid(String productID)
    {
        return productRepository.findByProductid(productID);
    }
    List<Product> findAll()
    {
        return productRepository.findAll();
    }
    public ResponseEntity<ProductDTO> searchProduct(String token, Integer page, Integer itemPerPage, String keyWord, Integer minPrice, Integer maxPrice)
    {
        try
        {
            UserDTO userDTO = userService.login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                ProductDTO productDTO = new ProductDTO();

                if (page != null && itemPerPage != null)
                {
                    if (keyWord != null)
                    {
                        if (minPrice != null && maxPrice != null)
                        {
                            List<Product> productListReturn = productRepository.findAllByProductnameContainingAndProductpriceGreaterThanEqualAndProductpriceLessThanEqual(keyWord, minPrice, maxPrice, PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending()));
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage((int) ((productRepository.findAllByProductnameContainingAndProductpriceGreaterThanEqualAndProductpriceLessThanEqual(keyWord, minPrice, maxPrice, Pageable.unpaged()).size() / itemPerPage) + 1));
                            info.setCurrentPage(page);
                            info.setItemPerPage(itemPerPage);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }
                        else
                        {
                            List<Product> productListReturn = productRepository.findAllByProductnameContaining(keyWord, PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending()));
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage((int) ((productRepository.findAllByProductnameContaining(keyWord, Pageable.unpaged()).size() / itemPerPage) + 1));
                            info.setCurrentPage(page);
                            info.setItemPerPage(itemPerPage);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }


                    }
                    else
                    {
                        if (minPrice != null && maxPrice != null)
                        {
                            List<Product> productListReturn = productRepository.findAllByProductpriceGreaterThanEqualAndProductpriceLessThanEqual(minPrice, maxPrice, PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending()));
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage((int) ((productRepository.findAllByProductpriceGreaterThanEqualAndProductpriceLessThanEqual(minPrice, maxPrice, Pageable.unpaged()).size() / itemPerPage) + 1));
                            info.setCurrentPage(page);
                            info.setItemPerPage(itemPerPage);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }
                        else
                        {
                            List<Product> productListReturn = productRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("productid").ascending())).getContent();
                            //productDTO.setMaxPage(productList.size());
                            HeaderReturnMix info = new HeaderReturnMix();
                            info.setMaxPage((int) ((productRepository.findAll().size() / itemPerPage) + 1));
                            info.setCurrentPage(null);
                            info.setItemPerPage(null);
                            productDTO.setInfo(info);
                            productDTO.setProductList(productListReturn);
                        }

                    }

                }
                else
                {
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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
