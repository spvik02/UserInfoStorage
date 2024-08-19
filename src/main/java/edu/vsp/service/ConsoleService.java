package edu.vsp.service;

import edu.vsp.model.DuplicateEmailException;
import edu.vsp.model.User;
import edu.vsp.model.ValidationResult;
import edu.vsp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ConsoleService {
    private final ConsoleInputReader consoleInputReader;
    private final UserRepository userRepository;
    private final UserValidatorService userValidatorService;
    private final String USER_INPUT_DELIMITER = " ";
    private final String MAIN_CHOICE_STRING = """
            Options:
            1 - create user
            2 - edit user
            3 - delete user
            4 - get user info
            5 - exit
            Enter the number and press Enter:""";
    private static final String CHOICE_EDIT_STRING = """
            Options:
            1 - edit name
            2 - edit surname
            3 - edit email
            4 - edit roles
            5 - edit phone numbers
            6 - save changes
            Enter the number and press Enter:""";
    private final String ENTER_NAME_STRING = "Enter the user name: ";
    private final String ENTER_SURNAME_STRING = "Enter the user surname: ";
    private final String ENTER_EMAIL_STRING = "Enter the user email (of format username@mail_server.domain e.g. any@email.com): ";
    private final String ENTER_ROLES_STRING = "Enter 1-3 user roles separated by space ([a-zA-Z0-9] charset is allowed): ";
    private final String ENTER_PHONES_STRING = "Enter 1-3 user phone numbers (of format 375*********) separated by space: ";

    public ConsoleService(ConsoleInputReader consoleInputReader, UserRepository userRepository, UserValidatorService userValidatorService) {
        this.consoleInputReader = consoleInputReader;
        this.userRepository = userRepository;
        this.userValidatorService = userValidatorService;
    }

    public void giveUserMainChoice() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(MAIN_CHOICE_STRING);
            int choice = consoleInputReader.getIntFromUserInput();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> editUser();
                case 3 -> deleteUser();
                case 4 -> getUserInfo();
                case 5 -> isRunning = false;
                default -> System.out.println("There in no such an option. Please, try again.");
            }
        }
    }

    private void getUserInfo() {
        String emailForDetails = askAndGetStringFromUserInput(
                "Enter email of the user you want to see details about and press Enter");
        System.out.println(userRepository.findByEmail(emailForDetails));
    }

    private void deleteUser() {
        String emailForDeleting = askAndGetStringFromUserInput(
                "Enter email of the user you want to delete and press Enter");
        userRepository.deleteByEmail(emailForDeleting);
    }

    private void editUser() {
        String email = askAndGetStringFromUserInput(
                "Enter email of the user you want to edit and press Enter:");
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User userForUpdate = userOptional.get();

            boolean isInEditingProcess = true;
            while (isInEditingProcess) {
                System.out.println(userForUpdate);
                System.out.println(CHOICE_EDIT_STRING);
                int choiceEdit = consoleInputReader.getIntFromUserInput();
                switch (choiceEdit) {
                    case 1 -> userForUpdate.setName(askAndGetStringFromUserInput(ENTER_NAME_STRING));
                    case 2 -> userForUpdate.setSurname(askAndGetStringFromUserInput(ENTER_SURNAME_STRING));
                    case 3 -> userForUpdate.setEmail(getValidEmailFromUserInput());
                    case 4 -> userForUpdate.setRoles(getValidRolesFromUserInput());
                    case 5 -> userForUpdate.setPhoneNumbers(getValidPhoneNumbersFromUserInput());
                    case 6 -> {
                        isInEditingProcess = false;
                        try {
                            userRepository.update(email, userForUpdate);
                        } catch (DuplicateEmailException e) {
                            System.out.println("User is not updated. " + e.getMessage());
                        }
                    }
                    default -> System.out.println("There is no such option. Please, try again.");
                }
            }
        } else {
            System.out.println("There is no user with such an email.");
        }
    }

    private void createUser() {
        User user = createUserFromConsoleInput();
        try {
            userRepository.save(user);
            System.out.println("User is saved!");
        } catch (DuplicateEmailException e) {
            System.out.println("User is not saved. " + e.getMessage());
        }
    }

    /**
     * Writes a message to console and read user console input
     * @param msg message to print
     * @return string from user input
     */
    private String askAndGetStringFromUserInput(String msg) {
        System.out.println(msg);
        return consoleInputReader.getStringFromUserInput();
    }

    /**
     * Writes a message to console, read user console input and split it to list using the specified delimiter.
     * @param msg message to print
     * @return list
     */
    private List<String> askAndGetStringListFromUserInput(String msg) {
        List<String> list;
        System.out.println(msg);
        list = consoleInputReader.getStringListFromUserInput(USER_INPUT_DELIMITER);
        return list;
    }

    /**
     * Asks to enter an email address and checks the format of the entered data.
     * If the format is incorrect, then repeats the request.
     *
     * @return validated email
     */
    private String getValidEmailFromUserInput() {
        String email;
        while (true) {
            email = askAndGetStringFromUserInput(ENTER_EMAIL_STRING);
            ValidationResult validationResult = userValidatorService.validateEmail(email);
            if (validationResult.isValid()) {
                break;
            } else {
                System.out.println(validationResult.getMessages());
                System.out.println("Please, try again.");
            }
        }
        return email;
    }

    /**
     * Asks to enter roles and checks the format of the entered data.
     * If entered data is invalid, then repeats the request.
     * @return list of validated roles
     */
    private List<String> getValidRolesFromUserInput() {
        List<String> roles;
        while (true) {
            roles = askAndGetStringListFromUserInput(ENTER_ROLES_STRING);
            ValidationResult validationResult = userValidatorService.validateRoles(roles);

            if (validationResult.isValid()) {
                break;
            } else {
                System.out.println(validationResult.getMessages());
                System.out.println("Please, try again.");
            }
        }
        return roles;
    }

    /**
     * Asks to enter phone numbers and checks the format of the entered data.
     * If entered data is invalid, then repeats the request.
     * @return list of validated phone numbers
     */
    private List<String> getValidPhoneNumbersFromUserInput() {
        List<String> phones;
        while (true) {
            phones = askAndGetStringListFromUserInput(ENTER_PHONES_STRING);
            ValidationResult validationResult = userValidatorService.validatePhoneNumbers(phones);

            if (validationResult.isValid()) {
                break;
            } else {
                System.out.println(validationResult.getMessages());
                System.out.println("Please, try again.");
            }
        }
        return phones;
    }

    /**
     * Asks to enter information about the user and checks validation
     * @return User
     */
    private User createUserFromConsoleInput() {
        String name, surname, email;
        List<String> roles, phones;
        name = askAndGetStringFromUserInput(ENTER_NAME_STRING);
        surname = askAndGetStringFromUserInput(ENTER_SURNAME_STRING);
        email = getValidEmailFromUserInput();
        roles = getValidRolesFromUserInput();
        phones = getValidPhoneNumbersFromUserInput();

        User user = new User(name, surname, email, roles, phones);
        return user;
    }
}
