package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DigitalSignatureTest {

    @Test
    void CheckNullException() {
        assertThrows(NullPointerException.class, () -> new DigitalSignature(null));
    }

}