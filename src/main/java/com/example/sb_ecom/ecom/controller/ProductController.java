package com.example.sb_ecom.ecom.controller;

import com.example.sb_ecom.ecom.model.Product;
import com.example.sb_ecom.ecom.payload.ProductDTO;
import com.example.sb_ecom.ecom.payload.ProductResponse;
import com.example.sb_ecom.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/admin/categories/{CategoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long CategoryId){
        ProductDTO productDTO = productService.addProduct(CategoryId, product);
        return new ResponseEntity<>(productDTO,HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{CategoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsFromCategory(@PathVariable Long CategoryId){
    ProductResponse productResponse = productService.getAllProductByCategory(CategoryId);
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/")
    public ResponseEntity<ProductResponse> getAllProductsByKeyword(@RequestParam String keyword){
        ProductResponse productResponse = productService.getAllProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/public/products/{ProductId}/")
    public ResponseEntity<ProductDTO> getAllProductsByKeyword(@PathVariable long ProductId, @RequestBody Product product){
        ProductDTO productDTO =  productService.updateProductByProductId(ProductId,product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
