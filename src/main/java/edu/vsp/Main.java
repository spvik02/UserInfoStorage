package edu.vsp;

import edu.vsp.configuration.CsvConfiguration;
import edu.vsp.repository.UserRepository;
import edu.vsp.repository.csv.UserCsvRepository;
import edu.vsp.service.ConsoleInputReader;
import edu.vsp.service.ConsoleService;
import edu.vsp.service.UserValidatorService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CsvConfiguration csvConfiguration = new CsvConfiguration();
        UserValidatorService userValidatorService = new UserValidatorService();
        ConsoleInputReader consoleInputReader = new ConsoleInputReader(scanner);
        UserRepository userRepository = new UserCsvRepository(csvConfiguration);
        ConsoleService consoleService = new ConsoleService(consoleInputReader, userRepository, userValidatorService);

        consoleService.giveUserMainChoice();
    }
}
