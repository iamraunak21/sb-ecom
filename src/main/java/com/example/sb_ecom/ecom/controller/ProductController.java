package com.example.sb_ecom.ecom.controller;

import com.example.sb_ecom.ecom.Config.AppConstant;
import com.example.sb_ecom.ecom.payload.ProductDTO;
import com.example.sb_ecom.ecom.payload.ProductResponse;
import com.example.sb_ecom.ecom.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/admin/categories/{CategoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long CategoryId){
        ProductDTO savedproductDTO = productService.addProduct(CategoryId, productDTO);
        return new ResponseEntity<>(savedproductDTO,HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "PageNumber" , required = false , defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber ,
            @RequestParam(name = "PageSize" , required = false , defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "SortBy" , required = false , defaultValue = AppConstant.SORT_BY_PRODUCT_SPECIAL_PRICE) String sortBy,
            @RequestParam(name = "SortOrder" , required = false , defaultValue = AppConstant.SORT_ORDER) String sortOrder

    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
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

    @PutMapping("/admin/products/{ProductId}/")
    public ResponseEntity<ProductDTO> getAllProductsByKeyword(@Valid @PathVariable long ProductId, @RequestBody ProductDTO productDTO){
        ProductDTO updatedProductDTO =  productService.updateProductByProductId(ProductId,productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{ProductId}/")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable long ProductId){
        ProductDTO productDTO =  productService.deleteProductByProductId(ProductId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{ProductId}/image")
    public ResponseEntity<ProductDTO> updateProductImageByProductId(@PathVariable long ProductId, @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO updatedProductDTO =  productService.updateProductImageByProductId(ProductId,image);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }
}
