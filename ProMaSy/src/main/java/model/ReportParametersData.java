package main.java.model;

/**
 * Created by AL on 11.10.2016.
 */
public class ReportParametersData {
    private static ReportParametersData instance = new ReportParametersData();
    private String headPosition = "";
    private String head = "";
    private String departmentHead = "";
    private String personallyLiableEmpl = "";
    private String accountant = "";
    private String economist = "";
    private String headTender = "";


    private ReportParametersData() {
    }

    public static ReportParametersData getInstance() {
        return instance;
    }

    public void setData(String headPosition, String head, String departmentHead, String personallyLiableEmpl, String accountant, String economist, String headTender) {
        this.headPosition = headPosition;
        this.head = head;
        this.departmentHead = departmentHead;
        this.personallyLiableEmpl = personallyLiableEmpl;
        this.accountant = accountant;
        this.economist = economist;
        this.headTender = headTender;
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

    public String getHeadTender() {
        return headTender;
    }

	@Override
	public String toString() {
		return "ReportParametersData [headPosition=" + headPosition + ", head=" + head + ", departmentHead="
				+ departmentHead + ", personallyLiableEmpl=" + personallyLiableEmpl + ", accountant=" + accountant
				+ ", economist=" + economist + "]";
	}
    
    
}
