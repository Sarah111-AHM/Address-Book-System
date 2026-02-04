package com.ucas.addressbook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtils {
    private static final Set<String> RESERVED_NUMBERS = new HashSet<>(
        Arrays.asList("911", "112", "999", "100", "101")
    );
    
    private static final Set<String> ALLOWED_TYPES = new HashSet<>(
        Arrays.asList("Family", "Personal", "Work", "Other")
    );
    
    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println(" Error: Name cannot be empty.");
            return false;
        }
        
        if (name.trim().length() < 2) {
            System.out.println(" Error: Name must be at least 2 characters.");
            return false;
        }
        
        // Check for valid characters (letters, spaces, hyphens, apostrophes)
        if (!name.matches("^[\\p{L} .'-]+$")) {
            System.out.println("Error: Name contains invalid characters.");
            return false;
        }
        
        return true;
    }
    
    public boolean isValidType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }
        
        for (String allowedType : ALLOWED_TYPES) {
            if (allowedType.equalsIgnoreCase(type.trim())) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            System.out.println("❌ Error: Phone number cannot be empty.");
            return false;
        }
        
        // Remove any non-digit characters
        String digitsOnly = phone.replaceAll("[^0-9]", "");
        
        if (digitsOnly.isEmpty()) {
            System.out.println("Error: Phone number must contain digits.");
            return false;
        }
        
        // Check length (adjust based on your country)
        if (digitsOnly.length() < 7 || digitsOnly.length() > 15) {
            System.out.println("Error: Phone number must be 7-15 digits.");
            return false;
        }
        
        // Check if it's a reserved number
        if (isReservedNumber(digitsOnly)) {
            System.out.println("Error: " + digitsOnly + " is a reserved emergency number.");
            return false;
        }
        
        return true;
    }
    
    public boolean isReservedNumber(String phone) {
        String digitsOnly = phone.replaceAll("[^0-9]", "");
        return RESERVED_NUMBERS.contains(digitsOnly);
    }
    
    public String standardizeType(String type) {
        if (type == null) return "Other";
        
        for (String allowedType : ALLOWED_TYPES) {
            if (allowedType.equalsIgnoreCase(type.trim())) {
                return allowedType;
            }
        }
        
        return "Other";
    }
    
    public String validateAndCleanPhone(String phone) {
        if (phone == null) return "";
        
        // Remove all non-digit characters
        String cleaned = phone.replaceAll("[^0-9]", "");
        
        // Add country code if missing (assuming Palestine +970)
        if (cleaned.startsWith("0") && cleaned.length() == 10) {
            cleaned = "970" + cleaned.substring(1);
        }
        
        return cleaned;
    }
    
    public boolean isDuplicateNumberAllowed() {
        // Configuration: return false to prevent duplicates
        return false; // Change to true if duplicates are allowed
    }
    
    // Get all allowed types for display purposes
    public String[] getAllowedTypes() {
        return ALLOWED_TYPES.toArray(new String[0]);
    }
    
    // Get all reserved numbers for display purposes
    public String[] getReservedNumbers() {
        return RESERVED_NUMBERS.toArray(new String[0]);
    }
}
