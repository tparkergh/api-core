package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import java.text.MessageFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LogicalIdValidator implements AbstractBeanValidator,
    ConstraintValidator<ValidLogicalId, String> {

  private String category;
  private boolean required;
  private String ignoredValue;
  private boolean ignoreable;

  @Override
  public void initialize(ValidLogicalId constraintAnnotation) {
    this.category = constraintAnnotation.category();
    this.required = constraintAnnotation.required();
    this.ignoredValue = constraintAnnotation.ignoredValue();
    this.ignoreable = constraintAnnotation.ignoreable();
  }

  @Override
  public boolean isValid(final String value, ConstraintValidatorContext context) {
    boolean valid = false;
    final boolean hasProp = value != null && !value.isEmpty();

    if (required && !hasProp) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
          MessageFormat.format("{0} sys code is required", category))
          .addPropertyNode(category).addConstraintViolation();
    }
    else if (isIgnorable(value)) {
      valid = true;
    }
    else if (hasProp) {
        valid =
            SystemCodeCache.global().verifyActiveLogicalIdForMeta(value, category);
    }

    return valid;
  }

  private boolean isIgnorable(String value) {
    return !required && ignoreable && hasIgnorableValue(value);
  }

  private boolean hasIgnorableValue(String value) {
    return value == null || (value.trim().equals(ignoredValue.trim()));
  }
}
