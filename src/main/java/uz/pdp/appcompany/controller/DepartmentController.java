package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;


    /**
     * RETURN ALL DEPARTMENT
     * @return DEPARTMENT LIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Department> departmentList = departmentService.getAll();
        return ResponseEntity.ok(departmentList);
    }



    /**
     * GET ONE DEPARTMENT WITH ID
     * @param id
     * @return apiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse.isSuccess()?apiResponse.getObject():apiResponse);
    }

    /**
     * ADD DEPARTMENT WITH COMPANY_ID AND DEPARTMENT_DTO
     * @param departmentDto
     * @return apiResponse
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.add(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }



    /**
     * DELETE DEPARTMENT WITH ID
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }





    /**
     * EDIT DEPARTMENT WITH ID AND DEPARTMENT_DTO
     * @param id
     * @param departmentDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.edit(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
