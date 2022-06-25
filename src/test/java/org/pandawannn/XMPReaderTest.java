package org.pandawannn;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.pandawannn.XMPReader.getDate;

class XMPReaderTest {

    @Test
    void getDateXMP() {
        String pathToXmp = "/xxx/xxx/xxx/xxx/IMG_2811.xmp";
        LocalDateTime date = getDate(new File(pathToXmp));
        System.out.println(date);
    }
}