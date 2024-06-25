package com.example.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class CsvWriter {

    public static void save(List<Dependency> dependencies, String filePath) throws IOException {
        try (var writer = new FileWriter(filePath)) {
            writer.write("Group ID;Artifact ID;Version\n");
            for (Dependency dep : dependencies) {
                writer.write(String.format("%s;%s;%s\n", dep.groupId(), dep.artifactId(), dep.version()));
            }
        }
    }
}
