package uz.com.employee_position.model.dto.response;

import lombok.*;
import uz.com.employee_position.model.dto.response.EmployeeForFront;

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
