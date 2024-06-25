package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
class DependencyParser {

    public static List<Dependency> parsePomFile(String filePath) {
        List<Dependency> dependencies = new ArrayList<>();

        try {
            var factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            NodeList dependencyNodes = document.getElementsByTagName("dependency");

            for (int i = 0; i < dependencyNodes.getLength(); i++) {
                Node node = dependencyNodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    var element = (Element) node;
                    String groupId = element.getElementsByTagName("groupId").item(0).getTextContent();
                    String artifactId = element.getElementsByTagName("artifactId").item(0).getTextContent();
                    String version = element.getElementsByTagName("version").item(0).getTextContent();

                    dependencies.add(new Dependency(groupId, artifactId, version));
                }
            }
        } catch (Exception e) {
            log.error("Failed to parse POM file: {}", filePath, e);
        }

        return dependencies;
    }
}
