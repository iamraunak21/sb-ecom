package com.example.sb_ecom.ecom.service;


import com.example.sb_ecom.ecom.exception.APIException;
import com.example.sb_ecom.ecom.exception.ResourceNotFoundException;
import com.example.sb_ecom.ecom.model.Category;
import com.example.sb_ecom.ecom.payload.CategoryDTO;
import com.example.sb_ecom.ecom.payload.CategoryResponse;
import com.example.sb_ecom.ecom.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
  //  private List<Category> categoryList = new ArrayList<>();

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

   // private long nextId = 1l;
    public CategoryResponse getCategoryList(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable categoryPage = PageRequest.of(pageNumber, pageSize , sortByAndOrder);
        Page<Category> categoryPageList = categoryRepository.findAll(categoryPage);



        List<Category> allCategory = categoryPageList.getContent();
        if(allCategory.isEmpty()){
            throw new APIException("No Category to display");
        }

        List<CategoryDTO> categoryDTOList = allCategory.stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryList(categoryDTOList);

        categoryResponse.setPageNumber(categoryPageList.getNumber());
        categoryResponse.setPageSize(categoryPageList.getSize());
        categoryResponse.setTotalPages(categoryPageList.getTotalPages());
        categoryResponse.setTotalElements(categoryPageList.getTotalElements());
        categoryResponse.setLastPage(categoryPageList.isLast());

        return categoryResponse;
    //    return categoryList;
    }


    public void addCategory(CategoryDTO categoryDTOObj){
      //  categoryObj.setCategoryId(nextId++);
        Category categoryObjMap = modelMapper.map(categoryDTOObj, Category.class);
        Category byCategoryName = categoryRepository.findByCategoryName(categoryObjMap.getCategoryName());

        if(byCategoryName != null){
            throw new APIException("Category with name " + byCategoryName.getCategoryName() + " already exists");
        }

        categoryRepository.save(categoryObjMap);
        // categoryList.add(categoryObj);
    }

    public ResponseEntity<String> updateCategory(Long categoryId, CategoryDTO categoryDTOObj){

        Category categoryObjMap = modelMapper.map(categoryDTOObj, Category.class);

        Optional<Category> categoryOptionalObj = categoryRepository.findById(categoryId);
        if(categoryOptionalObj.isPresent()){
            Category categoryObjectFromRepo = categoryOptionalObj.get();
            categoryObjMap.setCategoryId(categoryObjectFromRepo.getCategoryId());
             categoryRepository.save(categoryObjMap);
            return new ResponseEntity<>("Content Updated for Category " + categoryId , HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Content Not Found", HttpStatus.NOT_FOUND);



//        Optional<Category> categoryOptionalObj = categoryList.stream().
//                filter(c -> c.getCategoryId() == categoryId).
//                findFirst();

//        if(categoryOptionalObj.isPresent()){
//            Category categoryOb = categoryOptionalObj.get();
//            categoryOb.setCategoryName(categoryObj.getCategoryName());
//            return new ResponseEntity<>("Content Updated for Category " + categoryId , HttpStatus.CREATED);
//        }
//        else
//            return new ResponseEntity<>("Content Not Found", HttpStatus.NOT_FOUND);

    }


    public ResponseEntity<CategoryDTO> deleteCategory(Long categoryId) {


        Optional<Category> optionalCategoryObjFromRepo = categoryRepository.findById(categoryId);
        if(optionalCategoryObjFromRepo.isPresent()){
            categoryRepository.deleteById(categoryId);
            CategoryDTO CategoryDTOObj = modelMapper.map(optionalCategoryObjFromRepo, CategoryDTO.class);

            //return new ResponseEntity<>("Successfully deleted Category " + optionalCategoryObjFromRepo.get().getCategoryName() +"with  id " + optionalCategoryObjFromRepo.get().getCategoryId() , HttpStatus.OK);
            return new ResponseEntity<>(CategoryDTOObj, HttpStatus.OK);
        }
       // return new ResponseEntity<>("Content Not Found", HttpStatus.NOT_FOUND);
        throw new ResourceNotFoundException("Category", "CategoryId" , categoryId );


//        Category categoryObj = categoryList.stream().
//                filter(c -> c.getCategoryId() == categoryId).
//                findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content Not Found"));
//        // if(categoryObj == null)
//        //     return "Category not found with  id " + categoryObj.getCategoryId();
//        categoryList.remove(categoryObj);
//        return new ResponseEntity<>("Successfully deleted Category" + categoryObj.getCategoryName() +"with  id " + categoryObj.getCategoryId() , HttpStatus.OK);
    }

}
