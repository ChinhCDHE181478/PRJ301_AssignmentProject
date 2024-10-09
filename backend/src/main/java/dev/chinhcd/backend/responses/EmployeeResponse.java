package dev.chinhcd.backend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeResponse {
    @JsonProperty("Employee_ID")
    Integer id;

    @JsonProperty("Employee_Name")
    String employeeName;

    @JsonProperty("Department_Name")
    String departmentNam;
}
