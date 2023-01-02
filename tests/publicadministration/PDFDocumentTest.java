package publicadministration;

import data.DocPath;
import exceptions.BadPathException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PDFDocumentTest {
    private static final String validDefPathDir = PDFDocument.getDefaultPathDir();
    private static final String validDownPath = PDFDocument.getDownloadPath();

    private static final String invalidDefPathDir = "invalid";
    private static final String invalidDownPath = "invalid";

    private static final DocPath validDest = new DocPath("./path_new");

    @BeforeEach
    void setValidPaths(){
        PDFDocument.setDefaultPathDir(validDefPathDir);
        PDFDocument.setDownloadPath(validDownPath);
    }

    @Test
    void nullDefPathDir() {
        PDFDocument.setDefaultPathDir(null);
        assertThrows(NullPointerException.class, () -> new PDFDocument());
    }

    @Test
    void nullDownPath() {
        PDFDocument.setDownloadPath(null);
        assertThrows(NullPointerException.class, () -> new PDFDocument());
    }

    @Test
    void invalidDefPathDir() {
        PDFDocument.setDefaultPathDir(invalidDefPathDir);
        assertThrows(BadPathException.class, () -> new PDFDocument());
    }

    @Test
    void invalidDownPath() {
        PDFDocument.setDownloadPath(invalidDownPath);
        assertThrows(BadPathException.class, () -> new PDFDocument());
    }

    @Test
    void validPaths() throws IOException, BadPathException {
        assertDoesNotThrow(() -> new PDFDocument());
    }

    @Test
    void moveDocTestInvalidDest() throws IOException, BadPathException {
        PDFDocument doc = new PDFDocument();
        assertThrows(BadPathException.class, () -> doc.moveDoc(new DocPath("invalid"), "name"));
    }

    @Test
    void moveDocCorrectTest() throws IOException, BadPathException {
        PDFDocument doc = new PDFDocument();
        assertDoesNotThrow(() -> doc.moveDoc(validDest, "name.pdf"));
    }

    @Test
    void openDocTestNotExistingFile() throws IOException, BadPathException {
        PDFDocument doc = new PDFDocument();
        doc.getFile().delete();
        assertThrows(BadPathException.class, () -> doc.openDoc());
    }

    @Test
    void openDocCorrectTest() throws IOException, BadPathException {
        PDFDocument doc = new PDFDocument();
        assertDoesNotThrow(() -> doc.openDoc());
    }
}