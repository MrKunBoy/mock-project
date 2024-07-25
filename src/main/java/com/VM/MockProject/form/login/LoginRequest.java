package com.VM.MockProject.form.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest {
    @NotBlank(message = "{User.login.form.usernameOrEmail.NotBlank}")
    @Length(min = 6, max = 100, message = "{User.login.form.username.LengthRange}")
    private String usernameOrEmail;

    @NotBlank(message = "{User.login.form.password.NotBlank}")
    @Length(min = 6, max = 20, message = "{User.login.form.password.LengthRange}")
    private String password;
}
