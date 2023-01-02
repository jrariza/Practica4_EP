package data;

import exceptions.NullParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureTest {

    @Test
    void checkNullException() {
        assertThrows(NullParameterException.class, () -> new DigitalSignature(null));
    }

    @Test
    void checkCorrectCase(){
        assertDoesNotThrow(()-> new DigitalSignature("123".getBytes()));
    }
}