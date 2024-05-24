package uz.com.employee_position.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.com.employee_position.model.dto.request.EmployeeDto;
import uz.com.employee_position.model.dto.request.LoginDto;
import uz.com.employee_position.model.dto.response.JwtResponse;
import uz.com.employee_position.model.dto.response.StandardResponse;
import uz.com.employee_position.service.employee.EmployeeService;
import uz.com.employee_position.service.employee.EmployeeServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("/sign-up")
    public StandardResponse<JwtResponse> signUp(
            @RequestBody EmployeeDto employeeDto
            ){
        return employeeServiceImpl.save(employeeDto);
    }

    @PostMapping("/sign-in")
    public StandardResponse<JwtResponse> signIn(
            @RequestBody LoginDto loginDto
            ){
        return employeeServiceImpl.login(loginDto);
    }
}
