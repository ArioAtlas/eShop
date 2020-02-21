package ir.rightel.bss.eshop.validators;

import ir.rightel.bss.eshop.models.Order;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","name.required");
        Order order = (Order) target;
        if(order.getName().length()<3){
            errors.rejectValue("name","name.required");
        }

        if(order.getItems() == null || order.getItems().size()<1){
            errors.rejectValue("items","items.required");
        }
    }
}