package avlyakulov.timur.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Schema(
            description = "Name of the customer",
            example = "Dima"
    )
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer",
            example = "dima@gmail.com"
    )
    @Email(message = "Email address should be valid value")
    @NotEmpty(message = "Email address can not be a null or empty")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "4433"
    )
    @Pattern(regexp = "(^$|[0-9]{4})", message = "Mobile number must be 4 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDto accountsDto;
}