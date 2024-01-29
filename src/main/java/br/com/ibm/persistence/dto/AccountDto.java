package br.com.ibm.persistence.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
    private String accountNumber;
    private Double balance;
    @NotBlank(message = "Account Type is Required")
    private String accountType;
    private String userName;

}
