package com.dydzik;

import java.util.Scanner;

public class Application {

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        CommandsController commandsController = new CommandsController();
        System.out.println("Welcome to Bank Application!");

        while (true) {
            System.out.println("Enter command or type help for info: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            commandsController.processCommand(input);
        }

        scanner.close();
        System.out.println("Goodbye!");
    }
}
