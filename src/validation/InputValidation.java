package validation;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidation {
    public static int validateInteger(Scanner scanner, String text) {
        int userInput = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(text);
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("-?\\d+");
            if(pattern.matcher(input).matches()){
                userInput = Integer.parseInt(input);
                isValid = true;
            } else {
                System.out.println("Invalid input. Please input integer number only!");
            }
        }
        return userInput;
    }
    public static double validateDouble(Scanner scanner, String text) {
        double userInput = 0.0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(text);
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
            if(pattern.matcher(input).matches()){
                userInput = Double.parseDouble(input);
                isValid = true;
            } else {
                System.out.println("Invalid input. Please input decimal number only!");
            }
        }
        return userInput;
    }
    public static String validateString(Scanner scanner, String text) {
        String validString = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(text);
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("[a-zA-Z]+");
            if(pattern.matcher(input).matches()){
                validString = input;
                isValid = true;
            } else {
                System.out.println("Invalid input. Please input text only!");
            }
        }
        return validString;
    }
}
