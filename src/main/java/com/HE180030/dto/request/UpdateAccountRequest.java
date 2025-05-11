package com.HE180030.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountRequest {
    String username;
    String password;
    String email;
    int id;
    boolean isAdmin;
    boolean isSell;
}
