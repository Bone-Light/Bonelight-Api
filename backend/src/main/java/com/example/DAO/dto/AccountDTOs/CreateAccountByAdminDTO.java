package com.example.DAO.dto.AccountDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateAccountByAdminDTO {
    @Email
    String email;
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(min = 1, max = 30)
    String username;
    @Length(min = 6, max = 30)
    String password;
}
