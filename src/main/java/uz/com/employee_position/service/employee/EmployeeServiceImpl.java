package uz.com.employee_position.service.employee;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import uz.com.employee_position.model.dto.request.ChangeOrSetPosition;
import uz.com.employee_position.model.dto.request.EmployeeDto;
import uz.com.employee_position.model.dto.request.LoginDto;
import uz.com.employee_position.model.dto.response.EmployeeForFront;
import uz.com.employee_position.model.dto.response.JwtResponse;
import uz.com.employee_position.model.dto.response.StandardResponse;
import uz.com.employee_position.model.entity.EmployeeEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeServiceImpl {

    StandardResponse<JwtResponse> save(EmployeeDto employeeDto);

    void checkUserEmailAndPhoneNumber(String email, String phoneNumber);

    StandardResponse<JwtResponse> login(LoginDto loginDto);

    StandardResponse<EmployeeForFront> getById(UUID id);

    StandardResponse<String> deleteById(UUID id, Principal principal);

    void getAllEmployeeExcel(HttpServletResponse response, String language) throws IOException;

    StandardResponse<List<EmployeeEntity>> getAll(int page, int size);

    StandardResponse<EmployeeForFront> update(EmployeeDto employeeDto,UUID id);

    StandardResponse<String> deleteEmployeeByPosition(String name,Principal principal);

    StandardResponse<String> applyToAdmin(String email);

    StandardResponse<String> changeEmployeePosition(ChangeOrSetPosition changeOrSetPosition);

    List<EmployeeEntity> getAllEmployeeByPosition(UUID id);

    StandardResponse<String> removePositionFromEmployee(UUID id);
}
