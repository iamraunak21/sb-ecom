package com.example.sb_ecom.ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    @NotBlank
    @Size(min= 3 , message = "Product Name atleast be 3 chars")
    private String productName;
    private String image;

    @NotBlank
    @Size(min= 6 , message = "Product Description atleast be 6 chars")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_Id")

    private Category category;


}
