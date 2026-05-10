# Selenium UI Automation Framework

A professional-grade automated testing suite built with **Java** and **Selenium WebDriver**. This framework implements the **Page Object Model (POM)** design pattern to ensure scalable, maintainable, and robust test scripts.

---

## Technology Stack
* **Language:** Java 11+
* **Automation:** Selenium WebDriver
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **Reporting:** Allure Report

---

## Framework Architecture

This project is designed with a focus on modularity and reusability through the following implementation standards:

### Page Object Model (POM)
The framework maintains a strict separation between test logic and UI-specific locators. Each web page is represented by a dedicated Java class, ensuring:
* **Encapsulated Locators:** Elements are defined using centralized `By` selectors to minimize maintenance efforts when the UI changes.
* **Reusable Functional Methods:** Page classes contain methods that model user actions, allowing for consistent behavior across multiple test cases.
* **Integrated Assertions:** Pages include self-validating methods that perform hard assertions, keeping the test layer focused on execution flow.

---

## Test Execution & Reporting

To execute the automation suite and view the interactive results, follow these steps in your terminal:

### Execute and Report
1. **Run Tests:** Execute the command to clean the environment and run the TestNG suite.
2. **View Report:** Follow up with the Allure command to launch the visual dashboard.

```bash
# Run the test suite
mvn clean test

# Generate and serve the Allure report
allure serve test-outputs/allure-results