package avlyakulov.timur.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
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
    @Column(name = "branch_address")
    private String branchAddress;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
