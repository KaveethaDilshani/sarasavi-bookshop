package lk.ijse.Sarasavi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Integer itemId;
    private Integer orderId;
    private Integer quantity;
    private double unitPrice;
}
