package lk.ijse.Sarasavi.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTM {
    private int id;
    private String name;
    private double price;
    private String category;
    private int qty;
}
