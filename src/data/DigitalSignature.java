package data;

import exceptions.NullParameterException;

import java.util.Arrays;
import java.util.Objects;

final public class DigitalSignature {
    private final byte[] digitalSignature;

    public DigitalSignature(byte[] digSign) throws NullParameterException {
        if (Objects.equals(digSign, null)) throw new NullParameterException();
        digitalSignature = digSign;
    }

    public byte[] getDigitalSignature() {
        return digitalSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature that = (DigitalSignature) o;
        return Arrays.equals(digitalSignature, that.digitalSignature);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digitalSignature);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" +
                "digitalSignature=" + Arrays.toString(digitalSignature) +
                '}';
    }
}
