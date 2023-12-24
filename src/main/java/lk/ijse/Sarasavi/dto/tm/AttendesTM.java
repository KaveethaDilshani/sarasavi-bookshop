package lk.ijse.Sarasavi.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendesTM {
    private LocalDate date;
    private String workingHours;
    private String otHours;
    private String employeeId;
}
