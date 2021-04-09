package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.Deleted;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.repository.AddressRepository;
import uz.pdp.appcompany.repository.DepartmentRepository;
import uz.pdp.appcompany.repository.WorkerRepository;

import java.util.*;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;


    /**
     * ADD WORKER WITH WORKER_DTO
     * @param workerDto
     * @return ApiResponse
     */
    public ApiResponse add(WorkerDto workerDto){
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("this phone number already exist",false);

        //CHECK DEPARTMENT
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("department not found",false);
        if (!optionalDepartment.get().isActive())
            return new ApiResponse("this department deleted",false);

        //SAVE ADDRESS
        Address address=new Address(workerDto.getStreet(), workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Worker worker=new Worker();
        worker.setFullName(workerDto.getFullName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(savedAddress);
        worker.setDepartment(optionalDepartment.get());

        workerRepository.save(worker);
        return new ApiResponse("worker added",true);
    }



    /**
     * GET ALL WORKERS
     * @return WORKER LIST
     */
    public List<Worker> getAll(){
        return workerRepository.findAll();
    }


    /**
     * GET ONE WORKER
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("worker not found",false);
        return new ApiResponse("successfully",true,optionalWorker.get());
    }


    /**
     * DELETE WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("worker not found",false);
        Worker worker = optionalWorker.get();

        //CHECK HOW MANY DELETED WORKER
        long numberOfDeletedWorker = workerRepository.existsByPhoneNumberStartingWithAndPhoneNumberEndingWith(Deleted.DELETED, worker.getPhoneNumber())+1;
        worker.setActive(false);
        worker.setPhoneNumber(Deleted.DELETED+numberOfDeletedWorker+":"+worker.getPhoneNumber());
        workerRepository.save(worker);
        return new ApiResponse("worker deleted",true);
    }



    /**
     * EDIT WORKER WITH ID AND WORKER_DTO
     * @param id
     * @param workerDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, WorkerDto workerDto){
        //CHECK UNIQUE PHONE_NUMBER AND ID_NOT
        boolean numberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (!numberAndIdNot)
            return new ApiResponse("this worker already exist",false);

        //CHECK WORKER
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("worker not found",false);

        //CHECK DEPARTMENT
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("department mot found",false);
        if (!optionalDepartment.get().isActive())
            return new ApiResponse("department deleted",false);

        Worker worker = optionalWorker.get();
        worker.setFullName(workerDto.getFullName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());

        Address address = worker.getAddress();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        addressRepository.save(address);

        workerRepository.save(worker);
        return new ApiResponse("worker edited",true);
    }
}
