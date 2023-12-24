package lk.ijse.Sarasavi.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTM {
    private String id;
    private String name;
    private String address;
    private int contactNumber;
}
