# Address-Book-System
comprehensive Java contact management application developed for Programming Language 1 course at University College of Applied Sciences. Features include contact CRUD operations, search functionality, input validation, and bonus features like multiple numbers per contact and fuzzy name matching
---
# Address Book System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)
![UCAS](https://img.shields.io/badge/UCAS-Academic%20Project-red)
![Version](https://img.shields.io/badge/Version-1.0.0-blue)

**A Professional Console-Based Contact Management Application**

<br/>

<!-- Replace the link below with your actual GIF file -->
<img src="docs/demo.gif" alt="Address Book System Demo" width="700"/>

</div>

---

## Overview

The **Address Book System** is a Java-based console application developed as an academic project for the **Programming Language 1** course at **University College of Applied Sciences (UCAS)**.

The system provides full CRUD functionality for managing contacts, with strong input validation and additional bonus features to enhance usability and robustness.

---

## Academic Information

- **Course:** Programming Language 1  
- **University:** University College of Applied Sciences (UCAS)  
- **Faculty:** Engineering and Smart Systems  
- **Department:** Computer Engineering  
- **Major:** Cybersecurity Engineering  
- **Semester:** First Semester 2025/2026  
- **Submission Deadline:** 26 February 2026  
- **Discussion Date:** 5 March 2026  

---

## Quick Start

```bash
# Clone the repository
git clone https://github.com/yourusername/address-book-system.git

# Compile and run
cd address-book-system
javac src/AddressBookSystem.java
java src.AddressBookSystem
```
## Features

### Core Functions
- Add new contact (name, type, phone number)
- Search contact by name (partial matching)
- Search contact by phone number (exact match)
- Delete contact by name
- Delete contact by phone number
- Display all stored contacts
- Exit the system safely

### Validation & Constraints
- Allowed contact types:
  - Family
  - Personal
  - Work
  - Other
- Reserved emergency numbers are not allowed:
  - 911
  - 112
  - 999
  - 100
  - 101
- Duplicate phone numbers are prevented
- User-friendly error handling and input validation

### Bonus Features
- Support for **multiple phone numbers per contact**
- **Similar name search** using flexible string matching

## Team

- **Bayan Saleh Ibrahim Abu Shawish**  
  University ID: 220259028  
  Role: Student / Main Developer  

- **Sarah Abu Mandeel**  
  Role: Technical Mentor & Code Reviewer  

**Academic Advisor:**  
- **Dr. Sana’a Wafa Tawfiq Al-Sayegh**  
  Department of Cybersecurity Engineering – UCAS  

---

## Technical Details

- **Programming Language:** Java (JDK 8+)
- **Application Type:** Console-based
- **Data Structure:** ArrayList
- **Search Method:** Linear search with string matching
- **Validation:** Manual input validation
- **Design Approach:** Modular and readable code structure

---

## Testing

The system was tested for:
- All menu options (1–7)
- Valid and invalid user inputs
- Duplicate entries
- Reserved numbers
- Edge cases and error recovery
- Bonus feature functionality

---

## Notes

- File naming format: `StudentName_UniversityID.java`
- Group and student details are stored in `docs/group.txt`
- Code originality is strictly respected
- Bonus features contribute to higher evaluation marks

---

## License

This project is licensed under the **MIT License**.  
See the `LICENSE` file for more details.

---

<div align="center">

Developed for UCAS – Programming Language 1  
*"Good code is clean, functional, and well-documented."*

</div>
