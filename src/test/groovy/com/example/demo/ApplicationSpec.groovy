package com.example.demo

import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

import static com.example.demo.Application.*

class ApplicationSpec extends Specification {

    @TempDir
    Path tempDir

    def setup() {
        INPUT_DIRECTORY = tempDir.resolve("input")
        OUTPUT_DIRECTORY = tempDir.resolve("output")
    }

    def "should create output directories and process XML files"() {
        given: "An XML file in the input directory"
        def xmlContent = '''<project>
                                <dependencies>
                                    <dependency>
                                        <groupId>com.example</groupId>
                                        <artifactId>example-artifact</artifactId>
                                        <version>1.0.0</version>
                                    </dependency>
                                </dependencies>
                            </project>'''
        def xmlFile = INPUT_DIRECTORY.resolve("test.xml")
        Files.createDirectories(INPUT_DIRECTORY)
        Files.writeString(xmlFile, xmlContent)

        when: "The application runs"
        main([] as String)

        then: "The output directories are created"
        Files.exists(OUTPUT_DIRECTORY)

        and: "CSV report is generated"
        def csvFile = OUTPUT_DIRECTORY.resolve("test_report.csv")
        Files.exists(csvFile)
        def csvContent = Files.readString(csvFile)
        csvContent.contains("com.example;example-artifact;1.0.0")

        and: "HTML report is generated"
        def htmlFile = OUTPUT_DIRECTORY.resolve("test_report.html")
        Files.exists(htmlFile)
        def htmlContent = Files.readString(htmlFile)
        htmlContent.contains("<td>com.example</td>")
        htmlContent.contains("<td>example-artifact</td>")
        htmlContent.contains("<td>1.0.0</td>")
    }

    def "should analyze self POM file when no XML files in input directory"() {
        given: "No XML files in the input directory"

        when: "The application runs"
        main([] as String)

        then: "Self POM file is analyzed"
        def logOutput = new ByteArrayOutputStream()
        System.setErr(new PrintStream(logOutput))
        def csvFile = OUTPUT_DIRECTORY.resolve("pom_report.csv")
        Files.exists(csvFile)
    }
}
