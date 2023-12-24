package lk.ijse.Sarasavi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendesDto {
    private int id;
    private Date date;
    private String workingHours;
    private String otHours;
    private String employeeId;
}
