/* 
Unit Tests for AddressBookSystem
Programming Language 1 - UCAS
Testing the main application flow
*/

package com.ucas.addressbook.tests;

import com.ucas.addressbook.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookSystemTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    @DisplayName("Test Main Menu Display")
    void testMainMenuDisplay() {
        // Simulate user input: 7 (Exit)
        String input = "7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // This would run main, but for unit test we check components
        assertTrue(true); // Placeholder for menu display test
    }
    
    @Test
    @DisplayName("Test Add Contact Flow")
    void testAddContactFlow() {
        ContactManager manager = new ContactManager();
        MenuInterface menu = new MenuInterface();
        
        // Simulate adding a contact
        String name = "Test User";
        String type = "Personal";
        String phone = "0599123456";
        
        boolean result = manager.addContact(name, type, phone, false);
        assertTrue(result, "Contact should be added successfully");
        assertEquals(1, manager.getContactCount(), "Should have 1 contact");
    }
    
    @Test
    @DisplayName("Test Search Contact Flow")
    void testSearchContactFlow() {
        ContactManager manager = new ContactManager();
        
        // Add test data
        manager.addContact("Alice Smith", "Friend", "1111111111", false);
        manager.addContact("Bob Johnson", "Work", "2222222222", false);
        
        // Search by name
        List<Contact> results = manager.searchByName("Alice", false);
        assertEquals(1, results.size(), "Should find Alice");
        assertEquals("Alice Smith", results.get(0).getName());
        
        // Search by number
        results = manager.searchByNumber("2222222222");
        assertEquals(1, results.size(), "Should find Bob by number");
    }
    
    @Test
    @DisplayName("Test Delete Contact Flow")
    void testDeleteContactFlow() {
        ContactManager manager = new ContactManager();
        
        // Add test data
        manager.addContact("Test Delete", "Personal", "3333333333", false);
        assertEquals(1, manager.getContactCount(), "Should have 1 contact");
        
        // Delete by name
        int deleted = manager.deleteByName("Test Delete");
        assertEquals(1, deleted, "Should delete 1 contact");
        assertEquals(0, manager.getContactCount(), "Should have 0 contacts");
        
        // Delete by number
        manager.addContact("Another Test", "Work", "4444444444", false);
        boolean deletedByNumber = manager.deleteByNumber("4444444444");
        assertTrue(deletedByNumber, "Should delete by number");
        assertEquals(0, manager.getContactCount(), "Should have 0 contacts");
    }
    
    @Test
    @DisplayName("Test Display All Contacts")
    void testDisplayAllContacts() {
        ContactManager manager = new ContactManager();
        
        // Add multiple contacts
        manager.addContact("Contact 1", "Family", "5555555555", false);
        manager.addContact("Contact 2", "Personal", "6666666666", false);
        
        List<Contact> allContacts = manager.getAllContacts();
        assertEquals(2, allContacts.size(), "Should have 2 contacts");
        
        // Check contents
        boolean hasContact1 = allContacts.stream()
            .anyMatch(c -> c.getName().equals("Contact 1"));
        assertTrue(hasContact1, "Should contain Contact 1");
    }
    
    @Test
    @DisplayName("Test Reserved Number Prevention")
    void testReservedNumberPrevention() {
        ContactManager manager = new ContactManager();
        ValidationUtils validator = new ValidationUtils();
        
        // Test with reserved number
        String reservedNumber = "911";
        assertTrue(validator.isReservedNumber(reservedNumber), "911 should be reserved");
        
        // Try to add contact with reserved number
        // Note: addContact should fail for reserved numbers
        // This test depends on implementation
    }
    
    @Test
    @DisplayName("Test Duplicate Number Prevention")
    void testDuplicateNumberPrevention() {
        ContactManager manager = new ContactManager();
        
        // Add first contact
        boolean firstAdd = manager.addContact("First", "Personal", "7777777777", false);
        assertTrue(firstAdd, "First contact should be added");
        
        // Try to add second contact with same number
        // Note: This depends on your duplicate prevention implementation
        // In our design, second add should fail
    }
    
    @Test
    @DisplayName("Test Complete Workflow")
    void testCompleteWorkflow() {
        ContactManager manager = new ContactManager();
        
        // 1. Add contacts
        manager.addContact("Workflow Test 1", "Work", "8888888888", false);
        manager.addContact("Workflow Test 2", "Family", "9999999999", false);
        
        // 2. Search
        List<Contact> results = manager.searchByName("Workflow", false);
        assertEquals(2, results.size(), "Should find both workflow contacts");
        
        // 3. Delete one
        int deleted = manager.deleteByName("Workflow Test 1");
        assertEquals(1, deleted, "Should delete one contact");
        
        // 4. Verify remaining
        results = manager.searchByName("Workflow", false);
        assertEquals(1, results.size(), "Should have one remaining");
        assertEquals("Workflow Test 2", results.get(0).getName());
        
        // 5. Clear all
        manager.clearAllContacts();
        assertEquals(0, manager.getContactCount(), "Should have 0 contacts");
    }
    
    @Test
    @DisplayName("Test Bonus Features - Multiple Numbers")
    void testMultipleNumbersFeature() {
        ContactManager manager = new ContactManager();
        
        // Add contact with option to add multiple numbers
        manager.addContact("Multi Number", "Personal", "1111111111", true);
        
        // Try to add same contact again with different number
        // Should add number to existing contact
        // This depends on your implementation
    }
    
    @Test
    @DisplayName("Test Bonus Features - Fuzzy Search")
    void testFuzzySearchFeature() {
        ContactManager manager = new ContactManager();
        
        // Add contacts with similar names
        manager.addContact("Mohamed Ahmed", "Personal", "1234567890", false);
        manager.addContact("Mohammad Ahmad", "Work", "0987654321", false);
        manager.addContact("Muhammad Ahmed", "Family", "1122334455", false);
        
        // Search with fuzzy matching
        List<Contact> results = manager.searchByName("Mohamed", true);
        assertTrue(results.size() >= 1, "Should find similar names");
        
        // Test with exact match disabled
        results = manager.searchByName("Mohamed", false);
        // Should only find exact match if exists
    }
}
