package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.repository.CompanyRepository;
import uz.pdp.appcompany.repository.DepartmentRepository;

import java.util.*;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;


    /**
     * ADD DEPARTMENT WITH COMPANY_ID AND DEPARTMENT_DTO
     * @param departmentDto
     * @return ApiResponse
     */
    public ApiResponse add(DepartmentDto departmentDto){
        //CHECK UNIQUE DEPARTMENT AND COMPANY_ID
        boolean existsByNameAndCompanyId = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompanyId) {
            Department byNameAndCompanyId = departmentRepository.findByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
            //CHECK THIS DEPARTMENT IS_ACTIVE
            if (byNameAndCompanyId.isActive())
            return new ApiResponse("This department already exist", false);
            byNameAndCompanyId.setActive(true);
            departmentRepository.save(byNameAndCompanyId);
            return new ApiResponse("Department added",true);
        }

        //CHECK COMPANY
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found",false);
        if (!optionalCompany.get().isActive())
            return new ApiResponse("this company deleted",false);


        Department department=new Department(departmentDto.getName(), optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department added",true);
    }




    /**
     * RETURN ALL DEPARTMENT
     * @return DEPARTMENT LIST
     */
    public List<Department> getAll(){
        return departmentRepository.findAll();
    }




    /**
     * GET ONE DEPARTMENT WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("department not found",false);
        return new ApiResponse("successfully",true,optionalDepartment.get());
    }


    /**
     * DELETE DEPARTMENT WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("department not found",false);
        Department department = optionalDepartment.get();
        department.setActive(false);
        departmentRepository.save(department);
        return new ApiResponse("department deleted",true);
    }


    /**
     * EDIT DEPARTMENT WITH ID AND DEPARTMENT_DTO
     * @param id
     * @param departmentDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, DepartmentDto departmentDto){
        //CHECK THIS DEPARTMENT EXIST
        boolean exists = departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (exists)
            return new ApiResponse("this department already exist",false);

        //CHECK DEPARTMENT
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("department not found",false);

        //CHECK COMPANY
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("company not found",true);
        if (!optionalCompany.get().isActive())
            return new ApiResponse("Company deleted",false);

        Department department = optionalDepartment.get();
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("department edited",true);
    }


}
