# Dependency Visualizer

Dependency Visualizer is a tool for analyzing and visualizing dependencies in a Maven project. It uses the `pom.xml`
files to generate a list of dependencies, and then creates an HTML reports with this list and csv files.

## Technologies

- Java 21
- Maven
- Groovy
- Spock Framework

## Features

- Parsing the `pom.xml` files to extract dependencies.
- If the pom file is not found in the input directory, the program will take itself pom file as input.
- Writing the list of dependencies to a csv file.
- Generating an HTML report with the list of dependencies.

## Project Structure

- `pom_example.xml`: An example `pom.xml` file used for testing.
- `input directory`: Contains the `pom.xml` files to be analyzed.
- `output directory`: Contains the generated HTML reports and CSV files.
- `src/main/java`: Contains the main classes of the project.
- `src/test/groovy`: Contains the tests for the project.

## How to Use

1. Build the project using Maven.
2. Use Java 21 to run the project.
3. Start the program by running the `Main` class.