package com.believe.bike.core.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p> The phone number validator. </p>
 *
 * @author Li Xingping
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
  @Override
  public void initialize(PhoneNumber constraintAnnotation) {

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.isNotBlank(value) && Validations.validateCellNo(value);
  }
}
