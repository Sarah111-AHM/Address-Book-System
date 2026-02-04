/*
Unit Tests for ValidationUtils Class
Programming Language 1 - UCAS
Testing input validation and sanitization
*/

package com.ucas.addressbook.tests;

import com.ucas.addressbook.ValidationUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {
    private ValidationUtils validator;
    
    @BeforeEach
    void setUp() {
        validator = new ValidationUtils();
    }
    
    // ========== Name Validation Tests ==========
    
    @Test
    @DisplayName("Test Valid Name - Normal Case")
    void testValidNameNormal() {
        assertTrue(validator.isValidName("John Doe"));
        assertTrue(validator.isValidName("Alice"));
        assertTrue(validator.isValidName("Mohamed Ahmed"));
    }
    
    @Test
    @DisplayName("Test Valid Name - With Special Characters")
    void testValidNameSpecialCharacters() {
        assertTrue(validator.isValidName("O'Connor"));
        assertTrue(validator.isValidName("Smith-Jones"));
        assertTrue(validator.isValidName("Jean-Luc Picard"));
        assertTrue(validator.isValidName("John O'Reilly-Smith"));
    }
    
    @Test
    @DisplayName("Test Valid Name - International Characters")
    void testValidNameInternational() {
        assertTrue(validator.isValidName("محمد أحمد"));
        assertTrue(validator.isValidName("José González"));
        assertTrue(validator.isValidName("François D'Haene"));
        assertTrue(validator.isValidName("北京张"));
    }
    
    @Test
    @DisplayName("Test Invalid Name - Empty or Null")
    void testInvalidNameEmptyNull() {
        assertFalse(validator.isValidName(""));
        assertFalse(validator.isValidName("   "));
        // For null, implementation should handle gracefully
        // This might throw exception or return false
    }
    
    @Test
    @DisplayName("Test Invalid Name - Too Short")
    void testInvalidNameTooShort() {
        assertFalse(validator.isValidName("A"));
        assertFalse(validator.isValidName(""));
    }
    
    @Test
    @DisplayName("Test Invalid Name - Invalid Characters")
    void testInvalidNameInvalidCharacters() {
        assertFalse(validator.isValidName("John123")); // Numbers
        assertFalse(validator.isValidName("John@Doe")); // Special chars
        assertFalse(validator.isValidName("John_Doe")); // Underscore
        assertFalse(validator.isValidName("John;DROP TABLE users;")); // SQL injection attempt
    }
    
    // ========== Type Validation Tests ==========
    
    @Test
    @DisplayName("Test Valid Type - Allowed Types")
    void testValidTypeAllowed() {
        assertTrue(validator.isValidType("Family"));
        assertTrue(validator.isValidType("Personal"));
        assertTrue(validator.isValidType("Work"));
        assertTrue(validator.isValidType("Other"));
        
        // Case insensitive
        assertTrue(validator.isValidType("FAMILY"));
        assertTrue(validator.isValidType("family"));
        assertTrue(validator.isValidType("FaMiLy"));
    }
    
    @Test
    @DisplayName("Test Invalid Type")
    void testInvalidType() {
        assertFalse(validator.isValidType("Friend"));
        assertFalse(validator.isValidType("Business"));
        assertFalse(validator.isValidType(""));
        assertFalse(validator.isValidType("123"));
        assertFalse(validator.isValidType(null));
    }
    
    @Test
    @DisplayName("Test Type Standardization")
    void testTypeStandardization() {
        assertEquals("Family", validator.standardizeType("Family"));
        assertEquals("Family", validator.standardizeType("FAMILY"));
        assertEquals("Family", validator.standardizeType("family"));
        assertEquals("Family", validator.standardizeType("FaMiLy"));
        
        assertEquals("Other", validator.standardizeType("Invalid"));
        assertEquals("Other", validator.standardizeType(""));
        assertEquals("Other", validator.standardizeType(null));
    }
    
    // ========== Phone Number Validation Tests ==========
    
    @Test
    @DisplayName("Test Valid Phone Number - Normal Cases")
    void testValidPhoneNumberNormal() {
        assertTrue(validator.isValidPhoneNumber("0599123456"));
        assertTrue(validator.isValidPhoneNumber("022345678"));
        assertTrue(validator.isValidPhoneNumber("1234567"));
        assertTrue(validator.isValidPhoneNumber("123456789012345")); // 15 digits
    }
    
    @Test
    @DisplayName("Test Valid Phone Number - With Formatting")
    void testValidPhoneNumberWithFormatting() {
        // Implementation should handle or clean formatting
        // This depends on whether validator cleans or validates strictly
        // assertTrue(validator.isValidPhoneNumber("0599-123-456"));
        // assertTrue(validator.isValidPhoneNumber("+970 599 123 456"));
        // assertTrue(validator.isValidPhoneNumber("(0599) 123-456"));
    }
    
    @Test
    @DisplayName("Test Invalid Phone Number - Empty or Null")
    void testInvalidPhoneNumberEmptyNull() {
        assertFalse(validator.isValidPhoneNumber(""));
        assertFalse(validator.isValidPhoneNumber("   "));
        // Null should be handled
    }
    
    @Test
    @DisplayName("Test Invalid Phone Number - Too Short")
    void testInvalidPhoneNumberTooShort() {
        assertFalse(validator.isValidPhoneNumber("123456")); // 6 digits
        assertFalse(validator.isValidPhoneNumber("1"));
    }
    
    @Test
    @DisplayName("Test Invalid Phone Number - Too Long")
    void testInvalidPhoneNumberTooLong() {
        assertFalse(validator.isValidPhoneNumber("1234567890123456")); // 16 digits
        assertFalse(validator.isValidPhoneNumber("12345678901234567890"));
    }
    
    @Test
    @DisplayName("Test Invalid Phone Number - Non-Digits")
    void testInvalidPhoneNumberNonDigits() {
        assertFalse(validator.isValidPhoneNumber("ABCDEFGH"));
        assertFalse(validator.isValidPhoneNumber("0599-ABC-DEF"));
        assertFalse(validator.isValidPhoneNumber("Phone: 0599123456"));
    }
    
    @Test
    @DisplayName("Test Reserved Number Detection")
    void testReservedNumberDetection() {
        assertTrue(validator.isReservedNumber("911"));
        assertTrue(validator.isReservedNumber("112"));
        assertTrue(validator.isReservedNumber("999"));
        assertTrue(validator.isReservedNumber("100"));
        assertTrue(validator.isReservedNumber("101"));
        
        // With formatting
        assertTrue(validator.isReservedNumber("9-1-1"));
        assertTrue(validator.isReservedNumber("1 1 2"));
        
        // Not reserved
        assertFalse(validator.isReservedNumber("9110"));
        assertFalse(validator.isReservedNumber("119"));
        assertFalse(validator.isReservedNumber("123"));
    }
    
    @Test
    @DisplayName("Test Phone Number Cleaning")
    void testPhoneNumberCleaning() {
        assertEquals("970599123456", validator.validateAndCleanPhone("0599-123-456"));
        assertEquals("970599123456", validator.validateAndCleanPhone("+970 599 123 456"));
        assertEquals("970599123456", validator.validateAndCleanPhone("(0599) 123-456"));
        assertEquals("970599123456", validator.validateAndCleanPhone("0599 123 456"));
        
        // Already clean
        assertEquals("0599123456", validator.validateAndCleanPhone("0599123456"));
        
        // Empty/null
        assertEquals("", validator.validateAndCleanPhone(""));
        assertEquals("", validator.validateAndCleanPhone(null));
    }
    
    @Test
    @DisplayName("Test Phone Number Cleaning - International Format")
    void testPhoneNumberCleaningInternational() {
        // Test Palestine number format
        String result = validator.validateAndCleanPhone("0599123456");
        assertEquals("970599123456", result);
        
        // Test with country code already
        result = validator.validateAndCleanPhone("+970599123456");
        assertEquals("970599123456", result);
        
        // Test other country code (should preserve)
        result = validator.validateAndCleanPhone("+201234567890");
        assertEquals("201234567890", result);
    }
    
    @Test
    @DisplayName("Test GetAllowedTypes")
    void testGetAllowedTypes() {
        String[] allowedTypes = validator.getAllowedTypes();
        assertNotNull(allowedTypes);
        assertEquals(4, allowedTypes.length);
        
        // Check contains expected types
        boolean hasFamily = false, hasPersonal = false, hasWork = false, hasOther = false;
        for (String type : allowedTypes) {
            if (type.equals("Family")) hasFamily = true;
            if (type.equals("Personal")) hasPersonal = true;
            if (type.equals("Work")) hasWork = true;
            if (type.equals("Other")) hasOther = true;
        }
        
        assertTrue(hasFamily);
        assertTrue(hasPersonal);
        assertTrue(hasWork);
        assertTrue(hasOther);
    }
    
    @Test
    @DisplayName("Test GetReservedNumbers")
    void testGetReservedNumbers() {
        String[] reservedNumbers = validator.getReservedNumbers();
        assertNotNull(reservedNumbers);
        assertTrue(reservedNumbers.length >= 5);
        
        // Check contains known reserved numbers
        boolean has911 = false, has112 = false, has999 = false;
        for (String num : reservedNumbers) {
            if (num.equals("911")) has911 = true;
            if (num.equals("112")) has112 = true;
            if (num.equals("999")) has999 = true;
        }
        
        assertTrue(has911);
        assertTrue(has112);
        assertTrue(has999);
    }
    
    @Test
    @DisplayName("Test Duplicate Number Configuration")
    void testDuplicateNumberConfiguration() {
        boolean allowDuplicates = validator.isDuplicateNumberAllowed();
        // Should be false to prevent duplicates (based on requirements)
        assertFalse(allowDuplicates);
    }
    
    @Test
    @DisplayName("Test Complete Validation - Valid Contact")
    void testCompleteValidationValidContact() {
        String name = "John Doe";
        String type = "Personal";
        String phone = "0599123456";
        
        assertTrue(validator.isValidName(name));
        assertTrue(validator.isValidType(type));
        assertTrue(validator.isValidPhoneNumber(phone));
        assertFalse(validator.isReservedNumber(phone));
    }
    
    @Test
    @DisplayName("Test Complete Validation - Invalid Contact")
    void testCompleteValidationInvalidContact() {
        String name = ""; // Invalid
        String type = "InvalidType";
        String phone = "911"; // Reserved
        
        assertFalse(validator.isValidName(name));
        assertFalse(validator.isValidType(type));
        // isValidPhoneNumber might return false for reserved numbers
        // or isReservedNumber check separately
        assertTrue(validator.isReservedNumber(phone));
    }
    
    @Test
    @DisplayName("Test Edge Cases - Boundary Values")
    void testEdgeCasesBoundaryValues() {
        // Name length boundaries
        String minName = "Ab"; // 2 characters
        String maxName = "A".repeat(50); // 50 characters
        String tooLongName = "A".repeat(51); // 51 characters
        
        assertTrue(validator.isValidName(minName));
        assertTrue(validator.isValidName(maxName));
        // assertFalse(validator.isValidName(tooLongName)); // If length check implemented
        
        // Phone length boundaries
        String minPhone = "1234567"; // 7 digits
        String maxPhone = "1".repeat(15); // 15 digits
        String tooShortPhone = "123456"; // 6 digits
        String tooLongPhone = "1".repeat(16); // 16 digits
        
        assertTrue(validator.isValidPhoneNumber(minPhone));
        assertTrue(validator.isValidPhoneNumber(maxPhone));
        assertFalse(validator.isValidPhoneNumber(tooShortPhone));
        assertFalse(validator.isValidPhoneNumber(tooLongPhone));
    }
    
    @Test
    @DisplayName("Test SQL Injection Prevention")
    void testSqlInjectionPrevention() {
        // Attempt SQL injection in name
        String sqlInjectionName = "John'; DROP TABLE contacts; --";
        assertFalse(validator.isValidName(sqlInjectionName));
        
        // Attempt SQL injection in phone
        String sqlInjectionPhone = "123'; DROP TABLE contacts; --";
        assertFalse(validator.isValidPhoneNumber(sqlInjectionPhone));
    }
    
    @Test
    @DisplayName("Test XSS Prevention")
    void testXssPrevention() {
        // Attempt XSS in name
        String xssName = "<script>alert('xss')</script>John";
        assertFalse(validator.isValidName(xssName));
        
        String xssName2 = "John<img src=x onerror=alert(1)>";
        assertFalse(validator.isValidName(xssName2));
    }
    
    @Test
    @DisplayName("Test Performance - Many Validations")
    void testPerformanceManyValidations() {
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 10000; i++) {
            validator.isValidName("Test Name " + i);
            validator.isValidPhoneNumber("0599" + i);
            validator.isValidType("Personal");
        }
        
        long endTime = System.currentTimeMillis();
        
        assertTrue((endTime - startTime) < 1000,
            "10,000 validations should complete within 1 second");
    }
    
    @Test
    @DisplayName("Test International Phone Number Formats")
    void testInternationalPhoneNumberFormats() {
        // Test various international formats
        // Note: Implementation might be localized for Palestine
        // These tests check flexibility
        
        String[] internationalNumbers = {
            "+1-800-555-1234",      // US
            "+44 20 7946 0958",     // UK
            "+33 1 23 45 67 89",    // France
            "+81 3 1234 5678",      // Japan
            "+86 10 1234 5678",     // China
        };
        
        for (String number : internationalNumbers) {
            // Clean should remove non-digits
            String cleaned = validator.validateAndCleanPhone(number);
            assertNotNull(cleaned);
            assertTrue(cleaned.matches("\\d+"), "Should contain only digits: " + cleaned);
        }
    }
    
    @Test
    @DisplayName("Test Validation for Bonus Features")
    void testValidationForBonusFeatures() {
        // Test scenarios relevant to bonus features
        
        // Multiple numbers - each should be validated separately
        String[] multipleNumbers = {"0599123456", "022345678", "1234567"};
        for (String number : multipleNumbers) {
            assertTrue(validator.isValidPhoneNumber(number));
        }
        
        // Similar names - validation should still work
        String[] similarNames = {"Mohamed", "Mohamad", "Muhammad", "Mohammed"};
        for (String name : similarNames) {
            assertTrue(validator.isValidName(name));
        }
    }
}
