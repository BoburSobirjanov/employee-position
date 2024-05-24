package uz.com.employee_position.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.employee_position.model.entity.PositionEntity;


import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, UUID> {
    @Query("select u from positions as u  where u.isDeleted=false and u.name=?1")
    PositionEntity findPositionEntityByName(String name);

    @Query("select u from positions as u where u.isDeleted=false and u.id=?1")
    PositionEntity findPositionEntityById(UUID id);

    @Query("select u from positions as u where u.isDeleted=false")
    Page<PositionEntity> getAll(Pageable pageable);
}
