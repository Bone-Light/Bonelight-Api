package com.example.DAO.dto.AccountDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@NotNull
public class AskCodeDTO {
    @Email
    String email;
    @Pattern(regexp = "reset|register", message = "类型必须是reset或register")
    String type;
}
