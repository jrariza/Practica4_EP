package publicadministration;

import data.Nif;
import exceptions.NotValidPaymentImportException;
import exceptions.NullParameterException;

import java.math.BigDecimal;
import java.util.Date;

public class CardPayment {
    // The information associated to the payment realized via internet

    private final String reference; // The code of the operation
    private final Nif nif; // The nif of the user
    private final Date date; // The date of the operation
    private final BigDecimal paymentImport; // The import of the payment

    public CardPayment(Nif nif, BigDecimal imp) throws NotValidPaymentImportException, NullParameterException {
        if (nif == null || imp == null) throw new NullParameterException();
        if (imp.compareTo(BigDecimal.ZERO) < 0) throw new NotValidPaymentImportException();

        // una instancia Nif siempre es válida
        this.nif = nif;
        this.paymentImport = imp;
        this.date = new Date();
        this.reference = nif.toString() + paymentImport.toString() + date.toString();
    }

    public String getReference() {
        return reference;
    }

    public Nif getNif() {
        return nif;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getPaymentImport() {
        return paymentImport;
    }

    @Override
    public String toString() {
        return "CardPayment{" +
                "reference='" + reference + '\'' +
                ", nif=" + nif +
                ", date=" + date +
                ", paymentImport=" + paymentImport +
                '}';
    }
}
