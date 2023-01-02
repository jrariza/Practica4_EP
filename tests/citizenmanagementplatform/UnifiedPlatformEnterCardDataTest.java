package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import data.goalTypes;
import exceptions.IncompleteFormException;
import exceptions.InsufficientBalanceException;
import exceptions.NotValidPaymentDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.Citizen;
import publicadministration.CreditCard;
import services.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformEnterCardDataTest {

    private UnifiedPlatform UP;
    private CertificationAuthority certAuth;
    private GPD gralPolDept;
    private CAS credAuthServ;

    private Nif registeredNif;
    private Date validNifDate;
    private SmallCode validPIN;

    private Citizen validCitz;
    private Goal goal;

    private CreditCard emptyCard;
    private CreditCard invalidCard;
    private CreditCard validCard;
    private BigDecimal balance;


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
        validCitz = new Citizen(registeredNif, "Fernando", "C/Prueba", "123456789");
        goal = new Goal(goalTypes.WORKWITHMINORS);

        // CAS Info
        emptyCard = null;
        c.set(3000, Calendar.JANUARY, 1);
        Date cardDate = c.getTime();
        validCard = new CreditCard(registeredNif, "1", cardDate, validPIN);
        invalidCard = new CreditCard(registeredNif, "2", cardDate, validPIN);

        // Double instances
        certAuth = new CertificationAuthorityDouble(false);
        gralPolDept = new GPDDouble();

        UP = new UnifiedPlatform();
        // Inject dependences
        UP.setCertAuth(certAuth);
        UP.setGralPoliceDept(gralPolDept);
//        UP.setCredAuthServ();
        prepareUseCase();
    }

    private void prepareUseCase() throws Throwable {
        UP.selectJusMin();
        UP.selectProcedures();
        UP.selectCriminalReportCertf();
        UP.selectAuthMethod(UnifiedPlatform.CLAVE_PIN);
        UP.enterNIFandPINobt(registeredNif, validNifDate);
        UP.enterPIN(validPIN);
        UP.enterForm(validCitz, goal);
        UP.realizePayment();
    }


    @Test
    void checkIncompleteFormException() {
        int payTransactionsBefore = UP.getPaymentHistory().size();
        assertThrows(IncompleteFormException.class, () -> UP.enterCardData(emptyCard));
        int payTransactionsAfter = UP.getPaymentHistory().size();
        assertEquals(payTransactionsAfter, payTransactionsBefore);
    }

    @Test
    void checkNotValidPaymenDataException() {
        int payTransactionsBefore = UP.getPaymentHistory().size();
        credAuthServ = new CASDouble2(false, validCard, balance);
        UP.setCredAuthServ(credAuthServ);
        assertThrows(NotValidPaymentDataException.class, () -> UP.enterCardData(invalidCard));
        int payTransactionsAfter = UP.getPaymentHistory().size();
        assertEquals(payTransactionsAfter, payTransactionsBefore);
    }

    @Test
    void checkInsufficientBalanceException() {
        int payTransactionsBefore = UP.getPaymentHistory().size();
        balance = new BigDecimal(-100);
        credAuthServ = new CASDouble2(false, validCard, balance);
        UP.setCredAuthServ(credAuthServ);
        assertThrows(InsufficientBalanceException.class, () -> UP.enterCardData(validCard));
        int payTransactionsAfter = UP.getPaymentHistory().size();
        assertEquals(payTransactionsAfter, payTransactionsBefore);
    }
}
