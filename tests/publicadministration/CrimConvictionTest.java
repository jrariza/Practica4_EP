package publicadministration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrimConvictionTest {

    private CrimConviction crimConv;
    private Date date;
    private String offense;
    private String sentence;

    @BeforeEach
    void setUp() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(1, Calendar.JANUARY, 2025);
        date = c.getTime();
        offense = "Robo";
        sentence = "2 años de carcel";
        crimConv = new CrimConviction(date, offense, sentence);
    }

    @Test
    void testToString() {
        String crimConvToString = "CrimConviction{" +
                "commitDate=" + date +
                ", offense='" + offense + '\'' +
                ", sentence='" + sentence + '\'' +
                '}';
        assertEquals(crimConvToString, crimConv.toString());
    }
}