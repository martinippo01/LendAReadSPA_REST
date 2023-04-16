package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailImpl implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email.matches(emailPattern)) {
            return true;
        } else {
            return false;
        }    }
}
