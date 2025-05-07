package com.example.DAO.dto.AccountDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountResetPwdDTO {
    @Email
    String email;
    @Length(min = 6, max = 6)
    String code;
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*()\\-_+=]")
    @Length(min = 6, max = 30)
    String password;
}
