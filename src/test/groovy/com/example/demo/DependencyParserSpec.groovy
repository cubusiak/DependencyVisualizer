package com.example.demo

import spock.lang.Specification

class DependencyParserSpec extends Specification {

    def "test parsing valid pom.xml file"() {
        given: "Get pom.xml file for testing"
        URL resourceUrl = DependencyParserSpec.class.getResource("/test-pom.xml")
        def resourceFile = new File(resourceUrl.toURI())

        when: "Parse the pom.xml file"
        List<Dependency> dependencies = DependencyParser.parsePomFile(resourceFile.getAbsolutePath())

        then: "Verify the parsed dependencies"
        dependencies.size() == 3

        and:
        dependencies[0].groupId == "com.example"
        dependencies[0].artifactId == "dependency-visualizer"
        dependencies[0].version == "1.0.0"

        and:
        dependencies[1].groupId == "org.slf4j"
        dependencies[1].artifactId == "slf4j-api"
        dependencies[1].version == "2.0.13"

        and:
        dependencies[2].groupId == "org.codehaus.groovy"
        dependencies[2].artifactId == "groovy"
        dependencies[2].version == "3.0.21"
    }
}
