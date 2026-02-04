package com.ucas.addressbook;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    
    // Standard search: name contains search term
    public List<Contact> searchByNameContains(List<Contact> contacts, String searchTerm) {
        List<Contact> results = new ArrayList<>();
        String searchLower = searchTerm.toLowerCase();
        
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchLower)) {
                results.add(contact);
            }
        }
        
        return results;
    }
    
    // Bonus: Fuzzy name matching
    public List<Contact> searchByNameFuzzy(List<Contact> contacts, String searchName) {
        List<Contact> results = new ArrayList<>();
        searchName = searchName.toLowerCase();
        
        for (Contact contact : contacts) {
            if (isSimilarName(contact.getName().toLowerCase(), searchName)) {
                results.add(contact);
            }
        }
        
        return results;
    }
    
    // Exact number search
    public List<Contact> searchByNumberExact(List<Contact> contacts, String number) {
        List<Contact> results = new ArrayList<>();
        
        for (Contact contact : contacts) {
            if (contact.hasPhoneNumber(number)) {
                results.add(contact);
            }
        }
        
        return results;
    }
    
    // Advanced fuzzy matching algorithm
    private boolean isSimilarName(String name1, String name2) {
        // 1. Direct comparison
        if (name1.equals(name2)) return true;
        
        // 2. One contains the other
        if (name1.contains(name2) || name2.contains(name1)) return true;
        
        // 3. Check for common Arabic/English name variations
        if (isCommonVariation(name1, name2)) return true;
        
        // 4. Calculate Levenshtein distance (simplified)
        double similarity = calculateNameSimilarity(name1, name2);
        return similarity >= 0.75; // 75% similarity threshold
    }
    
    private boolean isCommonVariation(String name1, String name2) {
        // Common name variations in Arabic/English
        String[][] variations = {
            {"mohamed", "mohamad", "mohammed", "muhammad", "محمد"},
            {"ahmed", "ahmad", "احمد"},
            {"ali", "aly", "علي"},
            {"yousef", "yusuf", "youssef", "يوسف"},
            {"khaled", "khalid", "خالد"},
            {"osama", "usama", "اسامة"},
            {"hassan", "hassaan", "حسن"},
            {"ibrahim", "ibraheem", "ابراهيم"},
            {"nour", "noor", "نور"},
            {"fatima", "fatma", "fatimah", "فاطمة"}
        };
        
        for (String[] group : variations) {
            boolean found1 = false, found2 = false;
            for (String variant : group) {
                if (name1.contains(variant) || variant.contains(name1)) found1 = true;
                if (name2.contains(variant) || variant.contains(name2)) found2 = true;
            }
            if (found1 && found2) return true;
        }
        
        return false;
    }
    
    private double calculateNameSimilarity(String name1, String name2) {
        int maxLength = Math.max(name1.length(), name2.length());
        if (maxLength == 0) return 1.0;
        
        // Calculate Hamming distance (simplified)
        int minLength = Math.min(name1.length(), name2.length());
        int matches = 0;
        
        for (int i = 0; i < minLength; i++) {
            if (name1.charAt(i) == name2.charAt(i)) {
                matches++;
            }
        }
        
        // Account for length difference
        double lengthPenalty = Math.abs(name1.length() - name2.length()) * 0.1;
        double similarity = (double) matches / maxLength;
        
        return similarity - lengthPenalty;
    }
    
    // Search by contact type
    public List<Contact> searchByType(List<Contact> contacts, String type) {
        List<Contact> results = new ArrayList<>();
        
        for (Contact contact : contacts) {
            if (contact.getType().equalsIgnoreCase(type)) {
                results.add(contact);
            }
        }
        
        return results;
    }
              }
