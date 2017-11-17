package com.believe.bike.core.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

  String message() default "{com.believe.bike.invalid.phoneNumber}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
