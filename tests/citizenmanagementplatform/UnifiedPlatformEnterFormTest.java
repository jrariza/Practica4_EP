package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import data.goalTypes;
import exceptions.IncompleteFormException;
import exceptions.IncorrectVerificationException;
import exceptions.NotMatchingNifsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.Citizen;
import services.CertificationAuthority;
import services.CertificationAuthorityDouble;
import services.GPD;
import services.GPDDouble;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformEnterFormTest {
    private UnifiedPlatform UP;
    private CertificationAuthority certAuth;
    private GPD gralPolDept;

    private Nif registeredNif;
    private Nif differentNif;
    private Date validNifDate;
    private SmallCode validPIN;

    private Citizen invalidCitz;
    private Citizen validCitzNotVerificated;
    private Goal goal;


    @BeforeEach
    void setUp() throws Throwable {
        // Certification Authority Info
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2023, Calendar.JANUARY, 1);
        validNifDate = c.getTime();
        registeredNif = new Nif("11111111A");
        validPIN = new SmallCode("123");

        //GPD Info
        differentNif = new Nif("22222222B");
        invalidCitz = new Citizen(differentNif, "Juan", "C/Prueba", "987654321");
        validCitzNotVerificated = new Citizen(registeredNif, "Claudio", "C/Prueba", "123456789");
        goal = new Goal(goalTypes.WORKWITHMINORS);

        // Double instances
        certAuth = new CertificationAuthorityDouble(false);
        gralPolDept = new GPDDouble(false, true);

        UP = new UnifiedPlatform();
        // Inject dependences
        UP.setCertAuth(certAuth);
        UP.setGralPoliceDept(gralPolDept);
        prepareUseCase();
    }

    private void prepareUseCase() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(registeredNif, validNifDate);
        UP.enterPIN(validPIN);
    }

    @Test
    void checkIncompleteFormExceptionNullParameters() {
        assertThrows(IncompleteFormException.class, () -> UP.enterForm(null, null));
    }

    @Test
    void checkIncompleteFormExceptionGoalNull() {
        assertThrows(IncompleteFormException.class, () -> UP.enterForm(invalidCitz, null));
    }

    @Test
    void checkIncompleteFormExceptionCitizenNull() {
        assertThrows(IncompleteFormException.class, () -> UP.enterForm(null, goal));
    }

    @Test
    void checkNotMatchingNifsException() {
        assertThrows(NotMatchingNifsException.class, () -> UP.enterForm(invalidCitz, goal));
    }

    @Test
    void checkIncorrectVerificationException() {
        assertThrows(IncorrectVerificationException.class, () -> UP.enterForm(validCitzNotVerificated, goal));
    }
}
