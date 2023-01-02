package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DocPathTest {

    @Test
    public void samePath() throws Exception {
        String tmp_path = "file.txt";

        DocPath tmp_d = new DocPath(tmp_path);

        Assertions.assertEquals(tmp_path, tmp_d.getDocPath());
    }
}
