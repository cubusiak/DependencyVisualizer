package com.example.demo

import spock.lang.Specification

class CsvWriterSpec extends Specification {

    def "test saving dependencies to CSV file"() {
        given: "A list of dependencies and a file path"
        List<Dependency> dependencies = [
                new Dependency("com.example", "dependency-visualizer", "1.0.0"),
                new Dependency("org.slf4j", "slf4j-api", "2.0.13"),
                new Dependency("org.codehaus.groovy", "groovy", "3.0.21")
        ]
        String filePath = "src/test/resources/test.csv"

        when: "Save the dependencies to the CSV file"
        CsvWriter.save(dependencies, filePath)

        then: "Verify the content of the CSV file"
        def lines = new File(filePath).readLines()
        lines.size() == 4
        lines[0] == "Group ID;Artifact ID;Version"
        lines[1] == "com.example;dependency-visualizer;1.0.0"
        lines[2] == "org.slf4j;slf4j-api;2.0.13"
        lines[3] == "org.codehaus.groovy;groovy;3.0.21"
    }
}