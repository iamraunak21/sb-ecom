package com.example.sb_ecom.ecom.exception;

public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String fieldName;
    String field;
    Long fieldId;

    public ResourceNotFoundException(String resourceName, String fieldName, String field) {
        super(String.format("Resource %s not found with field %s", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

    public ResourceNotFoundException( String resourceName, String field, Long fieldId) {
        super(String.format("Resource %s not found with id %d", resourceName, fieldId));
        this.fieldId = fieldId;
        this.resourceName = resourceName;
        this.field = field;
    }


}
