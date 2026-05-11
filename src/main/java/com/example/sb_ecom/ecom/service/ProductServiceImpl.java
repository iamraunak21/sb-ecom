package com.example.sb_ecom.ecom.service;

import com.example.sb_ecom.ecom.exception.APIException;
import com.example.sb_ecom.ecom.exception.ResourceNotFoundException;
import com.example.sb_ecom.ecom.model.Category;
import com.example.sb_ecom.ecom.model.Product;
import com.example.sb_ecom.ecom.payload.ProductDTO;
import com.example.sb_ecom.ecom.payload.ProductResponse;
import com.example.sb_ecom.ecom.repository.CategoryRepository;
import com.example.sb_ecom.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));

        List<Product> allproductsOfgivenCategory = category.getProducts();

        boolean isProductExists = false;
        for(Product eachProduct : allproductsOfgivenCategory)
        {
            if(eachProduct.getProductName().equals(productDTO.getProductName()))
                isProductExists = true;
        }

        if(isProductExists)
            throw new APIException("Product Already Exists !!!");

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getDiscount()*0.01*product.getPrice());
        product.setSpecialPrice(specialPrice);
        product.setImage("default.png");
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        ProductResponse productResponse = new ProductResponse();

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable productPage = PageRequest.of(pageNumber, pageSize , sortByAndOrder);

        Page<Product> productPageList = productRepository.findAll(productPage);
        List<Product> allProducts = productPageList.getContent();
        List<ProductDTO> productListDTO = allProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        productResponse.setContent(productListDTO);
        productResponse.setPageNumber(productPageList.getNumber());
        productResponse.setPageSize(productPageList.getSize());
        productResponse.setTotalPages(productPageList.getTotalPages());
        productResponse.setTotalElements(productPageList.getTotalElements());
        productResponse.setLastPage(productPageList.isLast());
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
    public ProductDTO updateProductByProductId(long productId, ProductDTO productDTOGivenAsInput) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product productGivenAsInput = modelMapper.map(productDTOGivenAsInput, Product.class);
        productFromDB.setProductName(productGivenAsInput.getProductName());
        productFromDB.setQuantity(productGivenAsInput.getQuantity());
        productFromDB.setPrice(productGivenAsInput.getPrice());
        productFromDB.setDiscount(productGivenAsInput.getDiscount());
        double specialPrice = productGivenAsInput.getPrice() -
                (0.01*productGivenAsInput.getPrice()*productGivenAsInput.getDiscount());
        productFromDB.setSpecialPrice(specialPrice);
        productRepository.save(productFromDB);
        return modelMapper.map(productFromDB, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductByProductId(long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(productFromDB);
        return modelMapper.map(productFromDB, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImageByProductId(long productId, MultipartFile image) throws IOException {
        //Get the product from DB

        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        //upload image to server

        // get the file name of uploaded image

       // String path = "images/";
        String fileName = fileService.uploadImage(path, image);

        //updating the unique file name with extension to the product

        productFromDB.setImage(fileName);

        // saving the product

        Product updatedProduct = productRepository.save(productFromDB);

        //return DTO after mapping productto DTO

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }




}
