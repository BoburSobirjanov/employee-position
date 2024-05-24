package uz.com.employee_position.model.dto.request;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDto {

    private String fullNameUz;
    private String fullNameRu;
    private String fullNameEng;

    private String email;

    private String password;

    private String phoneNumber;

    private int age;

}
