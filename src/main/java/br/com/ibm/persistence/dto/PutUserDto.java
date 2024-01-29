package br.com.ibm.persistence.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PutUserDto {
    @NotBlank(message = "Name is Required")
    private String name;

    @NotNull(message = "Age is Required")
    @Min(value = 16, message = "Age must be 16 or greater")
    @Max(value = 200, message = "value invalid")
    private Integer age;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "\\d{2,3}\\d{4,5}\\d{5}", message = "Invalid Phone Number format")
    private String phone;

    @NotBlank(message = "Address is Required")
    private String address;
}
