package citizenmanagementplatform;

import data.Nif;
import exceptions.AnyMobileRegisteredException;
import exceptions.IncorrectValDateException;
import exceptions.NifNotRegisteredException;
import exceptions.ProceduralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CertificationAuthority;
import services.CertificationAuthorityDouble;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformEnterNIFandPINobtTest {

    private UnifiedPlatform UP;
    private Nif notRegisteredNif;
    private Nif registeredNif;
    private Nif nifWithoutMobile;
    private Date validNifDate;
    private Date invalidNifDate;

    @BeforeEach
    void setUp() throws Throwable {
        // Certification Authority Info
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2050, Calendar.JANUARY, 1);
        invalidNifDate = c.getTime();
        c.set(2023, Calendar.JANUARY, 1);
        validNifDate = c.getTime();
        notRegisteredNif = new Nif("12345678A");
        registeredNif = new Nif("11111111A");
        nifWithoutMobile = new Nif("22222222B");

        // Double instances
        CertificationAuthority certAuth = new CertificationAuthorityDouble(false);

        UP = new UnifiedPlatform();
        // Inject dependences
        UP.setCertAuth(certAuth);
        prepareUseCase();
    }

    private void prepareUseCase() throws ProceduralException {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
    }

    @Test
    void checkNifNotRegisteredException() {
        assertThrows(NifNotRegisteredException.class, () -> UP.enterNIFandPINobt(notRegisteredNif, null));
        assertNull(UP.getCitizen());
    }

    @Test
    void checkIncorrectValDateException() {
        assertThrows(IncorrectValDateException.class, () -> UP.enterNIFandPINobt(registeredNif, invalidNifDate));
        assertNull(UP.getCitizen());
    }

    @Test
    void checkAnyMobileRegisteredException() {
        assertThrows(AnyMobileRegisteredException.class, () -> UP.enterNIFandPINobt(nifWithoutMobile, validNifDate));
        assertNull(UP.getCitizen());
    }
}
