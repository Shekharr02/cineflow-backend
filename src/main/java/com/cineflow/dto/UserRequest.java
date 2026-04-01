package com.cineflow.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "{user.name.blank}")
    @Size(min = 3, max = 50, message = "{user.name.length}")
    private String name;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.blank}")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$", message = "{user.password.invalid}")
    private String password;
}
