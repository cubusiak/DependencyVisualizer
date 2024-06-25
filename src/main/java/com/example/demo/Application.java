package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Application {

    public static Path INPUT_DIRECTORY = Path.of("input");
    public static Path OUTPUT_DIRECTORY = Path.of("output");

    public static void main(String[] args) {
        try {
            Files.createDirectories(INPUT_DIRECTORY);
            Files.createDirectories(OUTPUT_DIRECTORY);

            List<Path> xmlFiles = getXmlFiles();
            if (xmlFiles.isEmpty()) {
                log.error("No XML files found in the input directory. Analyzing self POM file.");
                analyseSelfPom(xmlFiles);
            }

            for (Path xmlFile : xmlFiles) {
                List<Dependency> dependencies = DependencyParser.parsePomFile(xmlFile.toString());
                String baseFileName = xmlFile.getFileName().toString().replace(".xml", "");
                createCsv(baseFileName, dependencies);
                createHtml(baseFileName, dependencies);
            }
            log.info("Application finished successfully");
        } catch (IOException | URISyntaxException e) {
            log.error("Error processing files", e);
        }
    }

    private static List<Path> getXmlFiles() throws IOException {
        return Files.list(INPUT_DIRECTORY)
                .filter(path -> path.toString().endsWith(".xml"))
                .collect(Collectors.toList());
    }

    private static void analyseSelfPom(List<Path> xmlFiles) {
        xmlFiles.add(Path.of("pom.xml"));
    }

    private static void createCsv(String baseFileName, List<Dependency> dependencies) throws IOException {
        String csvOutputFilePath = OUTPUT_DIRECTORY.resolve(baseFileName + "_report.csv").toString();
        CsvWriter.save(dependencies, csvOutputFilePath);
        log.info("CSV report generated: {}", csvOutputFilePath);
    }

    private static void createHtml(String baseFileName, List<Dependency> dependencies) throws IOException, URISyntaxException {
        String htmlOutputFilePath = OUTPUT_DIRECTORY.resolve(baseFileName + "_report.html").toString();
        HtmlWriter.generate(dependencies, htmlOutputFilePath);
        log.info("HTML report generated: {}", htmlOutputFilePath);
    }
}
