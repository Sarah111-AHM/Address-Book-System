package com.ucas.addressbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactManager {
    private List<Contact> contacts;
    private ValidationUtils validator;
    
    public ContactManager() {
        this.contacts = new ArrayList<>();
        this.validator = new ValidationUtils();
    }
    
    // Add contact with single or multiple numbers
    public boolean addContact(String name, String type, String phoneNumber, boolean allowMultipleNumbers) {
        // Validate inputs
        if (!validator.isValidName(name) || 
            !validator.isValidType(type) || 
            !validator.isValidPhoneNumber(phoneNumber)) {
            return false;
        }
        
        // Check if number is reserved
        if (validator.isReservedNumber(phoneNumber)) {
            System.out.println("Error: " + phoneNumber + " is a reserved emergency number.");
            return false;
        }
        
        // Check if number already exists
        if (isNumberExists(phoneNumber)) {
            System.out.println("Error: This phone number already exists in another contact.");
            return false;
        }
        
        // Check if contact with same name exists (for adding multiple numbers)
        Contact existingContact = findContactByName(name);
        if (existingContact != null && allowMultipleNumbers) {
            // Add number to existing contact
            existingContact.addPhoneNumber(phoneNumber);
            System.out.println("📞 Number added to existing contact: " + existingContact.getName());
            return true;
        }
        
        // Create new contact
        Contact newContact = new Contact(name, type, phoneNumber);
        contacts.add(newContact);
        return true;
    }
    
    // Search by name (partial matching)
    public List<Contact> searchByName(String name, boolean useFuzzy) {
        List<Contact> results = new ArrayList<>();
        
        for (Contact contact : contacts) {
            if (useFuzzy) {
                // Bonus: Fuzzy matching
                if (contact.isNameSimilar(name)) {
                    results.add(contact);
                }
            } else {
                // Standard: Contains matching
                if (contact.containsName(name)) {
                    results.add(contact);
                }
            }
        }
        
        return results;
    }
    
    // Search by number (exact match)
    public List<Contact> searchByNumber(String number) {
        List<Contact> results = new ArrayList<>();
        
        for (Contact contact : contacts) {
            if (contact.hasPhoneNumber(number)) {
                results.add(contact);
            }
        }
        
        return results;
    }
    
    // Delete by name (exact match) - deletes all matches
    public int deleteByName(String name) {
        int deletedCount = 0;
        Iterator<Contact> iterator = contacts.iterator();
        
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                deletedCount++;
            }
        }
        
        return deletedCount;
    }
    
    // Delete by number (exact match)
    public boolean deleteByNumber(String number) {
        boolean deleted = false;
        
        for (int i = contacts.size() - 1; i >= 0; i--) {
            Contact contact = contacts.get(i);
            if (contact.hasPhoneNumber(number)) {
                if (contact.getPhoneNumbers().size() > 1) {
                    // Remove only this number (Bonus feature)
                    contact.removePhoneNumber(number);
                    System.out.println("📞 Number removed from contact: " + contact.getName());
                } else {
                    // Remove entire contact
                    contacts.remove(i);
                }
                deleted = true;
                break; // Remove only first match (or modify to remove all)
            }
        }
        
        return deleted;
    }
    
    // Get all contacts
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }
    
    // Helper methods
    private Contact findContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
    
    private boolean isNumberExists(String number) {
        for (Contact contact : contacts) {
            if (contact.hasPhoneNumber(number)) {
                return true;
            }
        }
        return false;
    }
    
    public int getContactCount() {
        return contacts.size();
    }
    
    public void clearAllContacts() {
        contacts.clear();
    }
}
