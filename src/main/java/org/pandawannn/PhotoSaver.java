package org.pandawannn;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhotoSaver {

    public static void reSaveFiles(File xmpFile, LocalDateTime date) {
        String pathWithoutExtension = FilenameUtils.removeExtension(xmpFile.getAbsolutePath());
        findMediaFile(pathWithoutExtension).forEach(file -> resetTimes(file, date));
    }

    private static List<File> findMediaFile(String pathWithoutExtension) {
        return Stream.of(".jpg", ".jpeg", ".mp4", ".mov")
                .filter(extension -> new File(pathWithoutExtension + extension).exists())
                .map(ext -> new File(pathWithoutExtension + ext))
                .collect(Collectors.toList());
    }

    private static void resetTimes(File file, LocalDateTime date) {
        try {
            BasicFileAttributeView attributes = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class);
            FileTime time = FileTime.from(date.toInstant(ZoneOffset.UTC));
            attributes.setTimes(time, time, time);
        } catch (IOException e) {
            throw new RuntimeException("Could not set new date: " + file, e);
        }
    }
}
