package uz.com.employee_position.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Entity(name = "positions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionEntity extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

}
