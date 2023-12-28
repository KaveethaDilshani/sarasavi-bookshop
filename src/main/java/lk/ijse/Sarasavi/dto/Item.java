package lk.ijse.Sarasavi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private int id;
    private double price;
    private String category;
    private int qty;

    public Item(String eName, String eAddress, int contactNumber, String eId) {
    }
}
