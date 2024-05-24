package uz.com.employee_position.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.employee_position.model.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    @Query("select u from employees as u where u.isDeleted=false and u.phoneNumber=?1")
    Optional<EmployeeEntity> findEmployeeEntityByNumber(String number);

    @Query("select u from employees as u where u.isDeleted=false and u.email=?1")
    EmployeeEntity findEmployeeEntityByEmail(String email);
    @Query("select u from employees as u  where u.isDeleted=false")
    Page<EmployeeEntity> findAllEmployee(Pageable pageable);
    @Query("select u from employees as u  where u.isDeleted=false")
    List<EmployeeEntity> getAll();

    @Query("select u from employees as u where u.isDeleted=false and u.id=?1")
    EmployeeEntity getEmployeeEntityById(UUID id);
}
