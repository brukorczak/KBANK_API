package br.com.ibm.persistence.dto;

import br.com.ibm.persistence.model.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddAccountDto {
    @NotNull(message = "Account type is Required")
    private AccountType accountType;
}
