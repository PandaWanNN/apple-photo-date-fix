package org.pandawannn;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.pandawannn.PhotoSaver.reSaveFiles;

class PhotoSaverTest {

    @Test
    void reSaveFilesTest() {
        String pathToXmp = "/xxx/xxx/xxx/xxx/IMG_2811.xmp";
        reSaveFiles(new File(pathToXmp), LocalDateTime.parse("2019-07-15T10:15:30"));
    }
}