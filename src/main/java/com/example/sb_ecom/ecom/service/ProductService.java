package com.example.sb_ecom.ecom.service;

import com.example.sb_ecom.ecom.model.Product;
import com.example.sb_ecom.ecom.payload.ProductDTO;
import com.example.sb_ecom.ecom.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse getAllProductByCategory(Long CategoryId);

    ProductResponse getAllProductByKeyword(String keyword);

    ProductDTO updateProductByProductId(long productId, Product product);
}
