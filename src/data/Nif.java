package data;

import exceptions.NotValidNifCodeException;
import exceptions.NullParameterException;

/**
 * Essential data classes
 */

final public class Nif {
    // The tax identification number in the Spanish state.

    private final String nif;

    public Nif(String code) throws NotValidNifCodeException, NullParameterException {
        if (code == null) throw new NullParameterException();
        if (!isValid(code)) throw new NotValidNifCodeException();
        this.nif = code;
    }

    public String getNif() {
        return nif;
    }

    private boolean isValid(String code) {
        //8 digitos + 1 letra i o ñ u
        return code.matches("[0-9]{8}[A-Z&&[^IOÑU]]");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nif niff = (Nif) o;
        return nif.equals(niff.nif);
    }

    @Override
    public int hashCode() {
        return nif.hashCode();
    }

    @Override
    public String toString() {
        return "Nif{" + "nif ciudadano='" + nif + '\'' + '}';
    }

}
