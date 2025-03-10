package com.fwitter.FwitterBackend.dto.login;

import com.fwitter.FwitterBackend.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponseDTO {

    private ApplicationUser user;
    private String token;
}
