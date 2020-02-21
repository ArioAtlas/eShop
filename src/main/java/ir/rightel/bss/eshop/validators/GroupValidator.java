package ir.rightel.bss.eshop.validators;

import ir.rightel.bss.eshop.models.ProductGroup;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GroupValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductGroup.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"groupName","name.required");
        ProductGroup group = (ProductGroup) target;

        if(group.getGroupName().length()<3){
            errors.rejectValue("groupName","name.required");
        }
    }
}
