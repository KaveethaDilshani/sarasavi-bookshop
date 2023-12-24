package lk.ijse.Sarasavi.dto;

public class CustomerDto {
    private  String id;
    private  String  Name;
    private  String address;
    private  String number;

    public CustomerDto(String id, String name, String address, String number){
        this.id = id;
        this. Name = name;
        this.address = address;
        this.number = number;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}