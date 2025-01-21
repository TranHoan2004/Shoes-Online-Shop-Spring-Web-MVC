package com.HE180030.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private int id;
    private String username;
    private String password;
    private Boolean isSell;
    private Boolean isAdmin;
    private String email;
}
