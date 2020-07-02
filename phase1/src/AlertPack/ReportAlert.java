package AlertPack;

import java.io.Serializable;

public class ReportAlert extends AdminAlert implements Serializable {


    protected String senderUserName; // username of the user who is sending the report
    protected String reportedUserName; // username of the user who is being reported
    protected boolean isTradeComplete;

    public ReportAlert(String senderUserName, String reportedUserName, boolean isTradeComplete) {
        this.senderUserName = senderUserName;
        this.reportedUserName = reportedUserName;
        this.isTradeComplete = false;
    }

    /**
     *
     * @return the username of the user who is sending the report.
     */
    public String getSenderUserName() {
        return senderUserName;
    }

    /**
     *
     * @return the username of the user who is being reported.
     */
    public String getReportedUserName() {
        return reportedUserName;
    }

    /**
     *
     * @return whether the trade between users is incomplete.
     */
    public boolean getIsTradeComplete() {
        return isTradeComplete;
    }

    /**
     *
     * @return the alert text.
     */
    @Override
    public String toString() {
        return senderUserName + " has reported user " + reportedUserName + " whose trade status is " + isTradeComplete;
    }

}

