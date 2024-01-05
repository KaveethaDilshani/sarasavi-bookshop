package lk.ijse.Sarasavi.dto;

public class Employee {
    private String eName;
    private String eId;
    private String eAddress;
    private int contactNumber;

    public Employee() {

    }

    public Employee(String eName, String eId, String eAddress, int contactNumber) {
        this.eName = eName;
        this.eId = eId;
        this.eAddress = eAddress;
        this.contactNumber = contactNumber;
    }

    public Employee(String eName, String eAddress, int contactNumber, String eId) {
    }

    public String getEName() {
        return eName;
    }

    public void setEName(String eName) {
        this.eName = eName;
    }

    public String getEId() {
        return eId;
    }

    public void setEId(String eId) {
        this.eId = eId;
    }

    public String getEAddress() {
        return eAddress;
    }

    public void setEAddress(String eAddress) {
        this.eAddress = eAddress;
    }

    public String getContactNumber() {
        return String.valueOf(contactNumber);
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eName='" + eName + '\'' +
                ", eId='" + eId + '\'' +
                ", eAddress='" + eAddress + '\'' +
                ", contactNumber=" + contactNumber +
                '}';
    }
}
