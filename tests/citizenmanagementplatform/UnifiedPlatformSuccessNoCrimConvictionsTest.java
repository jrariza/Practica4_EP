package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import data.goalTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.Citizen;
import publicadministration.CreditCard;
import publicadministration.CriminalRecordCertf;
import services.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UnifiedPlatformSuccessNoCrimConvictionsTest implements UnifiedPlatformSuccessTest {
    private UnifiedPlatform UP;
    private CAS credAuthServ;
    private CertificationAuthority certAuth;
    private GPD gralPolDept;
    private JusticeMinistry justMin;

    private Citizen citz;
    private Date validNifDate;
    private CreditCard validCredCard;

    @Override
    @BeforeEach
    public void setUp() throws Throwable {
        // Citizen Info
        String name = "Fernando";
        Nif nif = new Nif("11111111A");
        String addr = "C/Prueba";
        String mobile = "123456789";
        citz = new Citizen(nif, name, addr, mobile);

        // Certification Authority Info
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2023, Calendar.JANUARY, 1);
        validNifDate = c.getTime();

        // Credit Card Info
        c.set(3000, Calendar.JANUARY, 1);
        Date expirDate = c.getTime();
        SmallCode svc = new SmallCode("012");
        validCredCard = new CreditCard(nif, "1", expirDate, svc);
        BigDecimal balance = new BigDecimal(1_000_000);

        // Double instances
        credAuthServ = new CASDouble(false, validCredCard, balance);
        certAuth = new CertificationAuthorityDouble(false);
        gralPolDept = new GPDDouble(false, false);
        justMin = new JusticeMinistryDouble(false, false);

        UP = new UnifiedPlatform();
        // Inject dependences
        UP.setCertAuth(certAuth);
        UP.setCredAuthServ(credAuthServ);
        UP.setGralPoliceDept(gralPolDept);
        UP.setJustMin(justMin);
    }

    @Override
    @Test
    public void completeCorrectUseCaseTest() {
        assertDoesNotThrow(() -> UP.selectJusMin());
        assertDoesNotThrow(() -> UP.selectProcedures());
        assertDoesNotThrow(() -> UP.selectCriminalReportCertf());
        assertDoesNotThrow(() -> UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN));

        assertDoesNotThrow(() -> UP.enterNIFandPINobt(citz.getNif(), validNifDate));
        assertNotNull(UP.getCitizen());
        assertEquals(UP.getCitizen().getNif(), citz.getNif());

        assertDoesNotThrow(() -> UP.enterPIN(new SmallCode("123")));

        assertDoesNotThrow(() -> UP.enterForm(citz, new Goal(goalTypes.WORKWITHMINORS)));
        assertNotNull(citz);

        int payTransactionsBefore = UP.getPaymentHistory().size();
        assertDoesNotThrow(() -> UP.realizePayment());
        assertDoesNotThrow(() -> UP.enterCardData(validCredCard));
        int payTransactionsAfter = UP.getPaymentHistory().size();
        assertEquals(payTransactionsAfter, payTransactionsBefore + 1);
        assertNotNull(UP.getCardPayment());
        assertEquals(citz.getNif(), UP.getCardPayment().getNif());

        assertDoesNotThrow(() -> UP.obtainCertificate());
        CriminalRecordCertf certf = UP.getCrimCertf();
        assertNotNull(certf);
        assertTrue(certf.getCrimConvs().isEmpty());
    }
}
