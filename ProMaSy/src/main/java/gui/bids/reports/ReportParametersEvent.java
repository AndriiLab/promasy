package main.java.gui.bids.reports;

/**
 * Created by laban on 23.06.2016.
 */
public class ReportParametersEvent {
    private String headPosition;
    private String head;
    private String departmentHead;
    private String personallyLiableEmpl;
    private String accountant;
    private String economist;

    public ReportParametersEvent(String headPosition, String head, String departmentHead, String personallyLiableEmpl, String accountant, String economist) {
        this.headPosition = headPosition;
        this.head = head;
        this.departmentHead = departmentHead;
        this.personallyLiableEmpl = personallyLiableEmpl;
        this.accountant = accountant;
        this.economist = economist;
    }

    public String getHeadPosition() {
        return headPosition;
    }

    public String getHead() {
        return head;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public String getPersonallyLiableEmpl() {
        return personallyLiableEmpl;
    }

    public String getAccountant() {
        return accountant;
    }

    public String getEconomist() {
        return economist;
    }
}