package data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DocPathTest {

    @Test
    public void correctUseTest() {
        assertDoesNotThrow(()-> new DocPath("./text.txt"));
    }
}
