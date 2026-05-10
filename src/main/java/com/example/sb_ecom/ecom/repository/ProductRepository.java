package com.example.sb_ecom.ecom.repository;

import com.example.sb_ecom.ecom.model.Category;
import com.example.sb_ecom.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByCategoryOrderByPriceAsc(Category category);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
