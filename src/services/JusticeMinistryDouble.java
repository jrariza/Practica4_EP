package services;

import data.*;
import exceptions.*;
import publicadministration.*;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;
import java.util.Objects;

public class JusticeMinistryDouble implements JusticeMinistry {

    private Nif convictedNif;   // All other nifs are considered valid Nifs without convictions
    private CrimConvictionsColl convictionsColl;

    boolean isConnectExActivated;
    boolean isDigSignatureExActivated;

    public JusticeMinistryDouble(boolean connectEx, boolean digSignEx){
        // There is no citizen with convictions, all citizens will get an empty convictionsColl
        isConnectExActivated = connectEx;
        isDigSignatureExActivated = digSignEx;
    }

    public JusticeMinistryDouble(boolean connectEx, boolean digSignEx, Citizen persD, CrimConviction crimC) {
        // Specifies a convicted citizen's Nif and their conviction Collection
        isConnectExActivated = connectEx;
        isDigSignatureExActivated = digSignEx;

        convictedNif = persD.getNif();
        convictionsColl = new CrimConvictionsColl();
        convictionsColl.addCriminalConviction(crimC);
    }

    @Override
    public CriminalRecordCertf getCriminalRecordCertf(Citizen persD, Goal g) throws ConnectException, DigitalSignatureException, IOException, BadPathException, NullParameterException {
        if (isConnectExActivated) throw new ConnectException();
        if (isDigSignatureExActivated) throw new DigitalSignatureException();

        Nif nif = persD.getNif();
        CrimConvictionsColl convictions = getConvictionsColl(nif);
        DigitalSignature dSign = getDigitalSignature(nif);

        return new CriminalRecordCertf(persD.getNif(), persD.getName(), g, dSign, convictions);
    }

    private CrimConvictionsColl getConvictionsColl(Nif nif) {
        if(Objects.equals(nif, convictedNif)) return convictionsColl;
        return new CrimConvictionsColl();
    }

    private DigitalSignature getDigitalSignature(Nif nif) throws NullParameterException {
        // Emulates a digital signature
        return new DigitalSignature("digSignature".getBytes());
    }

}
