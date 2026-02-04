📄 DESIGN_DOCUMENTATION.md

```markdown
# 🏗️ Design Documentation - Address Book System

## 📋 Overview
This document outlines the system design and architecture of the Address Book System developed for Programming Language 1 course at University College of Applied Sciences.

## 🎯 System Architecture

### High-Level Design
```

┌─────────────────────────────────────────────┐
│              User Interface                  │
│  (MenuInterface.java - CLI Interface)       │
└────────────────┬────────────────────────────┘
│
┌────────────────▼────────────────────────────┐
│           Business Logic Layer               │
│  (ContactManager.java - Core Operations)    │
└────────────────┬────────────────────────────┘
│
┌────────────────▼────────────────────────────┐
│           Data Access Layer                  │
│  (Contact.java - Data Model)                │
│  (SearchEngine.java - Search Algorithms)    │
│  (ValidationUtils.java - Input Validation)  │
└─────────────────────────────────────────────┘

```

## 🏛️ Class Diagrams

### 1. Contact Class
```java
class Contact {
    // Attributes
    - int id
    - String name
    - String type
    - List<String> phoneNumbers
    
    // Methods
    + Contact(name, type, phoneNumber)
    + addPhoneNumber(number)
    + removePhoneNumber(number)
    + hasPhoneNumber(number)
    + containsName(searchTerm)
    + isNameSimilar(searchName)
    + toString()
}
```

2. ContactManager Class

```java
class ContactManager {
    // Attributes
    - List<Contact> contacts
    - ValidationUtils validator
    
    // Methods
    + addContact(name, type, phone, allowMultiple)
    + searchByName(name, useFuzzy)
    + searchByNumber(number)
    + deleteByName(name)
    + deleteByNumber(number)
    + getAllContacts()
}
```

3. MenuInterface Class

```java
class MenuInterface {
    // Attributes
    - Scanner scanner
    - ValidationUtils validator
    
    // Methods
    + displayMainMenu()
    + getMenuChoice()
    + getInput(prompt)
    + getContactType()
    + getPhoneNumber()
    + displaySearchResults(results)
    + displayAllContacts(contacts)
}
```

🔗 Class Relationships

Association

· AddressBookSystem uses ContactManager and MenuInterface
· ContactManager has Contact objects (1-to-many)
· ContactManager uses ValidationUtils and SearchEngine

Dependency

· MenuInterface depends on ValidationUtils for input validation
· SearchEngine operates on Contact objects

📊 Data Flow Diagram

```
┌─────────┐    Input    ┌─────────────┐   Process   ┌─────────────┐   Store   ┌──────────┐
│  User   │────────────►│  MenuInterface │───────────►│ ContactManager │──────────►│ Contacts  │
│         │◄────────────│              │◄───────────│              │◄──────────│          │
└─────────┘  Response   └─────────────┘   Results   └─────────────┘  Retrieve └──────────┘
```

🔍 Search Algorithm Design

1. Name Search (Standard)

```
Algorithm: searchByNameContains
Input: List<Contact> contacts, String searchTerm
Output: List<Contact> results

1. Initialize empty results list
2. For each contact in contacts:
   a. Convert contact name to lowercase
   b. Convert search term to lowercase
   c. If contact name contains search term:
      - Add contact to results
3. Return results
```

2. Fuzzy Name Search (Bonus)

```
Algorithm: searchByNameFuzzy
Input: List<Contact> contacts, String searchName
Output: List<Contact> results

1. Initialize empty results list
2. For each contact in contacts:
   a. If isSimilarName(contact.name, searchName) returns true:
      - Add contact to results
3. Return results

Function isSimilarName(name1, name2):
  1. If name1 equals name2: return true
  2. If name1 contains name2 or vice versa: return true
  3. Check common name variations (Arabic/English)
  4. Calculate similarity score using character matching
  5. Return true if similarity ≥ 75%
```

🔐 Validation Rules

1. Contact Name Validation

```java
Rules:
- Not null or empty
- Minimum 2 characters
- Only letters, spaces, hyphens, apostrophes allowed
- Maximum 50 characters
```

2. Phone Number Validation

```java
Rules:
- Not null or empty
- Only digits (0-9) allowed
- Length: 7-15 digits
- Not in reserved numbers list: [911, 112, 999, 100, 101]
- No duplicate numbers allowed (configurable)
```

3. Contact Type Validation

```java
Allowed Types:
1. "Family"
2. "Personal"
3. "Work"
4. "Other"

