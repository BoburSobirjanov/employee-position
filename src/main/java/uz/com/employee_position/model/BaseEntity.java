package uz.com.employee_position;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @CreationTimestamp
    protected LocalDateTime createdTime;

    @UpdateTimestamp
    protected LocalDateTime updatedTime;

    @Column(columnDefinition = "boolean default false")
    protected boolean isDeleted;

    protected UUID deletedBy;

    protected LocalDateTime deletedTime;

    protected UUID createdBy;

}
