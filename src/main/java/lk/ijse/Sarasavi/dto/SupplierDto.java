package lk.ijse.Sarasavi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
    private int id;
    private String name;
    private String address;
    private int contactNumber;
}
