package com.example.beveragemanager.Reponsitory;

import com.example.beveragemanager.Entiry.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByProductid(String productID);
    List<Product> findAllByProductnameContaining(String productName, Pageable pageable);
    List<Product> findAllByProductnameContainingAndProductpriceGreaterThanEqualAndProductpriceLessThanEqual(String productName, Integer minRange, Integer maxRange, Pageable pageable);
    List<Product> findAllByProductpriceGreaterThanEqualAndProductpriceLessThanEqual(Integer minRange, Integer maxRange, Pageable pageable);
}
