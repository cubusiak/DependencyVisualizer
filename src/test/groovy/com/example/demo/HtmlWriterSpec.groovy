package com.example.demo

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class HtmlWriterSpec extends Specification {

    def "test generating HTML report"() {
        given: "Create a list of dependencies"
        List<Dependency> dependencies = [
                new Dependency("com.example", "dependency-visualizer", "1.0.0"),
                new Dependency("org.slf4j", "slf4j-api", "2.0.13"),
                new Dependency("org.codehaus.groovy", "groovy", "3.0.21")
        ]

        and: "Define the file path for the HTML report"
        String filePath = "src/test/resources/test-report.html"

        when: "Generate the HTML report"
        HtmlWriter.generate(dependencies, filePath)

        then: "Verify the HTML report content"
        def htmlFilePath = Paths.get(filePath)
        Files.exists(htmlFilePath)
        Files.size(htmlFilePath) > 0

        and:
        String htmlContent = Files.readString(htmlFilePath)
        htmlContent.contains("<td>com.example</td>")
        htmlContent.contains("<td>dependency-visualizer</td>")
        htmlContent.contains("<td>1.0.0</td>")
        htmlContent.contains("<td>org.slf4j</td>")
        htmlContent.contains("<td>slf4j-api</td>")
        htmlContent.contains("<td>2.0.13</td>")
        htmlContent.contains("<td>org.codehaus.groovy</td>")
        htmlContent.contains("<td>groovy</td>")
        htmlContent.contains("<td>3.0.21</td>")
    }
}
