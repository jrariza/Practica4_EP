package citizenmanagementplatform;

import data.Goal;
import data.Nif;
import data.SmallCode;
import exceptions.*;
import publicadministration.CardPayment;
import publicadministration.Citizen;
import publicadministration.CriminalRecordCertf;
import services.CAS;
import services.CertificationAuthority;
import services.GPD;
import services.JusticeMinistry;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Date;
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

    public void selectCriminalReportCertf() throws ProceduralException {
        // Comprobar que estamos en Min. Justicia y se ha seleccionado tramites
        if (!(currentAAPP == AAPP.JUST_MIN && proceduresEnabled)) throw new ProceduralException();
        procedureIsActive = true;
        procedureCost = new BigDecimal("3.86");
    }

    public void selectAuthMethod(byte opc) throws ProceduralException {
        // Comprobar que estamos en Min.Justicia, tramite en curso
        if (!(currentAAPP == AAPP.JUST_MIN && procedureIsActive)) throw new ProceduralException();
        authMethod = opc;
    }

    public void enterNIFandPINobt(Nif nif, Date valDate) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException, ProceduralException {
        // Comprobar tramite en curso y cl@ve pin
        if (!(procedureIsActive && authMethod == CLAVE_PIN)) throw new ProceduralException();
        PINSent = CertAuth.sendPIN(nif, valDate);
        citizen = new Citizen(nif, null, null, null);
    }

    public void enterPIN(SmallCode pin) throws NotValidPINException, ConnectException, ProceduralException {
        if (!(procedureIsActive && authMethod == CLAVE_PIN && PINSent)) throw new ProceduralException();
        authenticated = CertAuth.checkPIN(citizen.getNif(), pin);
    }
}

