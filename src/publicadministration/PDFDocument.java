package publicadministration;

import data.DocPath;
import exceptions.BadPathException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class PDFDocument {
    // Represents a PDF document
    private static String defaultPathDir = "./path_tmp/";
    private static String downloadPath = "./certf_database/certf.pdf";
    private static int docNum;

    private final Date creatDate;
    private DocPath path;
    private File file;


    public PDFDocument() throws IOException, BadPathException {
        // Initializes attributes and emulates the document download at a defaut path
        if (areInvalidPaths(defaultPathDir, downloadPath)) throw new BadPathException();

        String docName = "certf" + Integer.toString(++docNum) + ".pdf";
        Path defPath = Paths.get(defaultPathDir, docName);
        Path downPath = Paths.get(downloadPath);

        Files.copy(downPath, defPath, StandardCopyOption.REPLACE_EXISTING);

        this.creatDate = new Date();
        setPathAndFile(defaultPathDir, docName);
    }

    private boolean areInvalidPaths(String src, String destDir) throws BadPathException {
        // Checks if source path and destination directory for file download are valid paths
        return Files.notExists(Paths.get(src)) || Files.notExists(Paths.get(destDir));
    }

    private void setPathAndFile(String dir, String docName) {
        this.path = new DocPath(dir + docName);
        this.file = new File(path.getDocPath());
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public DocPath getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public static String getDefaultPathDir() {
        return defaultPathDir;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

    public static void setDefaultPathDir(String defaultPathDir) {
        PDFDocument.defaultPathDir = defaultPathDir;
    }

    public static void setDownloadPath(String downloadPath) {
        PDFDocument.downloadPath = downloadPath;
    }


    // To implement only optionally
    public void moveDoc(DocPath destPath, String docName) throws IOException, BadPathException {
        // Moves the document to the destination path indicated
        String src = path.getDocPath();
        String destDir = destPath.getDocPath();

        if (areInvalidPaths(src, destDir)) throw new BadPathException();

        Files.move(Paths.get(src), Paths.get(destDir, docName), StandardCopyOption.REPLACE_EXISTING);
        setPathAndFile(destDir, docName);
    }


    public void openDoc() throws IOException, BadPathException {
        // Opens the document
        Path p = Paths.get(path.getDocPath());
        if (Files.notExists(p)) throw new BadPathException();

        Desktop.getDesktop().open(this.file);
    }


    @Override
    public String toString() {
        return "PDFDocument{" +
                "creatDate=" + creatDate +
                ", path=" + path +
                '}';
    }
}