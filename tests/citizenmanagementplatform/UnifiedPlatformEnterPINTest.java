package citizenmanagementplatform;

import data.Nif;
import data.SmallCode;
import exceptions.NotValidPINException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CertificationAuthority;
import services.CertificationAuthorityDouble;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformEnterPINTest {

    private UnifiedPlatform UP;
    private CertificationAuthority certAuth;

    private Nif registeredNif;
    private Date validNifDate;
    private SmallCode invalidPIN;


    @BeforeEach
    void setUp() throws Throwable {
        // Certification Authority Info
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2023, Calendar.JANUARY, 1);
        validNifDate = c.getTime();
        registeredNif = new Nif("11111111A");
        invalidPIN = new SmallCode("000");

        // Double instances
        certAuth = new CertificationAuthorityDouble(false);

        UP = new UnifiedPlatform();
        // Inject dependences
        UP.setCertAuth(certAuth);
        prepareUseCase();
    }

    private void prepareUseCase() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(registeredNif, validNifDate);
    }

    @Test
    void checkNotValidPINException() {
        assertThrows(NotValidPINException.class, () -> UP.enterPIN(invalidPIN));
    }
}
