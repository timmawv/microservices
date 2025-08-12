package avlyakulov.timur.accounts.dto;

import avlyakulov.timur.accounts.dto.external.CardsDto;
import avlyakulov.timur.accounts.dto.external.LoansDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDto {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of name should be between 5 and 30")
    private String name;

    @Email(message = "Email address should be valid value")
    @NotEmpty(message = "Email address can not be a null or empty")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{4})", message = "Mobile number must be 4 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;

    private CardsDto cardsDto;

    private LoansDto loansDto;
}