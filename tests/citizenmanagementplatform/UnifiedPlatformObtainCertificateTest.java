package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import data.goalTypes;
import exceptions.BadPathException;
import exceptions.DigitalSignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.Citizen;
import publicadministration.CreditCard;
import publicadministration.PDFDocument;
import services.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformObtainCertificateTest {
    private UnifiedPlatform UP;
    private CAS credAuthServ;
    private CertificationAuthority certAuth;
    private GPD gralPolDept;
    private JusticeMinistry justMin;

    private Calendar calendar;

    private Nif registeredNif;
    private Date nifDate;
    private SmallCode validPIN;

    private Citizen citz;
    private Goal goal;

    private CreditCard validCreditCard;


    @BeforeEach
    public void setUp() throws Throwable {
        // Citizen Info
        String name = "Fernando";
        registeredNif = new Nif("11111111A");
        String addr = "C/Prueba";
        String mobile = "123456789";
        citz = new Citizen(registeredNif, name, addr, mobile);

        // Calendar
        calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2023, Calendar.JANUARY, 1);

        // Certification Authority Info
        nifDate = calendar.getTime();
        validPIN = new SmallCode("123");

        // GPD Info
        goal = new Goal(goalTypes.WORKWITHMINORS);

        // Credit Card Info
        calendar.set(2025, Calendar.JANUARY, 1);
        Date expirDate = calendar.getTime();
        SmallCode svc = new SmallCode("012");
        validCreditCard = new CreditCard(registeredNif, "1", expirDate, svc);


        // Double instances
        BigDecimal balance = new BigDecimal(1_000_000);
        credAuthServ = new CASDouble(false, validCreditCard, balance);
        certAuth = new CertificationAuthorityDouble(false);
        gralPolDept = new GPDDouble(false, false);

        justMin = new JusticeMinistryDouble(false, false);

        UP = new UnifiedPlatform();

        // Inject dependences
        UP.setCertAuth(certAuth);
        UP.setCredAuthServ(credAuthServ);
        UP.setGralPoliceDept(gralPolDept);
        UP.setJustMin(justMin);

        prepareUseCase();
    }

    private void prepareUseCase() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(registeredNif, nifDate);
        UP.enterPIN(validPIN);
        UP.enterForm(citz, goal);
        UP.realizePayment();
        UP.enterCardData(validCreditCard);
    }

    @Test
    public void BadPathExceptionTest() {
        String validPath = PDFDocument.getDefaultPathDir();
        PDFDocument.setDefaultPathDir("invalid");
        assertThrows(BadPathException.class, () -> UP.obtainCertificate());
        PDFDocument.setDefaultPathDir(validPath);
    }

    @Test
    public void DigitalSignatureExceptionTest() {
        JusticeMinistry jMin = new JusticeMinistryDouble(false, true);
        UP.setJustMin(jMin);
        assertThrows(DigitalSignatureException.class, () -> UP.obtainCertificate());
    }
}
