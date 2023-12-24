package lk.ijse.Sarasavi.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int itemCode;
    private String description;
    private int quantity;
    private double unitPrice;
    private double subTotal;

}
