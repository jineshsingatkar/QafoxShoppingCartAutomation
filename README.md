# Qafox Shopping Cart Automation

This project contains automated tests for the Qafox shopping cart functionality using Selenium WebDriver with Java and TestNG framework.

## Project Overview

The automation framework is designed to test the shopping cart functionality of the Qafox e-commerce website. It implements the Page Object Model design pattern and includes robust wait mechanisms for better test stability.

### Key Features

- Page Object Model implementation
- Explicit waits for better stability
- Cross-browser testing support
- Maven for dependency management
- TestNG for test execution and reporting
- Comprehensive logging

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome browser (latest version)
- ChromeDriver (compatible with your Chrome version)

## Project Structure

```
QafoxShoppingCartAutomation/
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── qafox/
│                   ├── base/
│                   │   └── BaseTest.java
│                   ├── pages/
│                   │   ├── HomePage.java
│                   │   └── ShoppingCartPage.java
│                   └── tests/
│                       └── ShoppingCartTest.java
├── pom.xml
├── testng.xml
└── README.md
```

## Dependencies

The project uses the following main dependencies:

- Selenium WebDriver 4.16.1
- TestNG 7.8.0
- WebDriverManager 5.6.2

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd QafoxShoppingCartAutomation
   ```

2. Install dependencies using Maven:
   ```bash
   mvn clean install
   ```

3. Ensure Chrome browser is installed on your system

4. Run the tests:
   ```bash
   mvn clean test
   ```

## Test Cases

The current test suite includes:

1. Shopping Cart Functionality Test
   - Search for MacBook
   - Add MacBook to cart with quantity 2
   - Validate success message
   - Open shopping cart
   - Validate cart URL
   - Validate cart items and pricing

## Page Objects

### HomePage
- Handles search functionality
- Manages product selection
- Controls cart interactions
- Validates success messages

### ShoppingCartPage
- Validates cart contents
- Verifies pricing calculations
- Checks quantity inputs
- Ensures proper page loading

## Best Practices Implemented

- Page Object Model design pattern
- Explicit waits for better stability
- Proper error handling and assertions
- Clean code structure
- Comprehensive logging
- Cross-browser support

## Troubleshooting

If you encounter any issues:

1. Ensure Chrome browser and ChromeDriver versions match
2. Check if all dependencies are properly installed
3. Verify network connectivity
4. Check the test logs for detailed error messages

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

 