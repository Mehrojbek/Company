package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    @NotNull(message = "corp name should not be empty")
    private String corpName;

    @NotNull(message = "director name should not be empty")
    private String directorName;

    @NotNull(message = "street name should not be empty")
    private String street;

    @NotNull(message = "home number should not be empty")
    private String homeNumber;
}
