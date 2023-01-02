package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import data.goalTypes;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.Citizen;
import publicadministration.CreditCard;
import publicadministration.CrimConviction;
import services.CASDouble;
import services.CertificationAuthorityDouble;
import services.GPDDouble;
import services.JusticeMinistryDouble;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlataformConnectionTest {

    private UnifiedPlatform UP;
    private CASDouble credAuthServ;
    private CertificationAuthorityDouble certAuth;
    private GPDDouble gralPolDept;
    private JusticeMinistryDouble justMin;

    private Nif registeredNif;
    private Citizen validCitz;
    private Date validNifDate;
    private CreditCard validCreditCard;
    private SmallCode validPIN;
    private Goal goal;
    private BigDecimal balance;
    private CrimConviction crimConv;


    @BeforeEach
    public void setUp() throws Throwable {
        // Certification Authority Info
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2023, Calendar.JANUARY, 1);
        validNifDate = c.getTime();
        registeredNif = new Nif("11111111A");
        validPIN = new SmallCode("123");

        //GPD Info
        validCitz = new Citizen(registeredNif, "Fernando", "C/Prueba", "123456789");
        goal = new Goal(goalTypes.WORKWITHMINORS);

        // CAS Info
        c.set(3000, Calendar.JANUARY, 1);
        Date cardDate = c.getTime();
        validCreditCard = new CreditCard(registeredNif, "1", cardDate, validPIN);
        goal = new Goal(goalTypes.WORKWITHMINORS);
        balance = new BigDecimal(1_000_000);

        // Justice Ministry Info
        c.set(2018, Calendar.JANUARY, 1);
        Date crimDate = c.getTime();
        String offense = "Robo";
        String sentence = "2 años de carcel";
        crimConv = new CrimConviction(crimDate, offense, sentence);

        // Double instances
        credAuthServ = new CASDouble(true, validCreditCard, balance);
        certAuth = new CertificationAuthorityDouble(true);
        gralPolDept = new GPDDouble(true, false);
        justMin = new JusticeMinistryDouble(true, false, validCitz, crimConv);

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
    }

    @Test
    public void enterNIFandPINobtConnectException() {
        assertThrows(ConnectException.class, () -> UP.enterNIFandPINobt(registeredNif, validNifDate));
    }

    @Test
    public void enterPINConnectException() throws NullParameterException, NotValidNifCodeException, InvalidFormatException, IncorrectValDateException, NifNotRegisteredException, ProceduralException, AnyMobileRegisteredException, ConnectException {
        UP.setCertAuth(new CertificationAuthorityDouble(false));
        UP.enterNIFandPINobt(registeredNif, validNifDate);
        UP.setCertAuth(new CertificationAuthorityDouble(true));
        assertThrows(ConnectException.class, () -> UP.enterPIN(validPIN));
    }

    @Test
    public void enterFormConnectException() throws NullParameterException, NotValidNifCodeException, InvalidFormatException, IncorrectValDateException, NifNotRegisteredException, ProceduralException, AnyMobileRegisteredException, ConnectException, NotValidPINException {
        UP.setCertAuth(new CertificationAuthorityDouble(false));
        UP.enterNIFandPINobt(registeredNif, validNifDate);
        UP.enterPIN(new SmallCode("123"));
        UP.setCertAuth(new CertificationAuthorityDouble(true));
        assertThrows(ConnectException.class, () -> UP.enterForm(validCitz, goal));
    }

    @Test
    public void enterCardDataConnectException() throws NullParameterException, NotValidNifCodeException, InvalidFormatException, IncorrectValDateException, NifNotRegisteredException, ProceduralException, AnyMobileRegisteredException, ConnectException, NotValidPINException, IncompleteFormException, IncorrectVerificationException, NotMatchingNifsException {
        UP.setCertAuth(new CertificationAuthorityDouble(false));
        UP.enterNIFandPINobt(registeredNif, validNifDate);
        UP.enterPIN(new SmallCode("123"));
        UP.setGralPoliceDept(new GPDDouble(false, false));
        UP.enterForm(validCitz, goal);
        UP.realizePayment();
        assertThrows(ConnectException.class, () -> UP.enterCardData(validCreditCard));
    }
}
