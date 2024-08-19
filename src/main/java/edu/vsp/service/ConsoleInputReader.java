package edu.vsp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleInputReader {

    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getIntFromUserInput() {
        int num = 0;
        boolean isValid = true;
        try {
            num = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input. Please, try again to enter the number.");
            isValid = false;
        }
        if (!isValid) {
            num = getIntFromUserInput();
        }
        return num;
    }

    public String getStringFromUserInput() {
        return scanner.nextLine();
    }

    public List<String> getStringListFromUserInput(String delimiter) {
        String line = scanner.nextLine();
        List<String> lines = Arrays.stream(line.split(delimiter)).toList();
        return lines;
    }
}
