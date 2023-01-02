package data;

import exceptions.NullParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoalTest {

    @Test
    void NullGoalTest() {
        assertThrows(NullParameterException.class, () -> new Goal(null));
    }

    @Test
    void validGoal(){
        assertDoesNotThrow(()-> new Goal(goalTypes.PUBLICWORKERS));
    }
}