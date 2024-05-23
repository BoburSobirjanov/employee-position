package uz.com.employee_position.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.com.employee_position.model.entity.PositionEntity;

import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, UUID> {
}
