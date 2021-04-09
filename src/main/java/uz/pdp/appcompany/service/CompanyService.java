package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.payload.Deleted;
import uz.pdp.appcompany.repository.AddressRepository;
import uz.pdp.appcompany.repository.CompanyRepository;

import java.util.*;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;


    /**
     * ADD NEW COMPANY
     * @param companyDto
     * @return ApiResponse
     * on the way comes CompanyDto
     */
    public ApiResponse add(CompanyDto companyDto){
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (existsByCorpName)
            return new ApiResponse("This company already exist",false);
        Address address=new Address(companyDto.getStreet(), companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);


        Company company=new Company(companyDto.getCorpName(), companyDto.getDirectorName(), savedAddress);
        companyRepository.save(company);
        return new ApiResponse("Company successfully created",true);
    }



    /**
     * GET ALL COMPANY
     * @return COMPANYLIST
     */
    public List<Company> getAll(){
        return companyRepository.findAll();
    }



    /**
     * GET ONE COMPANY
     * @param id
     * @return Company
     * on the way comes id
     */
    public Company getOne(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }




    /**
     * DELETE COMPANY WITH ID
     * @param id
     * @return ApiResponse
     * on the way comes id
     */
    public ApiResponse delete(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company noy found",false);
        Company company = optionalCompany.get();
        long numberOfDeletedCompany = companyRepository.countAllByCorpNameStartingWithAndCorpNameEndingWith(Deleted.DELETED, company.getCorpName())+1;
        company.setCorpName(Deleted.DELETED+numberOfDeletedCompany+":"+company.getCorpName());
        company.setActive(false);
        companyRepository.save(company);
        return new ApiResponse("Company deleted",true);
    }


    /**
     * EDIT COMPANY WITH ID AND COMPANYDTO
     * @param id
     * @param companyDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, CompanyDto companyDto){
        boolean nameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (nameAndIdNot)
            return new ApiResponse("This company already exist",false);
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found",false);
        Company company = optionalCompany.get();
        company.setDirectorName(companyDto.getDirectorName());
        company.setCorpName(companyDto.getCorpName());

        Address address = company.getAddress();
        address.setHomeNumber(companyDto.getHomeNumber());
        address.setStreet(companyDto.getStreet());

        companyRepository.save(company);

        return new ApiResponse("Company edited",true);
    }

}
