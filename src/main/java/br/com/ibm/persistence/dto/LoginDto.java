package br.com.ibm.persistence.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "CPF is Required")
    private String cpf;

    @NotBlank(message = "Password is Required")
    private String password;
}
