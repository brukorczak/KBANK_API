package br.com.ibm.persistence.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "CPF is Required")
    @CPF
    private String cpf;

    @NotBlank(message = "Password is Required")
    private String password;
}
