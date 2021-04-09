package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;


    /**
     * GET ALL WORKERS
     * @return WORKER LIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(workerService.getAll());
    }


    /**
     * GET ONE WORKER
     * @param id
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }


    /**
     * ADD WORKER WITH WORKER_DTO
     * @param workerDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.add(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }



    /**
     * DELETE WITH ID
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }



    /**
     * EDIT WORKER WITH ID AND WORKER_DTO
     * @param id
     * @param workerDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.edit(id, workerDto);
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
