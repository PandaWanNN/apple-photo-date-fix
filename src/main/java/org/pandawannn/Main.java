package org.pandawannn;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.pandawannn.PhotoSaver.reSaveFiles;
import static org.pandawannn.XMPReader.getDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");
        String path = args[0];
        if (!new File(path).exists()) {
            throw new RuntimeException("unknown path: " + path);
        }
        readFiles(reSavePhoto(), path);
        System.out.println("Pandathastisch!!!");
    }

    private static void readFiles(Consumer<File> consumer, String folder) {
        try (Stream<Path> list = Files.list(Path.of(folder))) {
            list.filter(Main::isXmp).forEach(path -> consumer.accept(path.toFile()));
        } catch (IOException e) {
            throw new RuntimeException("Could not scan folder", e);
        }
    }

    private static boolean isXmp(Path path) {
        return FilenameUtils.getExtension(path.getFileName().toString()).equals("xmp");
    }

    private static Consumer<File> reSavePhoto() {
        return file -> {
            System.out.println("Process: " + file.getAbsolutePath());
            LocalDateTime date = getDate(file);
            reSaveFiles(file, date);
            if (!file.delete()) {
                throw new RuntimeException("can not delete xmp file: " + file.getAbsolutePath());
            }
        };
    }
}