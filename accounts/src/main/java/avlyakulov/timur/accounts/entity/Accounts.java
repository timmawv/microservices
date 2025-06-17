package avlyakulov.timur.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Accounts extends BaseEntity {

    @Id
    @Column(name = "account_number")
    private Long accountNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "branch_address")
    private String branchAddress;
    @Column(name = "customer_id")
    private Long customerId;
}
