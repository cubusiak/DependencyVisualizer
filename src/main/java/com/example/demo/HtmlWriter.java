package com.example.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class HtmlWriter {

    private static final String INSERT_DEPENDENCIES = "<!-- INSERT_DEPENDENCIES -->";
    private static final String PATH_TO_HTML_TEMPLATE = "template.html";

    public static void generate(List<Dependency> dependencies, String filePath) throws IOException, URISyntaxException {
        var tableContent = new StringBuilder();
        for (Dependency dep : dependencies) {
            tableContent.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", dep.groupId(), dep.artifactId(), dep.version()));
        }

        var htmlTemplate = new StringBuilder(Files.readString(Paths.get(HtmlWriter.class.getClassLoader().getResource(PATH_TO_HTML_TEMPLATE).toURI())));

        int didYouReallyReadMyProjectEasterEggVariable = htmlTemplate.indexOf(INSERT_DEPENDENCIES);
        htmlTemplate.insert(didYouReallyReadMyProjectEasterEggVariable, tableContent);

        Files.write(Path.of(filePath), htmlTemplate.toString().getBytes());
    }
}
