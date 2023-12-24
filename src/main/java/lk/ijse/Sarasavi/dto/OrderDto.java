package lk.ijse.Sarasavi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private int orderId;
    private String customerId;
    private Date orderDate;
    private String orderType;
    private double total;

    private List<OrderDetailDto> orderDetailDtos;
}
