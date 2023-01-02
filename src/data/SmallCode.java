package data;

import exceptions.InvalidFormatException;

import java.util.Objects;

final public class SmallCode {
    private final String smallCode;

    public SmallCode(String code) throws InvalidFormatException {
        if (Objects.equals(code, null)) throw new NullPointerException("Small Code is null");
        if (!isValid(code)) throw new InvalidFormatException("Small Code format is wrong");
        smallCode = code;
    }

    public String getSmallCode() {
        return smallCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmallCode smallCode1 = (SmallCode) o;
        return smallCode.equals(smallCode1.smallCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(smallCode);
    }

    @Override
    public String toString() {
        return "SmallCode{" +
                "smallCode='" + smallCode + '\'' +
                '}';
    }

    private boolean isValid(String code) {
        return code.matches("[0-9]{3}");
    }
}
