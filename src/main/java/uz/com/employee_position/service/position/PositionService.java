package uz.com.employee_position.service.position;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.employee_position.exception.DataHasAlreadyExistException;
import uz.com.employee_position.exception.DataNotFoundException;
import uz.com.employee_position.exception.UserBadRequestException;
import uz.com.employee_position.model.dto.request.PositionDto;
import uz.com.employee_position.model.dto.response.PositionForFront;
import uz.com.employee_position.model.entity.EmployeeEntity;
import uz.com.employee_position.model.entity.PositionEntity;
import uz.com.employee_position.repository.EmployeeRepository;
import uz.com.employee_position.repository.PositionRepository;
import uz.com.employee_position.model.dto.response.StandardResponse;
import uz.com.employee_position.model.dto.response.Status;
import uz.com.employee_position.service.position.PositionServiceImpl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PositionService implements PositionServiceImpl {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;




    public StandardResponse<PositionForFront> save(PositionDto positionDto, Principal principal){
        EmployeeEntity employee = employeeRepository.findEmployeeEntityByEmail(principal.getName());
        checkHasPosition(positionDto.getName());
        PositionEntity position = modelMapper.map(positionDto, PositionEntity.class);
        position.setName(positionDto.getName());
        position.setCreatedBy(employee.getId());
        PositionEntity positionEntity = positionRepository.save(position);
        PositionForFront positionForFront = modelMapper.map(positionEntity, PositionForFront.class);

        return StandardResponse.<PositionForFront>builder()
                .status(Status.SUCCESS)
                .data(positionForFront)
                .message("position saved!")
                .build();
    }






    public StandardResponse<PositionForFront> getById(UUID id){
        PositionEntity position = positionRepository.findPositionEntityById(id);
        if (position==null){
            throw new DataNotFoundException("position not found!");
        }
        PositionForFront positionForFront = modelMapper.map(position, PositionForFront.class);
        return StandardResponse.<PositionForFront>builder()
                .status(Status.SUCCESS)
                .data(positionForFront)
                .message("this is position!")
                .build();
    }






    public StandardResponse<String> deleteById(UUID id,Principal principal){
        EmployeeEntity employee = employeeRepository.findEmployeeEntityByEmail(principal.getName());
        PositionEntity position = positionRepository.findPositionEntityById(id);
        if (position==null){
            throw new DataNotFoundException("position not found!");
        }
        position.setDeleted(true);
        position.setDeletedBy(employee.getId());
        position.setDeletedTime(LocalDateTime.now());
        positionRepository.save(position);

        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .data("DELETED")
                .message("position deleted!")
                .build();
    }





    public void checkHasPosition(String name){
        PositionEntity position = positionRepository.findPositionEntityByName(name);
        if (position!=null){
            throw new DataHasAlreadyExistException("position has already exist");
        }
    }






    public StandardResponse<List<PositionEntity>> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<PositionEntity> position = positionRepository.getAll(pageable).getContent();
        return StandardResponse.<List<PositionEntity>>builder()
                .status(Status.SUCCESS)
                .data(position)
                .message("position list "+page+"-page")
                .build();
    }

}
