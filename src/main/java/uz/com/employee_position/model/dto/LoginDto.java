package uz.com.employee_position.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    private String email;
    private String password;

}
