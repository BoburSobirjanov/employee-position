package uz.com.employee_position.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.employee_position.model.dto.request.ChangeOrSetPosition;
import uz.com.employee_position.model.dto.request.EmployeeDto;
import uz.com.employee_position.model.dto.response.EmployeeForFront;
import uz.com.employee_position.model.entity.EmployeeEntity;
import uz.com.employee_position.model.dto.response.StandardResponse;
import uz.com.employee_position.service.employee.EmployeeServiceImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/excel")
    public void getEmployeeExcel(HttpServletResponse response,
                                 @RequestHeader(value = "Accept-Language") String language) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=employees.xls";
        response.setHeader(headerKey,headerValue);
        employeeServiceImpl.getAllEmployeeExcel(response,language);
        response.flushBuffer();
    }

    @PostMapping("/get-by-id")
    public StandardResponse<EmployeeForFront> getById(
            @RequestParam UUID id
            ){
        return employeeServiceImpl.getById(id);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @RequestParam UUID id,
            Principal principal
    ){
        return employeeServiceImpl.deleteById(id, principal);
    }

    @GetMapping("/get-all")
    public StandardResponse<List<EmployeeEntity>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return employeeServiceImpl.getAll(page, size);
    }

    @PutMapping("/update")
    public StandardResponse<EmployeeForFront> update(
            @RequestBody EmployeeDto employeeDto,
            @RequestParam UUID id
            ){
        return employeeServiceImpl.update(employeeDto, id);
    }

    @PostMapping("/apply-to-admin")
    public StandardResponse<String> applyToAdmin(
            @RequestParam String email
    ){
        return employeeServiceImpl.applyToAdmin(email);
    }

    @DeleteMapping("/delete-by-position")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> deleteByPosition(
            @RequestParam String name,
            Principal principal
    ){
        return employeeServiceImpl.deleteEmployeeByPosition(name, principal);
    }


    @PostMapping("/change-position")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> changeEmployeePosition(
            @RequestBody ChangeOrSetPosition changeOrSetPosition
            ){
        return employeeServiceImpl.changeEmployeePosition(changeOrSetPosition);
    }

    @GetMapping("/get-employee-by-position")
    public List<EmployeeEntity> getAllEmployeesByPosition(
            @RequestParam UUID id
    ){
        return employeeServiceImpl.getAllEmployeeByPosition(id);
    }

    @PutMapping("/remove-position")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> removePositionFromEmployee(
            @RequestParam UUID id
    ){
        return employeeServiceImpl.removePositionFromEmployee(id);
    }
}
