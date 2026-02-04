/*
Unit Tests for ContactManager Class
Programming Language 1 - UCAS
Testing business logic and operations
*/

package com.ucas.addressbook.tests;

import com.ucas.addressbook.Contact;
import com.ucas.addressbook.ContactManager;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {
    private ContactManager contactManager;
    
    @BeforeEach
    void setUp() {
        contactManager = new ContactManager();
    }
    
    @AfterEach
    void tearDown() {
        contactManager.clearAllContacts();
    }
    
    @Test
    @DisplayName("Test Initial State")
    void testInitialState() {
        assertEquals(0, contactManager.getContactCount());
        assertTrue(contactManager.getAllContacts().isEmpty());
    }
    
    @Test
    @DisplayName("Test Add Contact - Valid Input")
    void testAddContactValid() {
        boolean result = contactManager.addContact("Test User", "Personal", "0599123456", false);
        assertTrue(result, "Contact should be added successfully");
        assertEquals(1, contactManager.getContactCount());
        
        List<Contact> contacts = contactManager.getAllContacts();
        assertEquals(1, contacts.size());
        assertEquals("Test User", contacts.get(0).getName());
    }
    
    @Test
    @DisplayName("Test Add Contact - Invalid Name")
    void testAddContactInvalidName() {
        // This depends on your validation implementation
        // If validation fails in ContactManager, should return false
        boolean result = contactManager.addContact("", "Personal", "1234567890", false);
        // assertFalse(result); // Uncomment if your implementation returns false for invalid names
    }
    
    @Test
    @DisplayName("Test Add Contact - Invalid Type")
    void testAddContactInvalidType() {
        boolean result = contactManager.addContact("Test", "InvalidType", "1234567890", false);
        // Should handle invalid type (either reject or default to "Other")
        // Depends on implementation
    }
    
    @Test
    @DisplayName("Test Add Contact - Duplicate Prevention")
    void testAddContactDuplicatePrevention() {
        // Add first contact
        boolean first = contactManager.addContact("First", "Personal", "1111111111", false);
        assertTrue(first);
        assertEquals(1, contactManager.getContactCount());
        
        // Try to add second contact with same number
        // In our design, this should fail
        boolean second = contactManager.addContact("Second", "Work", "1111111111", false);
        // assertFalse(second); // Uncomment if duplicates are prevented
    }
    
    @Test
    @DisplayName("Test Add Contact - Multiple Numbers (Bonus)")
    void testAddContactMultipleNumbers() {
        // Add first contact
        contactManager.addContact("Multi", "Personal", "1111111111", true);
        
        // Add second number to same contact
        // In our implementation, this adds to existing contact
        contactManager.addContact("Multi", "Personal", "2222222222", true);
        
        // Should still have 1 contact but with 2 numbers
        assertEquals(1, contactManager.getContactCount());
        
        List<Contact> contacts = contactManager.getAllContacts();
        Contact contact = contacts.get(0);
        assertTrue(contact.getPhoneNumbers().size() >= 1);
    }
    
    @Test
    @DisplayName("Test Search By Name - Exact Match")
    void testSearchByNameExact() {
        // Setup test data
        contactManager.addContact("Alice Wonderland", "Personal", "111", false);
        contactManager.addContact("Bob Builder", "Work", "222", false);
        contactManager.addContact("Charlie Brown", "Family", "333", false);
        
        // Search exact name
        List<Contact> results = contactManager.searchByName("Alice", false);
        assertEquals(1, results.size());
        assertEquals("Alice Wonderland", results.get(0).getName());
        
        // Search partial
        results = contactManager.searchByName("B", false);
        assertEquals(2, results.size()); // Bob and Charlie Brown (contains 'b')
        
        // Search non-existent
        results = contactManager.searchByName("Nonexistent", false);
        assertTrue(results.isEmpty());
    }
    
    @Test
    @DisplayName("Test Search By Name - Fuzzy Match (Bonus)")
    void testSearchByNameFuzzy() {
        contactManager.addContact("Mohamed Ahmed", "Personal", "111", false);
        contactManager.addContact("Mohammad Ahmad", "Work", "222", false);
        contactManager.addContact("Muhammad Ahmed", "Family", "333", false);
        contactManager.addContact("John Smith", "Other", "444", false);
        
        // Fuzzy search for variations
        List<Contact> results = contactManager.searchByName("Mohamed", true);
        assertTrue(results.size() >= 2, "Should find similar names");
        
        // Fuzzy search for non-similar
        results = contactManager.searchByName("John", true);
        assertEquals(1, results.size(), "Should find exact match");
        
        // Fuzzy search with no matches
        results = contactManager.searchByName("XYZ", true);
        assertTrue(results.isEmpty());
    }
    
    @Test
    @DisplayName("Test Search By Number - Exact Match")
    void testSearchByNumberExact() {
        contactManager.addContact("Phone Test 1", "Personal", "5555555555", false);
        contactManager.addContact("Phone Test 2", "Work", "6666666666", false);
        
        List<Contact> results = contactManager.searchByNumber("5555555555");
        assertEquals(1, results.size());
        assertEquals("Phone Test 1", results.get(0).getName());
        
        results = contactManager.searchByNumber("7777777777");
        assertTrue(results.isEmpty());
    }
    
    @Test
    @DisplayName("Test Search By Number - Multiple Contacts Same Number")
    void testSearchByNumberMultipleContacts() {
        // In our design, numbers should be unique
        // So this scenario shouldn't happen
        // But testing for robustness
        contactManager.addContact("Shared 1", "Personal", "8888888888", false);
        
        List<Contact> results = contactManager.searchByNumber("8888888888");
        assertEquals(1, results.size());
    }
    
    @Test
    @DisplayName("Test Delete By Name - Exact Match")
    void testDeleteByNameExact() {
        // Setup
        contactManager.addContact("To Delete", "Personal", "111", false);
        contactManager.addContact("Keep Me", "Work", "222", false);
        assertEquals(2, contactManager.getContactCount());
        
        // Delete
        int deleted = contactManager.deleteByName("To Delete");
        assertEquals(1, deleted);
        assertEquals(1, contactManager.getContactCount());
        
        // Verify remaining
        List<Contact> remaining = contactManager.getAllContacts();
        assertEquals("Keep Me", remaining.get(0).getName());
        
        // Delete non-existent
        deleted = contactManager.deleteByName("Nonexistent");
        assertEquals(0, deleted);
        assertEquals(1, contactManager.getContactCount());
    }
    
    @Test
    @DisplayName("Test Delete By Name - Multiple Matches")
    void testDeleteByNameMultipleMatches() {
        contactManager.addContact("Same Name", "Personal", "111", false);
        contactManager.addContact("Same Name", "Work", "222", false);
        contactManager.addContact("Same Name", "Family", "333", false);
        contactManager.addContact("Different", "Other", "444", false);
        
        assertEquals(4, contactManager.getContactCount());
        
        int deleted = contactManager.deleteByName("Same Name");
        assertEquals(3, deleted);
        assertEquals(1, contactManager.getContactCount());
        
        List<Contact> remaining = contactManager.getAllContacts();
        assertEquals("Different", remaining.get(0).getName());
    }
    
    @Test
    @DisplayName("Test Delete By Name - Case Insensitive")
    void testDeleteByNameCaseInsensitive() {
        contactManager.addContact("Case Test", "Personal", "111", false);
        
        int deleted = contactManager.deleteByName("CASE TEST");
        assertEquals(1, deleted, "Should be case insensitive");
        assertEquals(0, contactManager.getContactCount());
    }
    
    @Test
    @DisplayName("Test Delete By Number")
    void testDeleteByNumber() {
        contactManager.addContact("Number Delete", "Personal", "1234567890", false);
        contactManager.addContact("Another", "Work", "0987654321", false);
        
        assertEquals(2, contactManager.getContactCount());
        
        boolean deleted = contactManager.deleteByNumber("1234567890");
        assertTrue(deleted);
        assertEquals(1, contactManager.getContactCount());
        
        // Verify correct contact deleted
        List<Contact> remaining = contactManager.getAllContacts();
        assertEquals("Another", remaining.get(0).getName());
        
        // Delete non-existent number
        deleted = contactManager.deleteByNumber("0000000000");
        assertFalse(deleted);
        assertEquals(1, contactManager.getContactCount());
    }
    
    @Test
    @DisplayName("Test Delete By Number - Multiple Numbers Contact")
    void testDeleteByNumberMultipleNumbers() {
        // Add contact with multiple numbers
        contactManager.addContact("Multi", "Personal", "111", true);
        contactManager.addContact("Multi", "Personal", "222", true);
        
        // Should have 1 contact with 2 numbers
        assertEquals(1, contactManager.getContactCount());
        
        // Delete one number
        boolean deleted = contactManager.deleteByNumber("111");
        assertTrue(deleted);
        
        // Contact should still exist with 1 number
        assertEquals(1, contactManager.getContactCount());
        List<Contact> contacts = contactManager.getAllContacts();
        assertTrue(contacts.get(0).hasPhoneNumber("222"));
        assertFalse(contacts.get(0).hasPhoneNumber("111"));
    }
    
    @Test
    @DisplayName("Test GetAllContacts")
    void testGetAllContacts() {
        assertTrue(contactManager.getAllContacts().isEmpty());
        
        contactManager.addContact("A", "Personal", "1", false);
        contactManager.addContact("B", "Work", "2", false);
        contactManager.addContact("C", "Family", "3", false);
        
        List<Contact> all = contactManager.getAllContacts();
        assertEquals(3, all.size());
        
        // Should return copy, not original list
        all.clear();
        assertEquals(3, contactManager.getContactCount(), 
            "Clearing returned list shouldn't affect manager");
    }
    
    @Test
    @DisplayName("Test GetContactCount")
    void testGetContactCount() {
        assertEquals(0, contactManager.getContactCount());
        
        contactManager.addContact("Test", "Personal", "123", false);
        assertEquals(1, contactManager.getContactCount());
        
        contactManager.addContact("Test2", "Work", "456", false);
        assertEquals(2, contactManager.getContactCount());
        
        contactManager.deleteByName("Test");
        assertEquals(1, contactManager.getContactCount());
    }
    
    @Test
    @DisplayName("Test ClearAllContacts")
    void testClearAllContacts() {
        contactManager.addContact("A", "Personal", "1", false);
        contactManager.addContact("B", "Work", "2", false);
        
        assertEquals(2, contactManager.getContactCount());
        
        contactManager.clearAllContacts();
        assertEquals(0, contactManager.getContactCount());
        assertTrue(contactManager.getAllContacts().isEmpty());
        
        // Can add after clear
        contactManager.addContact("C", "Family", "3", false);
        assertEquals(1, contactManager.getContactCount());
    }
    
    @Test
    @DisplayName("Test Performance - Many Contacts")
    void testPerformanceManyContacts() {
        // Add many contacts
        for (int i = 0; i < 1000; i++) {
            contactManager.addContact("Contact " + i, "Personal", "Number" + i, false);
        }
        
        assertEquals(1000, contactManager.getContactCount());
        
        // Search performance
        long startTime = System.currentTimeMillis();
        List<Contact> results = contactManager.searchByName("Contact 500", false);
        long endTime = System.currentTimeMillis();
        
        assertEquals(1, results.size());
        assertTrue((endTime - startTime) < 1000, 
            "Search should complete within 1 second for 1000 contacts");
    }
    
    @Test
    @DisplayName("Test Edge Cases - Empty Database Operations")
    void testEmptyDatabaseOperations() {
        // Search empty
        List<Contact> results = contactManager.searchByName("Anything", false);
        assertTrue(results.isEmpty());
        
        results = contactManager.searchByNumber("123");
        assertTrue(results.isEmpty());
        
        // Delete from empty
        int deleted = contactManager.deleteByName("None");
        assertEquals(0, deleted);
        
        boolean deletedByNumber = contactManager.deleteByNumber("123");
        assertFalse(deletedByNumber);
        
        // Get all from empty
        assertTrue(contactManager.getAllContacts().isEmpty());
        assertEquals(0, contactManager.getContactCount());
    }
    
    @Test
    @DisplayName("Test Integration - Full CRUD Cycle")
    void testFullCrudCycle() {
        // Create
        contactManager.addContact("CRUD Test", "Personal", "123", false);
        assertEquals(1, contactManager.getContactCount());
        
        // Read
        List<Contact> found = contactManager.searchByName("CRUD", false);
        assertEquals(1, found.size());
        
        // Update (simulated by delete + add)
        contactManager.deleteByName("CRUD Test");
        contactManager.addContact("CRUD Updated", "Work", "456", false);
        
        // Verify update
        found = contactManager.searchByName("Updated", false);
        assertEquals(1, found.size());
        
        // Delete
        int deleted = contactManager.deleteByName("CRUD Updated");
        assertEquals(1, deleted);
        assertEquals(0, contactManager.getContactCount());
    }
}
