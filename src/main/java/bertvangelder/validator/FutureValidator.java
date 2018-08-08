package bertvangelder.validator;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FutureValidator implements ConstraintValidator<Future, Temporal> {

    @Override
    public void initialize(Future constraintAnnotation) {
    }

    @Override
    public boolean isValid(Temporal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate ld = LocalDate.from(value);
        if (ld.isAfter(LocalDate.now())) {
            return true;
        }
        return false;
    }

}