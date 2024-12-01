package de.huege.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Reader {

    public static List<String> readAllLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(String.format("src/main/resources/inputs/%s", filename)));
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + filename);
        }
        return List.of();
    }

    public static List<String> readAllLinesForDay(int day, boolean isExample) {
        return isExample ? readAllLines("day" + day + "-example.txt") : readAllLines("day" + day + ".txt");

    }
}
