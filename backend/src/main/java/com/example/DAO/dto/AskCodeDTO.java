package com.example.DAO.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@NotNull
public class AskCodeDTO {
    @Email
    String code;
    @Pattern(regexp = "reset | register")
    String type;
}
