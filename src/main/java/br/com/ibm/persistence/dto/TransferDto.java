package br.com.ibm.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferDto {
    @NotBlank(message = "Source account number is Required")
    String sourceAccountNumber;

    @NotBlank(message = "Target account number is Required")
    String targetAccountNumber;

    @NotNull(message = "Value is Required")
    @Min(value = 1, message = "Value must be greater than or equal to 0")
    private Double value;
}
