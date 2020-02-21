package com.arioatlas.eshop.validators;

import com.arioatlas.eshop.models.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"name","name.required");
        Product product = (Product) target;

        if(product.getGroup() == null){
            errors.rejectValue("group","group.required");
        }

        if(product.getUserId()==null){
            errors.rejectValue("userId","user.required");
        }
    }
}
