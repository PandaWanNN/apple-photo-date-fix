package org.pandawannn;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PhotoSaver {

    public static void reSaveFiles(File xmpFile, LocalDateTime date) {
        String pathWithoutExtension = FilenameUtils.removeExtension(xmpFile.getAbsolutePath());
        resetTimes(new File(pathWithoutExtension + ".jpg"), date);
        File livePhoto = new File(pathWithoutExtension + ".mov");
        if (livePhoto.exists()) {
            resetTimes(livePhoto, date);
        }
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
