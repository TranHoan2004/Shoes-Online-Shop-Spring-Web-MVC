package com.HE180030.dto.request;

public record UpdateAccountRequest(int id,
                                   String username,
                                   String password,
                                   String email,
                                   boolean isAdmin,
                                   boolean isSell) {

}
