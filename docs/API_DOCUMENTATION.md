```markdown
# 🔧 API Documentation - Address Book System

## 📋 Overview
This document describes the public API (Application Programming Interface) for the Address Book System. It details all classes, methods, and their usage for developers who want to extend or integrate with the system.

## 🏗️ Package Structure
```

com.ucas.addressbook
├── AddressBookSystem.java    # Main application entry point
├── Contact.java              # Data model class
├── ContactManager.java       # Business logic controller
├── MenuInterface.java        # User interface handler
├── SearchEngine.java         # Search algorithms
└── ValidationUtils.java      # Input validation utilities

```

## 📚 Class Reference

### 1. **AddressBookSystem Class**
**Description**: Main application class that coordinates all components.

```java
public class AddressBookSystem {
    // Main entry point
    public static void main(String[] args)
}
```

Usage Example:

```java
// To start the application
java AddressBookSystem
```

2. Contact Class

Description: Represents a contact entity with name, type, and phone numbers.

Constructor

```java
// Create contact with single number
Contact(String name, String type, String phoneNumber)

// Create contact with multiple numbers (Bonus feature)
Contact(String name, String type, List<String> phoneNumbers)
```

Public Methods

Method Parameters Return Type Description
getId() None int Get unique contact ID
getName() None String Get contact name
getType() None String Get contact type
getPhoneNumbers() None List<String> Get all phone numbers
addPhoneNumber(String) number boolean Add new phone number
removePhoneNumber(String) number boolean Remove phone number
hasPhoneNumber(String) number boolean Check if has specific number
containsName(String) searchTerm boolean Check if name contains term
isNameSimilar(String) searchName boolean Fuzzy name matching
toString() None String String representation
toFormattedString() None String Formatted output

Usage Example:

```java
// Create a contact
Contact person = new Contact("John Doe", "Personal", "0599123456");

// Add another number
person.addPhoneNumber("022345678");

// Check if name contains "John"
boolean hasJohn = person.containsName("John");

// Fuzzy name matching
boolean isSimilar = person.isNameSimilar("Jon Doe");
```

3. ContactManager Class

Description: Manages all contact operations (CRUD, search, delete).

Constructor

```java
ContactManager()  // Creates empty contact list
```

Public Methods

Add Operations

```java
/**
 * Add a new contact to the system
 * @param name Contact name
 * @param type Contact type (Family/Personal/Work/Other)
 * @param phoneNumber Primary phone number
 * @param allowMultipleNumbers If true, can add to existing contact
 * @return true if successful, false otherwise
 */
boolean addContact(String name, String type, String phoneNumber, boolean allowMultipleNumbers)
```

Search Operations

```java
/**
 * Search contacts by name
 * @param name Name or partial name to search
 * @param useFuzzy If true, uses fuzzy matching algorithm
 * @return List of matching contacts
 */
List<Contact> searchByName(String name, boolean useFuzzy)

/**
 * Search contacts by phone number (exact match)
 * @param number Phone number to search
 * @return List of contacts with matching number
 */
List<Contact> searchByNumber(String number)
```

Delete Operations

```java
/**
 * Delete contacts by exact name match
 * @param name Exact name to delete
 * @return Number of contacts deleted
 */
int deleteByName(String name)

/**
 * Delete contact by exact phone number match
 * @param number Phone number to delete
 * @return true if deleted, false if not found
 */
boolean deleteByNumber(String number)
```

Retrieval Operations

```java
/**
 * Get all contacts in the system
 * @return List of all contacts
 */
List<Contact> getAllContacts()

/**
 * Get total number of contacts
 * @return Contact count
 */
int getContactCount()
```

Utility Operations

```java
/**
 * Clear all contacts (for testing/reset)
 */
void clearAllContacts()
```

Usage Example:

```java
ContactManager manager = new ContactManager();

// Add contact
manager.addContact("Alice", "Friend", "123456789", false);

// Search
List<Contact> results = manager.searchByName("Ali", true);

// Delete
int deleted = manager.deleteByName("Bob");

// Get all
List<Contact> all = manager.getAllContacts();
```

4. MenuInterface Class

Description: Handles all user interaction through console.

Constructor

```java
MenuInterface()  // Initializes scanner and validator
```

Public Methods

Display Methods

```java
/**
 * Display the main menu
 */
void displayMainMenu()

/**
 * Display search results
 * @param results List of contacts to display
 * @param searchType Type of search performed
 * @param searchTerm Term used for search
 */
