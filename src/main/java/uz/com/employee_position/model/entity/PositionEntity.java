package uz.com.employee_position.model.entity;


import jakarta.persistence.Entity;
import lombok.*;
import uz.com.employee_position.model.BaseEntity;


@Entity(name = "positions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionEntity extends BaseEntity {

    private String nameUz;
    private String nameRu;
    private String nameEn;

}
