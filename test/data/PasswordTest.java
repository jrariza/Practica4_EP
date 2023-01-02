package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void samePassword() {
        String tmp_passwd = "secret";

        Password tmp_p = new Password(tmp_passwd);

        Assertions.assertEquals(tmp_passwd, tmp_p.getPassword());
    }
}