Default: "Other" if invalid type entered
```

💾 Data Storage Design

In-Memory Storage

```java
Data Structure: ArrayList<Contact>
Advantages:
- Fast access and retrieval
- Simple implementation
- No external dependencies
- Suitable for small datasets

Limitations:
- Data lost on program exit
- Limited scalability for large datasets
```

🎨 User Interface Design

Main Menu Layout

```
══════════════════════════════════════════
           ADDRESS BOOK SYSTEM
══════════════════════════════════════════
1. 📝 Add new contact
2. 🔍 Search by name
3. 📞 Search by number
4. 🗑️  Delete contact by name
5. 🗑️  Delete contact by number
6. 📋 Show all contacts
7. 🚪 Exit
══════════════════════════════════════════
```

Input/Output Design Principles

1. Consistency: Same format throughout application
2. Clarity: Clear prompts and messages
3. Feedback: Immediate feedback for user actions
4. Error Prevention: Validate before processing
5. Recovery: Graceful error handling

⚡ Performance Considerations

Time Complexity Analysis

Operation Time Complexity Description
Add Contact O(1) Direct addition to ArrayList
Search by Name O(n) Linear search through contacts
Search by Number O(n) Linear search with number check
Delete by Name O(n) Linear search and removal
Delete by Number O(n) Linear search and removal

Space Complexity

· Overall: O(n) where n = number of contacts
· Per Contact: O(1) for attributes + O(k) for phone numbers (k = numbers per contact)

🔧 Error Handling Strategy

1. Input Validation Errors

· Show specific error messages
· Allow retry without crashing
· Provide examples of valid input

2. Data Processing Errors

· Check for null values
· Handle empty data structures
· Validate business rules

3. System Errors

· Graceful exit on critical errors
· Preserve data integrity
· Clear error messages for debugging

📈 Scalability Considerations

Current Limitations

1. In-memory storage only
2. No persistence between sessions
3. Linear search algorithms
4. No multi-user support

Future Enhancement Possibilities

1. Database Integration: SQLite/MySQL for persistence
2. Indexed Search: HashMaps for faster lookups
3. File Storage: Save/load from JSON/XML files
4. Caching: Frequently accessed contacts cache

🔐 Security Considerations

Input Sanitization

1. Prevent SQL/command injection (future DB implementation)
2. Validate string lengths to prevent buffer issues
3. Escape special characters in display

Data Protection

1. Phone number validation prevents reserved numbers
2. Duplicate prevention maintains data integrity
3. Type validation ensures consistency

🧪 Testing Strategy

Unit Testing Scope

1. Contact class methods
2. Validation utilities
3. Search algorithms
4. Business logic in ContactManager

Integration Testing

1. End-to-end workflow testing
2. Menu navigation testing
3. Error scenario testing
4. Boundary condition testing

📝 Design Decisions Rationale

1. Why ArrayList instead of HashMap?

· Decision: Used ArrayList for contact storage
· Rationale:
  · Simpler implementation for beginners
  · Maintains insertion order
  · Easier to iterate and display
  · Sufficient for expected contact count (< 1000)

2. Why separate classes for each responsibility?

· Decision: Single Responsibility Principle (SRP)
· Rationale:
  · Easier to maintain and debug
  · Code reusability
  · Clear separation of concerns
  · Better testability

3. Why in-memory storage only?

· Decision: No file/database persistence
· Rationale:
  · Meets project requirements
  · Simpler for first programming project
  · Focus on core programming concepts
  · Can be extended in future versions

🏆 Achievement of Requirements

✅ Fully Implemented Requirements

1. All 7 menu options functional
2. Complete input validation
3. Reserved number prevention
4. Duplicate number prevention
5. Contact type restriction

✅ Bonus Features Implemented

1. Multiple numbers per contact
2. Fuzzy name matching algorithm
3. Professional error messages
4. Clean user interface

🔮 Future Design Improvements

Short-term Enhancements

1. Add file persistence
2. Implement sorting options
3. Add contact editing feature

Long-term Enhancements

1. GUI implementation
2. Database backend
3. Network synchronization
4. Mobile app version

---

Last Updated: February 2026
Version: 1.0
Author: [Your Name] - UCAS Programming Language 1
Status: Complete ✅

