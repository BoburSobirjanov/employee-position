package uz.com.employee_position.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.com.employee_position.model.dto.EmployeeDto;
import uz.com.employee_position.model.dto.EmployeeForFront;
import uz.com.employee_position.model.dto.LoginDto;
import uz.com.employee_position.response.JwtResponse;
import uz.com.employee_position.response.StandardResponse;
import uz.com.employee_position.service.EmployeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final EmployeeService employeeService;

    @PostMapping("/sign-up")
    public StandardResponse<JwtResponse> signUp(
            @RequestBody EmployeeDto employeeDto
            ){
        return employeeService.save(employeeDto);
    }

    @PostMapping("/sign-in")
    public StandardResponse<JwtResponse> signIn(
            @RequestBody LoginDto loginDto
            ){
        return employeeService.login(loginDto);
    }
}
