package services;

import data.Nif;
import data.SmallCode;
import exceptions.*;

import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CertificationAuthorityDouble implements CertificationAuthority {

    private final boolean connectExc;
    private final Nif validNif, nifWithoutMobile;
    private final SmallCode validPIN;
    private final Date validDate;


    public CertificationAuthorityDouble(boolean connectExc) throws NotValidNifCodeException, InvalidFormatException, NullParameterException {
        this.connectExc = connectExc;
        this.validNif = new Nif("11111111A");
        this.nifWithoutMobile = new Nif("22222222B");
        validPIN = new SmallCode("123");

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2023, Calendar.JANUARY, 1);
        validDate = c.getTime();
    }

    private void checkOperationStatus(Nif nif, Date date) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        if (connectExc) throw new ConnectException();
        if (isNotRegistered(nif)) throw new NifNotRegisteredException();
        if (isNifExpired(date)) throw new IncorrectValDateException();
        if (hasNoMobile(nif)) throw new AnyMobileRegisteredException();
    }

    private boolean isNotRegistered(Nif nif) {
        return !validNif.equals(nif) && !nifWithoutMobile.equals(nif);
    }

    private boolean isNifExpired(Date date) {
        return !validDate.equals(date);
    }

    private boolean hasNoMobile(Nif nif) {
        return Objects.equals(nifWithoutMobile, nif);
    }

    @Override
    public boolean sendPIN(Nif nif, Date date) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        checkOperationStatus(nif, date);
        return true;
    }

    @Override
    public boolean checkPIN(Nif nif, SmallCode pin) throws NotValidPINException, ConnectException {
        if (connectExc) throw new ConnectException();
        if (!validPIN.equals(pin)) throw new NotValidPINException();
        return true;
    }

}
