package data;

import exceptions.NotValidNifCodeException;
import exceptions.NullParameterException;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NifTest {

    @Test
    void NullNifTest() {
        assertThrows(NullParameterException.class, () -> new Nif(null));
    }

    @Name("Sin letra final, demasiado corto")
    @Test
    void InvalidNifTest1() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678"));
    }

    @Name("Con letra inicial")
    @Test
    void InvalidNifTest2() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("A12345678"));
    }

    @Name("2 letras finales")
    @Test
    void InvalidNifTest3() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678AA"));
    }

    @Name("Sin dígitos, solo 9 letras")
    @Test
    void InvalidNifTest4() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("ABCABCABC"));
    }

    @Name("Sin letra, solo 9 dígitos")
    @Test
    void InvalidNifTest5() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("123456789"));
    }

    @Name("Número menos, demasiado corto")
    @Test
    void InvalidNifTest6() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("1234567A"));
    }

    @Name("Número más, demasiado largo")
    @Test
    void InvalidNifTest8() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("123456789A"));
    }

    @Name("Letra inválida I")
    @Test
    void InvalidNifTest9() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678I"));
    }

    @Name("Letra inválida O")
    @Test
    void InvalidNifTest10() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678O"));
    }

    @Name("Letra inválida Ñ")
    @Test
    void InvalidNifTest11() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678Ñ"));
    }

    @Name("Letra inválida U")
    @Test
    void InvalidNifTest12() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678U"));
    }

    @Name("Letra minúscula")
    @Test
    void InvalidNifTest13() {
        assertThrows(NotValidNifCodeException.class, () -> new Nif("12345678a"));
    }

    @Name("Nif válido")
    @Test
    void ValidNifTest() {
        assertDoesNotThrow(() -> new Nif("12345678A"));
    }
}