void displaySearchResults(List<Contact> results, String searchType, String searchTerm)

/**
 * Display all contacts in formatted table
 * @param contacts List of contacts to display
 */
void displayAllContacts(List<Contact> contacts)
```

Input Methods

```java
/**
 * Get menu choice from user
 * @return Valid menu choice (1-7)
 */
int getMenuChoice()

/**
 * Get text input from user
 * @param prompt Message to show user
 * @return User input string
 */
String getInput(String prompt)

/**
 * Get validated contact type
 * @return Valid contact type
 */
String getContactType()

/**
 * Get validated phone number
 * @return Valid phone number
 */
String getPhoneNumber()

/**
 * Ask yes/no question
 * @param question Question to ask
 * @return true for yes, false for no
 */
boolean askYesNo(String question)
```

Utility Methods

```java
/**
 * Wait for user to press Enter
 */
void pressEnterToContinue()

/**
 * Close scanner (call before exit)
 */
void closeScanner()
```

Usage Example:

```java
MenuInterface menu = new MenuInterface();

// Display menu
menu.displayMainMenu();

// Get user choice
int choice = menu.getMenuChoice();

// Get input
String name = menu.getInput("Enter name: ");

// Display results
menu.displaySearchResults(results, "name", "John");
```

5. SearchEngine Class

Description: Contains search algorithms for contacts.

Public Methods

```java
/**
 * Search by name using contains matching
 * @param contacts List to search
 * @param searchTerm Term to search for
 * @return Matching contacts
 */
List<Contact> searchByNameContains(List<Contact> contacts, String searchTerm)

/**
 * Search by name using fuzzy matching
 * @param contacts List to search
 * @param searchName Name to search for
 * @return Similar contacts
 */
List<Contact> searchByNameFuzzy(List<Contact> contacts, String searchName)

/**
 * Search by exact phone number
 * @param contacts List to search
 * @param number Phone number to find
 * @return Contacts with matching number
 */
List<Contact> searchByNumberExact(List<Contact> contacts, String number)

/**
 * Search by contact type
 * @param contacts List to search
 * @param type Type to filter by
 * @return Contacts of specified type
 */
List<Contact> searchByType(List<Contact> contacts, String type)
```

Usage Example:

```java
SearchEngine engine = new SearchEngine();

// Standard search
List<Contact> standardResults = engine.searchByNameContains(contacts, "Moh");

// Fuzzy search
List<Contact> fuzzyResults = engine.searchByNameFuzzy(contacts, "Mohamed");

// Number search
List<Contact> numberResults = engine.searchByNumberExact(contacts, "0599123456");
```

6. ValidationUtils Class

Description: Provides validation methods for all inputs.

Public Methods

```java
// Name validation
boolean isValidName(String name)

// Type validation
boolean isValidType(String type)

// Phone validation
boolean isValidPhoneNumber(String phone)
boolean isReservedNumber(String phone)

// Type standardization
String standardizeType(String type)

// Phone cleaning
String validateAndCleanPhone(String phone)

// Configuration
boolean isDuplicateNumberAllowed()
String[] getAllowedTypes()
String[] getReservedNumbers()
```

Usage Example:

```java
ValidationUtils validator = new ValidationUtils();

// Check name
if (validator.isValidName("John Doe")) {
    // Proceed
}

// Check type
if (validator.isValidType("Family")) {
    // Proceed
}

// Check and clean phone
String cleanPhone = validator.validateAndCleanPhone("0599-123-456");

// Get allowed types
String[] types = validator.getAllowedTypes();
```

🔄 Method Chaining Examples

Complete Contact Addition Flow

```java
ContactManager manager = new ContactManager();
MenuInterface menu = new MenuInterface();
ValidationUtils validator = new ValidationUtils();

// Get and validate inputs
String name = menu.getInput("Enter name: ");
if (!validator.isValidName(name)) return;

String type = menu.getContactType();
String phone = menu.getPhoneNumber();

