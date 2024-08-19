package edu.vsp.service;

import edu.vsp.model.ValidationResult;
import edu.vsp.util.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;

public class UserValidatorService {
    private final String EMAIL_REGEX = "^[A-Za-z0-9]+@[a-z]+\\.[a-z]{2,}$";
    private final String ROLE_REGEX = "^[a-zA-Z0-9]+$";
    private final String PHONE_REGEX = "^375[0-9]{9}$";

    public ValidationResult validateEmail(String email) {
        boolean isFormatMatch = ValidatorUtil.matches(email, EMAIL_REGEX);
        List<String> messages = new ArrayList<>();

        if (!isFormatMatch) {
            messages.add("Wrong email format");
        }

        return new ValidationResult(isFormatMatch, messages);
    }

    public ValidationResult validateRoles(List<String> roles) {
        List<String> messages = new ArrayList<>();
        boolean isListSizeValid = ValidatorUtil.isListSizeValid(roles, 1, 3);
        boolean isFormatMatch = ValidatorUtil.listMatches(roles, ROLE_REGEX);

        if (!isListSizeValid) {
            messages.add("The number of entries is incorrect.");
        }
        if (!isFormatMatch) {
            messages.add("Wrong role format.");
        }

        return new ValidationResult(isListSizeValid && isFormatMatch, messages);
    }

    public ValidationResult validatePhoneNumbers(List<String> roles) {
        List<String> messages = new ArrayList<>();
        boolean isListSizeValid = ValidatorUtil.isListSizeValid(roles, 1, 3);
        boolean isFormatMatch = ValidatorUtil.listMatches(roles, PHONE_REGEX);

        if (!isListSizeValid) {
            messages.add("The number of entries is incorrect.");
        }
        if (!isFormatMatch) {
            messages.add("Wrong phone format.");
        }

        return new ValidationResult(isListSizeValid && isFormatMatch, messages);
    }
}
