package com.fwitter.FwitterBackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Date dob;


}
