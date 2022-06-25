package org.pandawannn;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class XMPReader {

    public static LocalDateTime getDate(File file) {
        String dateString = getDateString(file);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return LocalDateTime.from(zonedDateTime);
    }

    private static String getDateString(File file) {
        try {
            Document document = getDocument(file);
            NodeList nodes = document.getFirstChild().getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeName().equals("photoshop:DateCreated")) {
                    return nodes.item(i).getTextContent();
                }
            }
            throw new RuntimeException("Could not find date: " + file.getAbsolutePath());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("Error while reading xmp file: " + file.getAbsolutePath(), e);
        }
    }

    private static Document getDocument(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        return doc;
    }
}
