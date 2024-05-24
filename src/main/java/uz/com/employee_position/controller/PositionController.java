package uz.com.employee_position.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.employee_position.model.dto.request.PositionDto;
import uz.com.employee_position.model.dto.response.PositionForFront;
import uz.com.employee_position.model.entity.PositionEntity;
import uz.com.employee_position.model.dto.response.StandardResponse;
import uz.com.employee_position.service.position.PositionServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/position")
public class PositionController {

    private final PositionServiceImpl positionServiceImpl;

    @PostMapping("/save")
    public StandardResponse<PositionForFront> save(
            @RequestBody PositionDto positionDto,
            Principal principal
            ){
        return positionServiceImpl.save(positionDto, principal);
    }

    @GetMapping("/get-by-id")
    public StandardResponse<PositionForFront> getById(
            @RequestParam UUID id
            ){
        return positionServiceImpl.getById(id);
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @RequestParam UUID id,
            Principal principal
    ){
        return positionServiceImpl.deleteById(id, principal);
    }

    @GetMapping("/get-all")
    public StandardResponse<List<PositionEntity>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return positionServiceImpl.getAll(page, size);
    }
}

