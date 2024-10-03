package dev.chinhcd.backend.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    @JsonProperty("Employee_ID")
    private int employeeID;
}
