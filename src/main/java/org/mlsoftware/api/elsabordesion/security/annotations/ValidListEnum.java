package org.mlsoftware.api.elsabordesion.security.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mlsoftware.api.elsabordesion.security.validators.EnumListValidator;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumListValidator.class)
public @interface ValidListEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Valor no válido para el enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
