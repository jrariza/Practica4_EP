package data;

import exceptions.NullParameterException;

import java.util.Objects;

final public class DocPath {
    private final String docPath;

    public DocPath(String path) {
        docPath = path;
    }

    public String getDocPath() {
        return docPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocPath docPath1 = (DocPath) o;
        return docPath.equals(docPath1.docPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docPath);
    }

    @Override
    public String toString() {
        return "DocPath{" +
                "docPath='" + docPath + '\'' +
                '}';
    }
}
