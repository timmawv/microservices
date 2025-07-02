package avlyakulov.timur.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDto {

    @Schema(
            description = "Account Number of Bank"
    )
    @NotEmpty(message = "Account number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of Bank",
            example = "Savings"
    )
    @NotEmpty(message = "Account type can not be a null or empty")
    private String accountType;


    @Schema(
            description = "Bank branch addresses",
            example = "123 New York"
    )
    @NotEmpty(message = "Branch address can not be a null or empty")
    private String branchAddress;
}