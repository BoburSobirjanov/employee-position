package uz.com.employee_position.response;

import lombok.*;
import uz.com.employee_position.model.dto.EmployeeForFront;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private EmployeeForFront employeeForFront;
}
