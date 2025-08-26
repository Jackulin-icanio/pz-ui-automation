UI Automation Project
This project is a UI automation framework built using Playwright WebDriver, TestNG, and Maven for automated testing of web applications.

Project Structure
├── src/                   # Source code directory
├── suite/                 # Test suite configurations
├── Screenshots/           # Screenshots captured during test execution
├── target/                # Compiled classes and test reports
├── test-output/           # TestNG reports
├── pom.xml                # Maven project configuration
└── testng.xml             # TestNG suite configuration
Prerequisites
Java JDK 21
Maven 3.14.0 or higher
Chrome/Firefox browser
Git
Setup Instructions
Clone the repository:

git clone [repository-url]
Navigate to the project directory:

cd UI-Automation
Install dependencies:

mvn clean install
Running Tests
To run the tests, use the following command:

mvn clean test

To run a specific test suite:

mvn test -DsuiteXmlFile=suite/[suite-name].xml

Test Reports
After test execution, reports can be found in:

target/surefire-reports/ - Surefire test reports
test-output/ - TestNG reports
Configuration
Test configurations are managed in testng.xml
Browser settings and other configurations can be modified in the respective configuration files
Dependencies
The project uses the following main dependencies:

playwright WebDriver - 1.51.0
TestNG
Maven
WebDriverManager
Contributing
Create a new branch for your feature
Make your changes
Submit a pull request