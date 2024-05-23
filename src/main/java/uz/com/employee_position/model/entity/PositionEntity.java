package uz.com.employee_position.model;


import jakarta.persistence.Entity;
import lombok.*;


@Entity(name = "positions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionEntity extends BaseEntity{

    private String nameUz;
    private String nameRu;
    private String nameEn;

}
