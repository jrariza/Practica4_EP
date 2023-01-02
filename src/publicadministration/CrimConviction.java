package publicadministration;

import java.util.Date;

public class CrimConviction {
    // Represents a criminal conviction registered
    private Date commitDate;
    private String offense;
    private String sentence;

    public CrimConviction(Date commit, String off, String sentc) {
        this.commitDate = commit;
        this.offense = off;
        this.sentence = sentc;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public String getOffense() {
        return offense;
    }

    public String getSentence() {
        return sentence;
    }

    @Override
    public String toString() {
        return "CrimConviction{" +
                "commitDate=" + commitDate +
                ", offense='" + offense + '\'' +
                ", sentence='" + sentence + '\'' +
                '}';
    }
}
