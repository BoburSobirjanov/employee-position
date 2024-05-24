package uz.com.employee_position.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.employee_position.model.dto.EmployeeDto;
import uz.com.employee_position.model.dto.EmployeeForFront;
import uz.com.employee_position.model.entity.EmployeeEntity;
import uz.com.employee_position.response.StandardResponse;
import uz.com.employee_position.service.EmployeeService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/excel")
    public void getEmployeeExcel(HttpServletResponse response,
                                 @RequestHeader(value = "Accept-Language") String language) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=employees.xls";
        response.setHeader(headerKey,headerValue);
        employeeService.getAllEmployeeExcel(response,language);
        response.flushBuffer();
    }

    @PostMapping("/get-by-id")
    public StandardResponse<EmployeeForFront> getById(
            @RequestParam UUID id
            ){
        return employeeService.getById(id);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @RequestParam UUID id,
            Principal principal
    ){
        return employeeService.deleteById(id, principal);
    }

    @GetMapping("/get-all")
    public StandardResponse<List<EmployeeEntity>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return employeeService.getAll(page, size);
    }

    @PutMapping("/update")
    public StandardResponse<EmployeeForFront> update(
            @RequestBody EmployeeDto employeeDto,
            @RequestParam UUID id
            ){
        return employeeService.update(employeeDto, id);
    }

    @PostMapping("/apply-to-admin")
    public StandardResponse<String> applyToAdmin(
            @RequestParam String email
    ){
        return employeeService.applyToAdmin(email);
    }

    @DeleteMapping("/delete-by-position")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> deleteByPosition(
            @RequestParam String name,
            Principal principal
    ){
        return employeeService.deleteEmployeeByPosition(name, principal);
    }

}
