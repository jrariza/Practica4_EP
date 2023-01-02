package services;

import data.Goal;
import exceptions.IncorrectVerificationException;
import publicadministration.Citizen;

import java.net.ConnectException;

public class GPDDouble implements GPD {

    private boolean connectExc;
    private boolean incorrectVerEx;

    public GPDDouble(boolean connectExc, boolean incorrectVerEx) {
        this.connectExc = connectExc;
        this.incorrectVerEx = incorrectVerEx;
    }

    public GPDDouble() {
        new GPDDouble(false, false);
    }

    @Override
    public boolean verifyData(Citizen persData, Goal goal) throws IncorrectVerificationException, ConnectException {
        if (connectExc) throw new ConnectException("Connexion Exception");
        if (incorrectVerEx) throw new IncorrectVerificationException("Incorrect Verification");
        return true;
    }
}