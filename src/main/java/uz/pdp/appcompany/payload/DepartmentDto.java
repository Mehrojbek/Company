package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "name should not be empty")
    private String name;

    @NotNull(message = "company id should not be empty")
    private Integer companyId;
}
