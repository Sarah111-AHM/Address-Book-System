package com.ucas.addressbook;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String name;
    private String type;
    private List<String> phoneNumbers;
    private int id;
    private static int nextId = 1;
    
    public Contact(String name, String type, String phoneNumber) {
        this.id = nextId++;
        this.name = name;
        this.type = type;
        this.phoneNumbers = new ArrayList<>();
        this.phoneNumbers.add(phoneNumber);
    }
    
    // Constructor for multiple numbers (Bonus feature)
    public Contact(String name, String type, List<String> phoneNumbers) {
        this.id = nextId++;
        this.name = name;
        this.type = type;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public List<String> getPhoneNumbers() { return new ArrayList<>(phoneNumbers); }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    
    // Phone number management
    public boolean addPhoneNumber(String number) {
        if (!phoneNumbers.contains(number)) {
            phoneNumbers.add(number);
            return true;
        }
        return false;
    }
    
    public boolean removePhoneNumber(String number) {
        return phoneNumbers.remove(number);
    }
    
    public boolean hasPhoneNumber(String number) {
        return phoneNumbers.contains(number);
    }
    
    // Search methods
    public boolean containsName(String searchTerm) {
        return name.toLowerCase().contains(searchTerm.toLowerCase());
    }
    
    // Bonus: Similar name check
    public boolean isNameSimilar(String searchName) {
        String contactName = name.toLowerCase();
        searchName = searchName.toLowerCase();
        
        // Direct contains
        if (contactName.contains(searchName) || searchName.contains(contactName)) {
            return true;
        }
        
        // Check length similarity
        int diff = Math.abs(contactName.length() - searchName.length());
        if (diff <= 2) {
            // Count matching characters
            int matches = 0;
            int minLength = Math.min(contactName.length(), searchName.length());
            for (int i = 0; i < minLength; i++) {
                if (contactName.charAt(i) == searchName.charAt(i)) {
                    matches++;
                }
            }
            double similarity = (double) matches / Math.max(contactName.length(), searchName.length());
            return similarity >= 0.7; // 70% similarity threshold
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id)
          .append(" | Name: ").append(name)
          .append(" | Type: ").append(type)
          .append(" | Numbers: ");
        
        for (int i = 0; i < phoneNumbers.size(); i++) {
            sb.append(phoneNumbers.get(i));
            if (i < phoneNumbers.size() - 1) {
                sb.append(", ");
            }
        }
        
        return sb.toString();
    }
    
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        for (String number : phoneNumbers) {
            sb.append("(").append(name)
              .append(", ").append(type)
              .append(", ").append(number)
              .append(")\n");
        }
        return sb.toString();
    }
}
