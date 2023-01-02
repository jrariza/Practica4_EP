package data;

import exceptions.NullParameterException;

import java.util.Objects;

final public class Goal {
    private final goalTypes goal;

    public Goal(goalTypes goalType) throws NullParameterException {
        if (goalType == null) throw new NullParameterException();
        goal = goalType;
    }

    public goalTypes getGoal() {
        return goal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal1 = (Goal) o;
        return goal == goal1.goal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(goal);
    }

    @Override
    public String toString() {
        return "Goal{" +
                "goal=" + goal +
                '}';
    }
}