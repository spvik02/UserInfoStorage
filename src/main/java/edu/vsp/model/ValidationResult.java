package edu.vsp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ValidationResult {
    private boolean isValid;
    private List<String> messages;
}
