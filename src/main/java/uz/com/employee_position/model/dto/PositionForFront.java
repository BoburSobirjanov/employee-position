package uz.com.employee_position.model.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionForFront {

    private String name;
    private UUID id;

}
