package uz.com.employee_position.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionDto {

    private String nameUz;
    private String nameRu;
    private String nameEng;

}
