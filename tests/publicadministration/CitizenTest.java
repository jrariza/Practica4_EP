package publicadministration;

import data.Nif;
import exceptions.NotValidNifCodeException;
import exceptions.NullParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CitizenTest {

    private Citizen c;
    private Nif nif;
    private String name;
    private String address;
    private String mobile;

    @BeforeEach
    void setUp() throws NullParameterException, NotValidNifCodeException {
        nif = new Nif("11111111A");
        name = "Pepito";
        address = "C/Prueba";
        mobile = "123456789";
        c = new Citizen(nif, name, address, mobile);
    }

    @Test
    void testToString() {
        String citizenToString = "Citizen{" +
                "nif=" + nif +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobileNumb='" + mobile + '\'' +
                '}';
        assertEquals(citizenToString, c.toString());
    }
}