// Add to system
boolean success = manager.addContact(name, type, phone, false);
if (success) {
    System.out.println("✅ Contact added!");
}
```

Complete Search Flow

```java
// User chooses search type
int choice = menu.getMenuChoice();
if (choice == 2) { // Search by name
    String name = menu.getInput("Enter name: ");
    boolean fuzzy = menu.askYesNo("Use fuzzy search?");
    
    List<Contact> results = manager.searchByName(name, fuzzy);
    menu.displaySearchResults(results, "name", name);
}
```

⚠️ Error Handling API

Validation Errors

All validation methods return boolean indicating success/failure. Error messages are printed to console.

Business Logic Errors

· addContact(): Returns false if validation fails or duplicate exists
· deleteByName(): Returns 0 if no contacts found
· deleteByNumber(): Returns false if number not found

User Input Errors

· getMenuChoice(): Loops until valid input (1-7)
· getInput(): Loops until non-empty input
· getContactType(): Defaults to "Other" if invalid

🔧 Extension Points

Adding New Search Methods

```java
// Example: Add search by partial number
public List<Contact> searchByPartialNumber(List<Contact> contacts, String partial) {
    List<Contact> results = new ArrayList<>();
    for (Contact contact : contacts) {
        for (String number : contact.getPhoneNumbers()) {
            if (number.contains(partial)) {
                results.add(contact);
                break;
            }
        }
    }
    return results;
}
```

Adding New Validation Rules

```java
// Example: Add email validation
public boolean isValidEmail(String email) {
    return email != null && 
           email.matches("^[A-Za-z0-9+_.-]+@(.+)$") &&
           email.length() <= 100;
}
```

🧪 Testing API

Unit Test Examples

```java
// Test contact creation
@Test
void testContactCreation() {
    Contact contact = new Contact("Test", "Personal", "123456");
    assertEquals("Test", contact.getName());
    assertEquals("Personal", contact.getType());
    assertTrue(contact.hasPhoneNumber("123456"));
}

// Test search functionality
@Test
void testSearchByName() {
    ContactManager manager = new ContactManager();
    manager.addContact("Alice", "Friend", "111", false);
    
    List<Contact> results = manager.searchByName("Ali", true);
    assertEquals(1, results.size());
}
```

📊 Performance API

Memory Usage Methods

```java
// Estimate memory usage
public int estimateMemoryUsage(List<Contact> contacts) {
    int total = 0;
    for (Contact contact : contacts) {
        total += contact.getName().length() * 2; // UTF-16
        total += contact.getType().length() * 2;
        for (String num : contact.getPhoneNumbers()) {
            total += num.length() * 2;
        }
    }
    return total; // bytes
}
```

🔐 Security API

Input Sanitization

All input methods automatically sanitize:

· Trim whitespace
· Remove invalid characters (phone numbers)
· Convert to appropriate case
· Check length limits

Data Validation

```java
// Comprehensive validation before processing
public boolean validateContactData(String name, String type, String phone) {
    return isValidName(name) &&
           isValidType(type) &&
           isValidPhoneNumber(phone) &&
           !isReservedNumber(phone);
}
```

📝 Logging API (Future Enhancement)

Suggested Logging Interface

```java
public interface Logger {
    void logInfo(String message);
    void logError(String message, Exception e);
    void logOperation(String operation, boolean success);
}
```

🔗 Integration API

Data Import/Export (Future)

```java
public interface DataExporter {
    void exportToCSV(List<Contact> contacts, String filename);
    List<Contact> importFromCSV(String filename);
    
    void exportToJSON(List<Contact> contacts, String filename);
    List<Contact> importFromJSON(String filename);
}
```

---

📋 API Summary Table

Component Purpose Key Methods
Contact Data Model addPhoneNumber(), isNameSimilar()
ContactManager Business Logic addContact(), searchByName(), deleteByNumber()
MenuInterface User Interface displayMainMenu(), getInput(), displayResults()
SearchEngine Search Algorithms searchByNameFuzzy(), searchByNumberExact()
ValidationUtils Input Validation isValidName(), isValidPhoneNumber()

🎯 Quick Reference

Most Commonly Used Methods

1. ContactManager.addContact() - Add new contact
2. ContactManager.searchByName() - Find contacts
3. ContactManager.deleteByName() - Remove contacts
4. MenuInterface.displayMainMenu() - Show options
5. ValidationUtils.isValidPhoneNumber() - Validate input

Return Value Patterns

· Boolean methods: Return true on success, false on failure
· List methods: Return empty list if no results
· Integer methods: Return 0 if nothing affected
· String methods: Return empty string or default value if invalid

---

API Version: 1.0
Last Updated: February 2026
Author: [Your Name] - UCAS Programming Language 1
Status: Production Ready ✅

For questions or issues, contact your lab lecturer with: Name, University ID, Section ID, Subject Name

```
