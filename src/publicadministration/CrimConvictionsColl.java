package publicadministration;

import java.util.ArrayList;
import java.util.Date;

public class CrimConvictionsColl {
    // Represents the total criminal convictions registered for a citizen


    // Its components, that is, the set of criminal convictions
    private final ArrayList<CrimConviction> allCrimConviction;


    public CrimConvictionsColl() {
        // Initializes the object
        allCrimConviction = new ArrayList<>();
    }

    public boolean isEmpty() {
        return allCrimConviction.isEmpty();
    }

    // the getters
    public ArrayList<CrimConviction> getAllCrimConviction() {
        return allCrimConviction;
    }

    public void addCriminalConviction(CrimConviction crmC) {
        // Adds a criminal conviction
        this.allCrimConviction.add(crmC);
    }

    public CrimConviction getCriminalConviction(Date date) {
        // Gets a specific criminal conviction by date
        for (CrimConviction crmC : this.allCrimConviction) {
            if (crmC.getCommitDate().equals(date)) {
                return crmC;
            }
        }

        return null;
    }

    public String toString() {
        // Converts to String
        String tmp_str = "";

        for (CrimConviction crmC : this.allCrimConviction) {
            tmp_str = tmp_str.concat(crmC.toString());
        }

        return null;
    }
}