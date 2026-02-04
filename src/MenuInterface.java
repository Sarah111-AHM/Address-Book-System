package com.ucas.addressbook;

import java.util.List;
import java.util.Scanner;

public class MenuInterface {
    private Scanner scanner;
    private ValidationUtils validator;
    
    public MenuInterface() {
        this.scanner = new Scanner(System.in);
        this.validator = new ValidationUtils();
    }
    
    public void displayMainMenu() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("           ADDRESS BOOK SYSTEM - MAIN MENU");
        System.out.println("═".repeat(50));
        System.out.println("1. Add new contact");
        System.out.println("2. Search by name");
        System.out.println("3. Search by number");
        System.out.println("4. Delete contact by name");
        System.out.println("5. Delete contact by number");
        System.out.println("6. Show all contacts");
        System.out.println("7. Exit");
        System.out.println("═".repeat(50));
        System.out.print("Enter your choice (1-7): ");
    }
    
    public int getMenuChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.print("  Invalid choice. Please enter a number between 1-7: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number between 1-7: ");
            }
        }
    }
    
    public String getInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Input cannot be empty. " + prompt);
            input = scanner.nextLine().trim();
        }
        return input;
    }
    
    public String getContactType() {
        while (true) {
            System.out.print("Enter contact type (Family/Personal/Work/Other): ");
            String type = scanner.nextLine().trim();
            
            if (validator.isValidType(type)) {
                // Standardize the case
                String[] allowedTypes = {"Family", "Personal", "Work", "Other"};
                for (String allowed : allowedTypes) {
                    if (allowed.equalsIgnoreCase(type)) {
                        return allowed;
                    }
                }
            }
            
            System.out.println("  Invalid type. Using 'Other' as default.");
            return "Other";
        }
    }
    
    public String getPhoneNumber() {
        while (true) {
            System.out.print("Enter phone number: ");
            String number = scanner.nextLine().trim();
            
            if (validator.isValidPhoneNumber(number)) {
                return number;
            } else {
                System.out.println("Invalid phone number format. Please enter digits only.");
            }
        }
    }
    
    public boolean askYesNo(String question) {
        System.out.print(question);
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y") || response.equals("نعم");
    }
    
    public void displaySearchResults(List<Contact> results, String searchType, String searchTerm) {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("SEARCH RESULTS for " + searchType.toUpperCase() + ": '" + searchTerm + "'");
        System.out.println("─".repeat(40));
        
        if (results.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Found " + results.size() + " contact(s):\n");
            for (int i =
