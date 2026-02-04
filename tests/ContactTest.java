/*
Unit Tests for SearchEngine Class
Programming Language 1 - UCAS
Testing search algorithms and fuzzy matching
*/

package com.ucas.addressbook.tests;

import com.ucas.addressbook.Contact;
import com.ucas.addressbook.SearchEngine;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineTest {
    private SearchEngine searchEngine;
    private List<Contact> testContacts;
    
    @BeforeEach
    void setUp() {
        searchEngine = new SearchEngine();
        testContacts = new ArrayList<>();
        
        // Create test contacts
        testContacts.add(new Contact("Mohamed Ahmed", "Personal", "1111111111"));
        testContacts.add(new Contact("Mohammad Ahmad", "Work", "2222222222"));
        testContacts.add(new Contact("Muhammad Ahmed", "Family", "3333333333"));
        testContacts.add(new Contact("John Smith", "Personal", "4444444444"));
        testContacts.add(new Contact("Alice Johnson", "Work", "5555555555"));
        testContacts.add(new Contact("Bob al-Walid", "Family", "6666666666"));
        testContacts.add(new Contact("Fatima Zahra", "Personal", "7777777777"));
        testContacts.add(new Contact("Aisha bint Abi Bakr", "Other", "8888888888"));
        
        // Add multiple numbers to some contacts (bonus feature)
        testContacts.get(0).addPhoneNumber("9999999999");
        testContacts.get(3).addPhoneNumber("0000000000");
    }
    
    @Test
    @DisplayName("Test Search By Name Contains - Exact Match")
    void testSearchByNameContainsExact() {
        List<Contact> results = searchEngine.searchByNameContains(testContacts, "Mohamed Ahmed");
        assertEquals(1, results.size());
        assertEquals("Mohamed Ahmed", results.get(0).getName());
    }
    
    @Test
    @DisplayName("Test Search By Name Contains - Partial Match")
    void testSearchByNameContainsPartial() {
        List<Contact> results = searchEngine.searchByNameContains(testContacts, "Moh");
        assertEquals(3, results.size()); // Mohamed, Mohammad, Muhammad
        
        results = searchEngine.searchByNameContains(testContacts, "Smith");
        assertEquals(1, results.size());
        assertEquals("John Smith", results.get(0).getName());
    }
    
    @Test
    @DisplayName("Test Search By Name Contains - Case Insensitive")
    void testSearchByNameContainsCaseInsensitive() {
        List<Contact> results = searchEngine.searchByNameContains(testContacts, "mohamed");
        assertEquals(1, results.size());
        
        results = searchEngine.searchByNameContains(testContacts, "JOHN");
        assertEquals(1, results.size());
    }
    
    @Test
    @DisplayName("Test Search By Name Contains - No Match")
    void testSearchByNameContainsNoMatch() {
        List<Contact> results = searchEngine.searchByNameContains(testContacts, "Nonexistent");
        assertTrue(results.isEmpty());
        
        results = searchEngine.searchByNameContains(testContacts, "");
        assertEquals(testContacts.size(), results.size(), 
            "Empty search should return all or none based on implementation");
    }
    
    @Test
    @DisplayName("Test Search By Name Fuzzy - Exact Match")
    void testSearchByNameFuzzyExact() {
        List<Contact> results = searchEngine.searchByNameFuzzy(testContacts, "Mohamed Ahmed");
        assertEquals(1, results.size());
    }
    
    @Test
    @DisplayName("Test Search By Name Fuzzy - Common Variations")
    void testSearchByNameFuzzyVariations() {
        // Test Arabic/English name variations
        List<Contact> results = searchEngine.searchByNameFuzzy(testContacts, "Mohamed");
        assertTrue(results.size() >= 3, "Should find Mohamed, Mohammad, Muhammad");
        
        results = searchEngine.searchByNameFuzzy(testContacts, "Ahmad");
        assertTrue(results.size() >= 1, "Should find Ahmad variations");
        
        results = searchEngine.searchByNameFuzzy(testContacts, "Fatma");
        assertTrue(results.size() >= 1, "Should find Fatima when searching Fatma");
    }
    
    @Test
    @DisplayName("Test Search By Name Fuzzy - Similar Spelling")
    void testSearchByNameFuzzySimilarSpelling() {
        List<Contact> results = searchEngine.searchByNameFuzzy(testContacts, "Mohamad");
        assertTrue(results.size() >= 2, "Should find similar names to Mohamad");
        
        results = searchEngine.searchByNameFuzzy(testContacts, "Muhammed");
        assertTrue(results.size() >= 1, "Should find Muhammad when searching Muhammed");
        
        results = searchEngine.searchByNameFuzzy(testContacts, "Aly");
        // No Ali in test data, but testing the algorithm
        assertEquals(0, results.size());
    }
    
    @Test
    @DisplayName("Test Search By Name Fuzzy - Short Names")
    void testSearchByNameFuzzyShortNames() {
        List<Contact> results = searchEngine.searchByNameFuzzy(testContacts, "Ali");
        assertEquals(0, results.size(), "No Ali in test data");
        
        results = searchEngine.searchByNameFuzzy(testContacts, "Bob");
        assertEquals(1, results.size(), "Should find Bob");
    }
    
    @Test
    @DisplayName("Test Search By Name Fuzzy - No Match")
    void testSearchByNameFuzzyNoMatch() {
        List<Contact> results = searchEngine.searchByNameFuzzy(testContacts, "XYZABC");
        assertTrue(results.isEmpty());
        
        results = searchEngine.searchByNameFuzzy(testContacts, "123");
        assertTrue(results.isEmpty());
    }
    
    @Test
    @DisplayName("Test Search By Name Fuzzy - Special Characters")
    void testSearchByNameFuzzySpecialCharacters() {
        // Test with Arabic names
        List<Contact> results = searchEngine.searchByNameFuzzy(testContacts, "بن");
        assertTrue(results.size() >= 1, "Should find 'bint' names");
        
        results = searchEngine.searchByNameFuzzy(testContacts, "الوليد");
        assertTrue(results.size() >= 1, "Should find al-Walid");
    }
    
    @Test
    @DisplayName("Test Search By Number Exact - Single Number")
    void testSearchByNumberExactSingle() {
        List<Contact> results = searchEngine.searchByNumberExact(testContacts, "1111111111");
        assertEquals(1, results.size());
        assertEquals("Mohamed Ahmed", results.get(0).getName());
    }
    
    @Test
    @DisplayName("Test Search By Number Exact - Multiple Numbers Contact")
    void testSearchByNumberExactMultiple() {
        // Contact 0 has two numbers: 1111111111 and 9999999999
        List<Contact> results = searchEngine.searchByNumberExact(testContacts, "9999999999");
        assertEquals(1, results.size());
        assertEquals("Mohamed Ahmed", results.get(0).getName());
        
        // Both numbers should return same contact
        results = searchEngine.searchByNumberExact(testContacts, "1111111111");
        assertEquals(1, results.size());
        assertEquals("Mohamed Ahmed", results.get(0).getName());
    }
    
    @Test
    @DisplayName("Test Search By Number Exact - No Match")
    void testSearchByNumber
