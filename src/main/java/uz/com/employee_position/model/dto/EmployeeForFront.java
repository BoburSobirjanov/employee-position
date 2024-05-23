package uz.com.employee_position.model.dto;

import lombok.*;
import uz.com.employee_position.model.entity.PositionEntity;
import uz.com.employee_position.model.entity.UserRole;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeForFront {

    private UUID id;
    private String fullNameEng;
    private String fullNameUz;
    private String fullNameRu;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private PositionEntity position;
    private int age;

}
