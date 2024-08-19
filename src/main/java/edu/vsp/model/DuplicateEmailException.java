package edu.vsp.model;

public class DuplicateEmailException extends RuntimeException {
    /**
     * Constructs a {@code DuplicateEmailException} with message
     *
     * @param s the detail message.
     */
    public DuplicateEmailException(String s) {
        super(s);
    }
}
