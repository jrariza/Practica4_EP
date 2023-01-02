package data;

import exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmallCodeTest {

    @Test
    @SuppressWarnings("ConstantConditions")
    void CheckNullException() {
        assertThrows(NullPointerException.class, () -> new SmallCode(null));
    }

    @Test
    void CheckCodeLengthSmallerThan3() {
        assertThrows(InvalidFormatException.class, () -> new SmallCode("0"));
    }

    @Test
    void CheckCodeLengthBiggerThan3() {
        assertThrows(InvalidFormatException.class, () -> new SmallCode("0123"));
    }

    @Test
    void CheckCodeWithLettersIsInvalidFormat() {
        assertThrows(InvalidFormatException.class, () -> new SmallCode("abc"));
    }

    @Test
    void CheckCodeWithSymbolsIsInvalidFormat() {
        assertThrows(InvalidFormatException.class, () -> new SmallCode("$/~"));
    }

    @Test
    void CheckCodeWithOnlyNumbersIsValidFormat() {
        StringBuilder code = new StringBuilder();
        Random r = new Random();
        for (int j = 0; j < 3; j++) code.append(r.nextInt(10));
        assertDoesNotThrow(() -> new SmallCode(code.toString()));
    }
}