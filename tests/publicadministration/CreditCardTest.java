package publicadministration;

import data.Nif;
import data.SmallCode;
import exceptions.InvalidFormatException;
import exceptions.NotValidNifCodeException;
import exceptions.NullParameterException;
import exceptions.OutdatedCardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class CreditCardTest {

    private Nif nif;
    private String cNum;
    private Date expirDate;
    private SmallCode svc;

    @BeforeEach
    void setUp() throws NullParameterException, NotValidNifCodeException, InvalidFormatException {
        nif = new Nif("11111111A");
        cNum = "1234123412341234";
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2000, Calendar.JANUARY, 1);
        expirDate = c.getTime();
        svc = new SmallCode("123");
    }

    @Test
    public void checkOutdatedCardException() {
        Assertions.assertThrows(OutdatedCardException.class, () -> new CreditCard(nif, cNum, expirDate, svc));
    }
}
