package publicadministration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class CrimConvictionsCollTest {

    @Test
    public void notEmpty() {
        CrimConviction tmp_crmC = new CrimConviction(new Date(), "off", "sentc");

        CrimConvictionsColl tmp_crmCColl = new CrimConvictionsColl();
        tmp_crmCColl.addCriminalConviction(tmp_crmC);

        ArrayList<CrimConviction> getCrmC = tmp_crmCColl.getAllCrimConviction();

        Assertions.assertTrue(getCrmC.contains(tmp_crmC));

        Assertions.assertFalse(tmp_crmCColl.isEmpty());
    }

}
