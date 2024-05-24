package uz.com.employee_position.service.position;

import org.springframework.stereotype.Service;
import uz.com.employee_position.model.dto.request.PositionDto;
import uz.com.employee_position.model.dto.response.PositionForFront;
import uz.com.employee_position.model.dto.response.StandardResponse;
import uz.com.employee_position.model.entity.PositionEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public interface PositionServiceImpl {

    StandardResponse<PositionForFront> save(PositionDto positionDto, Principal principal);

    StandardResponse<PositionForFront> getById(UUID id);

    StandardResponse<String> deleteById(UUID id,Principal principal);

    void checkHasPosition(String name);

    StandardResponse<List<PositionEntity>> getAll(int page, int size);
}
