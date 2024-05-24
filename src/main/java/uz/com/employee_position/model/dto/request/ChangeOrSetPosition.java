package uz.com.employee_position.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChangeOrSetPosition {

    private String employeeId;
    private String positionId;
}
