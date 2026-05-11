package com.example.sb_ecom.ecom.service;

import com.example.sb_ecom.ecom.payload.ProductDTO;
import com.example.sb_ecom.ecom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getAllProductByCategory(Long CategoryId);

    ProductResponse getAllProductByKeyword(String keyword);

    ProductDTO updateProductByProductId(long productId, ProductDTO product);

    ProductDTO deleteProductByProductId(long productId);

    ProductDTO updateProductImageByProductId(long productId, MultipartFile image) throws IOException;
}
