package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull(message = "full name should not be empty")
    private String fullName;

    @NotNull(message = "phone number should not be empty")
    private String phoneNumber;

    @NotNull(message = "street name should not be empty")
    private String street;

    @NotNull(message = "home number should not be empty")
    private String homeNumber;

    @NotNull(message = "department id should not be empty")
    private Integer departmentId;
}
