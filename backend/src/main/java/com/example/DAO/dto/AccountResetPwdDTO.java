package com.example.DAO.dto;

import lombok.Data;

@Data
public class AccountResetPwdDTO {
    String email;
    String code;
    String password;
}
