package com.example.DAO.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountRegisterDTO {
    @Email
    String email;
    @Length(min = 6, max = 6)
    String code;
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(min = 1, max = 30)
    String name;
    @Length(min = 6, max = 30)
    String password;
}
