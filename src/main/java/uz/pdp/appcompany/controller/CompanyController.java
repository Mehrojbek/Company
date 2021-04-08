package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    /**
     * GET ALL COMPANY
     * @return COMPANYLIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(companyService.getAll());
    }


    /**
     * GET COMPANY WITH ID
     * @param id
     * @return Company
     * on the way comes id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        Company company = companyService.getOne(id);
        return ResponseEntity.status(company!=null?200:404).body(company);
    }


    /**
     * ADD NEW COMPANY
     * @param companyDto
     * @return ApiResponse
     * on the way comes CompanyDto
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.add(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    /**
     * DELETE COMPANY WITH ID
     * @param id
     * @return ApiResponse
     * on the way comes id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){

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
