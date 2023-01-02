package data;

import exceptions.NullParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GoalTest {

    @Test
    void NullGoalTest() {
        assertThrows(NullParameterException.class, () -> new Goal(null));
    }
}