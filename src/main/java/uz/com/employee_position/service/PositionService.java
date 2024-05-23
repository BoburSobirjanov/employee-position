package uz.com.employee_position.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.employee_position.repository.EmployeeRepository;
import uz.com.employee_position.repository.PositionRepository;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

}
