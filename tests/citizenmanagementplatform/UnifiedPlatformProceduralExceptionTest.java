package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import data.goalTypes;
import exceptions.ProceduralException;
import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.Citizen;
import publicadministration.CreditCard;
import publicadministration.CrimConviction;
import services.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformProceduralExceptionTest {

    private UnifiedPlatform UP;
    private CAS credAuthServ;
    private CertificationAuthority certAuth;
    private GPD gralPolDept;
    private JusticeMinistry justMin;

    private Calendar calendar;
    private Citizen citz;
    private Date nifDate;
    private SmallCode validPIN;
    private CreditCard validCreditCard;
    public Goal goal;

    @BeforeEach
    public void setUp() throws Throwable {
        // Citizen Info
        String name = "Fernando";
        Nif nif = new Nif("11111111A");
        String addr = "C/Prueba";
        String mobile = "123456789";
        citz = new Citizen(nif, name, addr, mobile);
        goal = new Goal(goalTypes.PUBLICWORKERS);

        // Calendar
        calendar = Calendar.getInstance();
        calendar.clear();

        // Certification Authority Info
        calendar.set(2023, Calendar.JANUARY, 1);
        nifDate = calendar.getTime();
        validPIN = new SmallCode("123");

        // Credit Card Info
        calendar.set(2025, Calendar.JANUARY, 1);
        Date expirDate = calendar.getTime();
        SmallCode svc = new SmallCode("012");
        validCreditCard = new CreditCard(nif, "1", expirDate, svc);


        // Double instances
        BigDecimal balance = new BigDecimal(1_000_000);
        credAuthServ = new CASDouble(false, validCreditCard, balance);
        certAuth = new CertificationAuthorityDouble(false);
        gralPolDept = new GPDDouble(false, false);

        // Justice Ministry Info
        calendar.set(2018, Calendar.JANUARY, 1);
        Date crimDate = calendar.getTime();
        String offense = "Robo";
        String sentence = "2 años de carcel";
        CrimConviction crimConv = new CrimConviction(crimDate, offense, sentence);
        justMin = new JusticeMinistryDouble(false, false, citz, crimConv);

        UP = new UnifiedPlatform();
        // Inject dependences
        UP.setCertAuth(certAuth);
        UP.setCredAuthServ(credAuthServ);
        UP.setGralPoliceDept(gralPolDept);
        UP.setJustMin(justMin);
    }

    @Test
    public void selectJusticeMinistryProceduralExcepcion() throws Throwable {
        UP.selectJusMin();
        assertThrows(ProceduralException.class, () -> UP.selectJusMin());
    }

    @Test
    public void selectProceduresProceduralExcepcion() {
        assertThrows(ProceduralException.class, () -> UP.selectProcedures());
    }


    @Test
    @Name("No estamos en Min Justicia")
    public void selectCriminalReportCertfProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.selectCriminalReportCertf());
    }

    @Test
    @Name("No hemos hecho selectProcedures()")
    public void selectCriminalReportCertfProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        assertThrows(ProceduralException.class, () -> UP.selectCriminalReportCertf());
    }

    @Test
    @Name("No estamos en Min Justicia")
    public void selectAuthMethodProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN));
    }

    @Test
    @Name("No hemos hecho selectCriminalReportCertf()")
    public void selectAuthMethodProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        assertThrows(ProceduralException.class, () -> UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN));
    }

    @Test
    @Name("No hay trámite en curso")
    public void enterNIFandPINobtProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.enterNIFandPINobt(citz.getNif(), nifDate));
    }

    @Test
    @Name("Método autent. no es Cl@ve PIN")
    public void enterNIFandPINobtProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PERM);

        assertThrows(ProceduralException.class, () -> UP.enterNIFandPINobt(citz.getNif(), nifDate));
    }

    @Test
    @Name("No hay trámite en curso")
    public void enterPINProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.enterPIN(new SmallCode("123")));
    }

    @Test
    @Name("Método autent. no es Cl@ve PIN")
    public void enterPINProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.DIG_CERTF);
        assertThrows(ProceduralException.class, () -> UP.enterPIN(new SmallCode("123")));
    }

    @Test
    @Name("No se ha enviado el PIN")
    public void enterPINProceduralExcepcion3() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        assertThrows(ProceduralException.class, () -> UP.enterPIN(new SmallCode("123")));
    }


    @Test
    @Name("No hay trámite en curso")
    public void enterFormProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.enterForm(citz, new Goal(goalTypes.WORKWITHMINORS)));
    }

    @Test
    @Name("No se ha finalizado la autenticación")
    public void enterFormProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        assertThrows(ProceduralException.class, () -> UP.enterForm(citz, new Goal(goalTypes.WORKWITHMINORS)));
    }

    @Test
    @Name("No hay trámite en curso")
    public void realizePaymentProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.realizePayment());
    }

    @Test
    @Name("No se ha finalizado la autenticación")
    public void realizePaymentProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        assertThrows(ProceduralException.class, () -> UP.realizePayment());
    }

    @Test
    @Name("No se han verificado los datos")
    public void realizePaymentProceduralExcepcion3() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(citz.getNif(), nifDate);
        UP.enterPIN(validPIN);
        assertThrows(ProceduralException.class, () -> UP.realizePayment());
    }

    @Test
    @Name("No hay trámite en curso")
    public void enterCardDataProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.enterCardData(validCreditCard));
    }

    @Test
    @Name("No se ha finalizado la autenticación")
    public void enterCardDataProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        assertThrows(ProceduralException.class, () -> UP.enterCardData(validCreditCard));
    }

    @Test
    @Name("No se han verificado los datos")
    public void enterCardDataProceduralExcepcion3() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(citz.getNif(), nifDate);
        UP.enterPIN(validPIN);
        assertThrows(ProceduralException.class, () -> UP.enterCardData(validCreditCard));
    }

    @Test
    @Name("No se ha ejecutado realizePayment()")
    public void enterCardDataProceduralExcepcion4() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(citz.getNif(), nifDate);
        UP.enterPIN(validPIN);
        UP.enterForm(citz, goal);
        assertThrows(ProceduralException.class, () -> UP.enterCardData(validCreditCard));
    }


    @Test
    @Name("No hay trámite en curso")
    public void obtainCertificateProceduralExcepcion1() {
        assertThrows(ProceduralException.class, () -> UP.obtainCertificate());
    }

    @Test
    @Name("No se ha finalizado la autenticación")
    public void obtainCertificateProceduralExcepcion2() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        assertThrows(ProceduralException.class, () -> UP.obtainCertificate());
    }

    @Test
    @Name("No se han verificado los datos")
    public void obtainCertificateProceduralExcepcion3() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(citz.getNif(), nifDate);
        UP.enterPIN(validPIN);
        assertThrows(ProceduralException.class, () -> UP.obtainCertificate());
    }

    @Test
    @Name("No se ha pagado el trámite")
    public void obtainCertificateProceduralExcepcion4() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(citz.getNif(), nifDate);
        UP.enterPIN(validPIN);
        UP.enterForm(citz, goal);
        UP.realizePayment();
        assertThrows(ProceduralException.class, () -> UP.obtainCertificate());
    }

}
