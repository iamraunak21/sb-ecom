package com.example.sb_ecom.ecom.service;

import com.example.sb_ecom.ecom.exception.ResourceNotFoundException;
import com.example.sb_ecom.ecom.model.Category;
import com.example.sb_ecom.ecom.model.Product;
import com.example.sb_ecom.ecom.payload.CategoryResponse;
import com.example.sb_ecom.ecom.payload.ProductDTO;
import com.example.sb_ecom.ecom.payload.ProductResponse;
import com.example.sb_ecom.ecom.repository.CategoryRepository;
import com.example.sb_ecom.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));

        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getDiscount()*0.01*product.getPrice());
        product.setSpecialPrice(specialPrice);
        product.setImage("default.png");
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        ProductResponse productResponse = new ProductResponse();
        List<Product> allProducts = productRepository.findAll();
        List<ProductDTO> productListDTO = allProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        productResponse.setContent(productListDTO);
        return productResponse;
    }

    @Override
    public ProductResponse getAllProductByCategory(Long categoryId) {
        ProductResponse productResponse = new ProductResponse();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        List<Product> productsByCategory = productRepository.findProductByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productListDTO = productsByCategory.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        productResponse.setContent(productListDTO);
        return productResponse;
    }

    @Override
    public ProductResponse getAllProductByKeyword(String keyword) {
        ProductResponse productResponse = new ProductResponse();

        List<Product> allProductsWithKeyword = productRepository.findByProductNameContainingIgnoreCase(keyword);
      //  List<Product> allProductsWithKeyword = productRepository.findByProductNameLikeIgnoreCase('%' + keyword+ '%');

        List<ProductDTO> productListDTO = allProductsWithKeyword.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        productResponse.setContent(productListDTO);
        return productResponse;
    }

    @Override
    public ProductDTO updateProductByProductId(long productId, Product productGivenAsInput) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productFromDB.setProductName(productGivenAsInput.getProductName());
        productFromDB.setCategory(productGivenAsInput.getCategory());
        productFromDB.setPrice(productGivenAsInput.getPrice());
        productFromDB.setDiscount(productGivenAsInput.getDiscount());
        double specialPrice = productGivenAsInput.getPrice() -
                (0.01*productGivenAsInput.getPrice()*productGivenAsInput.getDiscount());
        productFromDB.setSpecialPrice(specialPrice);
        productRepository.save(productFromDB);
        return modelMapper.map(productFromDB, ProductDTO.class);
    }


}
