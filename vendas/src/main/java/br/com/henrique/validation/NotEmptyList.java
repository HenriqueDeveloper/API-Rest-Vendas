package br.com.henrique.validation;

import br.com.henrique.validation.constraintvalidation.NotEmptyListValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyListValidation.class)
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
