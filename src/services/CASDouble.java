package services;

import exceptions.InsufficientBalanceException;
import exceptions.NotValidPaymentDataException;
import publicadministration.CreditCard;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Date;
import java.util.Objects;

public class CASDouble implements CAS {
    private CreditCard validCard;
    private final BigDecimal balance;
    private final boolean connectEx;

    public CASDouble(boolean conEx) {
        // Won't consider any CreditCard as valid
        this.balance = null;
        this.connectEx = conEx;
    }

    public CASDouble(boolean conEx, CreditCard card, BigDecimal balance) {
        // Will only consider the specified CreditCard as valid
        this.validCard = card;
        this.balance = balance;
        this.connectEx = conEx;
    }

    @Override
    public boolean askForApproval(String nTrans, CreditCard cardData, Date date, BigDecimal imp) throws NotValidPaymentDataException, InsufficientBalanceException, ConnectException {
        if (connectEx) throw new ConnectException();
        if (!isValidCredCard(cardData)) throw new NotValidPaymentDataException();
        if (!hasEnoughCredit(imp)) throw new InsufficientBalanceException();

        return true;
    }

    private boolean isValidCredCard(CreditCard card) {
        return Objects.equals(validCard, card);
    }

    private boolean hasEnoughCredit(BigDecimal imp) {
        return balance.compareTo(imp) >= 0;
    }
}