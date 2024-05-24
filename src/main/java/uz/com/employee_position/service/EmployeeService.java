package uz.com.employee_position.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.com.employee_position.exception.AuthenticationFailedException;
import uz.com.employee_position.exception.DataNotFoundException;
import uz.com.employee_position.exception.UserBadRequestException;
import uz.com.employee_position.model.dto.EmployeeDto;
import uz.com.employee_position.model.dto.EmployeeForFront;
import uz.com.employee_position.model.dto.LoginDto;
import uz.com.employee_position.model.entity.EmployeeEntity;
import uz.com.employee_position.model.entity.UserRole;
import uz.com.employee_position.repository.EmployeeRepository;
import uz.com.employee_position.response.JwtResponse;
import uz.com.employee_position.response.StandardResponse;
import uz.com.employee_position.response.Status;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public StandardResponse<JwtResponse> save(EmployeeDto employeeDto) {
        checkUserEmailAndPhoneNumber(employeeDto.getEmail(),employeeDto.getPhoneNumber());
        EmployeeEntity employee =  modelMapper.map(employeeDto, EmployeeEntity.class);
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setAge(employeeDto.getAge());
        employee.setRole(UserRole.EMPLOYEE);
        employee.setFullNameUz(employeeDto.getFullNameUz());
        employee.setFullNameRu(employeeDto.getFullNameRu());
        employee.setFullNameEng(employeeDto.getFullNameEng());
        employee=employeeRepository.save(employee);
        employee.setCreatedBy(employee.getId());
        employeeRepository.save(employee);
        String accessToken = jwtService.generateAccessToken(employee);
        String refreshToken = jwtService.generateRefreshToken(employee);
        EmployeeForFront employeeForFront = modelMapper.map(employee, EmployeeForFront.class);
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .employeeForFront(employeeForFront)
                .build();
        return StandardResponse.<JwtResponse>builder()
                .status(Status.SUCCESS)
                .message("SIGN UP SUCCESSFULLY!")
                .data(jwtResponse)
                .build();
    }

    public void checkUserEmailAndPhoneNumber(String email, String phoneNumber) {
        EmployeeEntity employee = employeeRepository.findEmployeeEntityByEmail(email);
        if (employee!=null){
            throw new UserBadRequestException("Email has already exist!");
        }
        if (employeeRepository.findEmployeeEntityByNumber(phoneNumber).isPresent()){
            throw new UserBadRequestException("Number has already exist!");
        }
    }
    public StandardResponse<JwtResponse> login(LoginDto loginDto) {
        EmployeeEntity employee = employeeRepository.findEmployeeEntityByEmail(loginDto.getEmail());
        if (employee == null){
            throw new DataNotFoundException("Employee not found!");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), employee.getPassword())){
            String accessToken= jwtService.generateAccessToken(employee);
            String refreshToken= jwtService.generateRefreshToken(employee);
            EmployeeForFront employeeForFront = modelMapper.map(employee, EmployeeForFront.class);
            JwtResponse jwtResponse= JwtResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .employeeForFront(employeeForFront)
                    .build();
            return StandardResponse.<JwtResponse>builder()
                    .status(Status.SUCCESS)
                    .message("SIGN IN SUCCESSFULLY!")
                    .data(jwtResponse)
                    .build();
    } else{
            throw new AuthenticationFailedException("Something error during signed in!");
        }
    }

    public StandardResponse<EmployeeForFront> getById(UUID id){
        EmployeeEntity employee= employeeRepository.getEmployeeEntityById(id);
        if (employee==null){
            throw new DataNotFoundException("Employee not found!");
        }
        EmployeeForFront employeeForFront = modelMapper.map(employee, EmployeeForFront.class);
        return StandardResponse.<EmployeeForFront>builder()
                .status(Status.SUCCESS)
                .data(employeeForFront)
                .message("Completed!")
                .build();
    }

    public StandardResponse<String> deleteById(UUID id, Principal principal){
        EmployeeEntity employee = employeeRepository.findEmployeeEntityByEmail(principal.getName());
        EmployeeEntity employee1 = employeeRepository.getEmployeeEntityById(id);
        if (employee1==null){
            throw new DataNotFoundException("Employee not found!");
        }
        employee1.setDeleted(true);
        employee1.setDeletedBy(employee.getId());
        employee1.setDeletedTime(LocalDateTime.now());
        employeeRepository.save(employee1);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .data("DELETED")
                .message("employee deleted")
                .build();

    }

    public void getAllEmployeeExcel(HttpServletResponse response,String language) throws IOException {
        List<EmployeeEntity> employeeList = employeeRepository.getAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Full name");
        row.createCell(1).setCellValue("Email");
        row.createCell(2).setCellValue("Phone Number");
        row.createCell(3).setCellValue("Position");
        row.createCell(4).setCellValue("Age");

        int dataRowIndex = 1;
        for (EmployeeEntity employee: employeeList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            if (language.equals("eng")){
            dataRow.createCell(0).setCellValue(employee.getFullNameEng());
            }
            if (language.equals("uz")){
                dataRow.createCell(0).setCellValue(employee.getFullNameUz());
            }
            if (language.equals("ru")){
                dataRow.createCell(0).setCellValue(employee.getFullNameRu());
            }
            dataRow.createCell(1).setCellValue(employee.getEmail());
            dataRow.createCell(2).setCellValue(employee.getPhoneNumber());
            dataRow.createCell(3).setCellValue(String.valueOf(employee.getPosition()));
            dataRow.createCell(4).setCellValue((employee.getAge()));
            dataRowIndex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    public StandardResponse<EmployeeForFront> update(EmployeeDto employeeDto,UUID id){
        EmployeeEntity employee = employeeRepository.getEmployeeEntityById(id);
        if (employee==null){
            throw new DataNotFoundException("employee not found!");
        }
        employee.setAge(employeeDto.getAge());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setEmail(employeeDto.getEmail());
        employee.setFullNameUz(employeeDto.getFullNameUz());
        employee.setFullNameRu(employeeDto.getFullNameRu());
        employee.setFullNameEng(employeeDto.getFullNameEng());
        employee.setUpdatedTime(LocalDateTime.now());
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setRole(UserRole.EMPLOYEE);
        EmployeeEntity save= employeeRepository.save(employee);
        EmployeeForFront employeeForFront = modelMapper.map(save, EmployeeForFront.class);
        return StandardResponse.<EmployeeForFront>builder()
                .status(Status.SUCCESS)
                .data(employeeForFront)
                .message("updated successfully!")
                .build();
    }

    public StandardResponse<List<EmployeeEntity>> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<EmployeeEntity> employeeEntities = employeeRepository.findAllEmployee(pageable).getContent();
        return StandardResponse.<List<EmployeeEntity>>builder()
                .status(Status.SUCCESS)
                .data(employeeEntities)
                .message("employee list "+page+"-page")
                .build();
    }

    public StandardResponse<String> applyToAdmin(String email){
        EmployeeEntity employee = employeeRepository.findEmployeeEntityByEmail(email);
        if (employee==null){
            throw new DataNotFoundException("employee not found!");
        }
        employee.setRole(UserRole.ADMIN);
        employeeRepository.save(employee);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .data("COMPLETED!")
                .message("employee role changed to admin!")
                .build();
    }

    public StandardResponse<String> deleteEmployeeByPosition(String name,Principal principal){
        List<EmployeeEntity> employeeEntities = employeeRepository.getAll();
        EmployeeEntity employeeEntity = employeeRepository.findEmployeeEntityByEmail(principal.getName());
        for (EmployeeEntity employee: employeeEntities) {
            if (employee.getPosition().getName().equals(name)){
                employee.setDeleted(true);
                employee.setDeletedTime(LocalDateTime.now());
                employee.setDeletedBy(employeeEntity.getId());
                employeeRepository.save(employee);
            }
        }
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .data("DELETED")
                .message("employees deleted")
                .build();
    }
}
