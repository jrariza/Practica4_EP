package citizenmanagementplatform;

import data.Goal;
import exceptions.ProceduralException;
import publicadministration.CardPayment;
import publicadministration.Citizen;
import publicadministration.CriminalRecordCertf;
import services.CAS;
import services.CertificationAuthority;
import services.GPD;
import services.JusticeMinistry;

import java.math.BigDecimal;
import java.util.HashMap;

public class UnifiedPlatform {
    public static final byte NO_AUTH = 0;
    public static final byte DIG_CERTF = 1;
    public static final byte CLAVE_PIN = 2;
    public static final byte CLAVE_PERM = 3;

    private enum AAPP {JUST_MIN, SS, SEPE}

    // Services
    private CAS CredAuthServ;
    private CertificationAuthority CertAuth;
    private GPD GralPoliceDept;
    private JusticeMinistry JustMin;

    // AAPP and procedure tracking variables
    private AAPP currentAAPP;
    private boolean proceduresEnabled;
    private boolean procedureIsActive;
    private BigDecimal procedureCost; //se podría asignar al elegir trámite

    // Authentication variables
    private byte authMethod;
    private boolean PINSent;
    private boolean authenticated;

    // Citizen data
    private Citizen citizen;
    private Goal goal;
    private boolean dataIsVerified;

    // Payment data
    private boolean paymentIsReady;
    private boolean paymentCompleted;
    private CardPayment cPay;
    private HashMap<String, CardPayment> paymentHistory;
    private static int numTrans;

    // Procedure output
    private CriminalRecordCertf crimCertf;

    // The constructor/s
    UnifiedPlatform() {
        CredAuthServ = null;
        CertAuth = null;
        GralPoliceDept = null;
        JustMin = null;

        currentAAPP = null;
        proceduresEnabled = false;
        procedureIsActive = false;
        procedureCost = null;

        authMethod = 0;
        PINSent = false;
        authenticated = false;

        citizen = null;
        goal = null;
        dataIsVerified = false;

        cPay = null;
        paymentHistory = new HashMap<>();
        paymentIsReady = false;
        paymentCompleted = false;
    }

    // Input events
    public void selectJusMin() throws ProceduralException {
        // Si ya se ha elegido una AAPP no se puede elegir otra hasta que no se salga de la actual
        if (currentAAPP != null) throw new ProceduralException();
        currentAAPP = AAPP.JUST_MIN;
    }

    public void selectProcedures() throws ProceduralException {
        // Comprobar que estamos en Min. Justicia
        if (currentAAPP != AAPP.JUST_MIN) throw new ProceduralException();
        proceduresEnabled = true;
    }
}

