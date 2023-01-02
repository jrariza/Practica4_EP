package publicadministration;

import data.Nif;
import exceptions.NotValidNifCodeException;
import exceptions.NotValidPaymentImportException;
import exceptions.NullParameterException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardPaymentTest {
    private Nif nif;
    private BigDecimal validImport;

    @BeforeEach
    void setupVariables() throws NullParameterException, NotValidNifCodeException {
        nif = new Nif("12345678A");
        validImport = new BigDecimal("10");
    }

    @Test
    void nullNifTest() {
        assertThrows(NullParameterException.class, () -> new CardPayment(null, validImport));
    }

    @Test
    void nullImportTest() {
        assertThrows(NullParameterException.class, () -> new CardPayment(nif, null));
    }

    @Test
    void negativeImportTest() {
        assertThrows(NotValidPaymentImportException.class, () -> new CardPayment(nif, new BigDecimal("-1")));
    }

    @Test
    void validZeroImportTest() {
        assertDoesNotThrow(() -> new CardPayment(nif, BigDecimal.ZERO));
    }

    @Test
    void validCardPayment() {
        assertDoesNotThrow(() -> new CardPayment(nif, validImport));
    }
}
