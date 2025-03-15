package org.mlsoftware.api.elsabordesion.security.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

import org.mlsoftware.api.elsabordesion.security.annotations.ValidListEnum;

public class EnumListValidator implements ConstraintValidator<ValidListEnum, Set<? extends Enum<?>>> {
  private Enum<?>[] enumValues;

  @Override
  public void initialize(ValidListEnum annotation) {
    enumValues = annotation.enumClass().getEnumConstants();
  }

  @Override
  public boolean isValid(Set<? extends Enum<?>> values, ConstraintValidatorContext context) {
    if (values == null || values.isEmpty()) {
      return false; // Maneja sets nulos o vacíos
    }

    for (Enum<?> value : values) {
      if (value == null) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("El conjunto contiene un valor nulo").addConstraintViolation();
        return false;
      }

      if (!isValidEnum(value)) {
        return false; // Fallo si el valor no coincide con los enums válidos
      }
    }
    return true;
  }

  private boolean isValidEnum(Enum<?> value) {
    for (Enum<?> enumValue : enumValues) {
      if (enumValue.name().equals(value.name())) {
        return true;
      }
    }
    return false;
  }
}
