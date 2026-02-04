

package com.ucas.addressbook;

public class AddressBookSystem {
    private static ContactManager contactManager = new ContactManager();
    private static MenuInterface menu = new MenuInterface();

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("        WELCOME TO ADDRESS BOOK SYSTEM");
        System.out.println("        University College of Applied Sciences");
        System.out.println("=".repeat(60));

        boolean running = true;
        while (running) {
            menu.displayMainMenu();
            int choice = menu.getMenuChoice();

            switch (choice) {
                case 1 -> addNewContact();
                case 2 -> searchByName();
                case 3 -> searchByNumber();
                case 4 -> deleteByName();
                case 5 -> deleteByNumber();
                case 6 -> displayAllContacts();
                case 7 -> { running = false; System.out.println("\nThank you for using Address Book System!"); System.out.println("Goodbye! 👋"); }
                default -> System.out.println("Invalid choice! Please enter 1-7.");
            }

            if (running && choice != 7) { menu.pressEnterToContinue(); }
        }
    }

    private static void addNewContact() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("        ADD NEW CONTACT");
        System.out.println("─".repeat(40));

        String name = menu.getInput("Enter contact name: ");
        String type = menu.getContactType();
        String phone = menu.getPhoneNumber();
        boolean addMoreNumbers = menu.askYesNo("Do you want to add another number for this contact? (yes/no): ");

        if (contactManager.addContact(name, type, phone, addMoreNumbers)) { System.out.println("✅ Contact added successfully!"); }
        else { System.out.println(" Failed to add contact. Please check your input."); }
    }

    private static void searchByName() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("        SEARCH BY NAME");
        System.out.println("─".repeat(40));

        String name = menu.getInput("Enter name to search: ");
        boolean useFuzzy = menu.askYesNo("Use similar name search? (yes/no): ");
        var results = contactManager.searchByName(name, useFuzzy);
        menu.displaySearchResults(results, "name", name);
    }

    private static void searchByNumber() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("        SEARCH BY NUMBER");
        System.out.println("─".repeat(40));

        String number = menu.getPhoneNumber();
        var results = contactManager.searchByNumber(number);
        menu.displaySearchResults(results, "number", number);
    }

    private static void deleteByName() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("        DELETE BY NAME");
        System.out.println("─".repeat(40));

        String name = menu.getInput("Enter exact name to delete: ");
        int deleted = contactManager.deleteByName(name);

        if (deleted > 0) { System.out.println(" Successfully deleted " + deleted + " contact(s)."); }
        else { System.out.println("No contacts found with name: " + name); }
    }

    private static void deleteByNumber() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("        DELETE BY NUMBER");
        System.out.println("─".repeat(40));

        String number = menu.getPhoneNumber();
        boolean deleted = contactManager.deleteByNumber(number);

        if (deleted) { System.out.println("Contact deleted successfully!"); }
        else { System.out.println(" No contact found with number: " + number); }
    }

    private static void displayAllContacts() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("        ALL CONTACTS");
        System.out.println("─".repeat(40));

        var allContacts = contactManager.getAllContacts();
        if (allContacts.isEmpty()) { System.out.println("No contacts stored yet."); }
        else { System.out.println("Total contacts: " + allContacts.size()); menu.displayAllContacts(allContacts); }
    }
}
