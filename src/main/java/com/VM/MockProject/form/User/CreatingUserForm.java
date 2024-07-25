package com.VM.MockProject.form.User;

import com.VM.MockProject.validation.user.UserEmailNotExists;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreatingUserForm {

    @NotBlank(message = "{User.createUser.form.password.NotBlank}")
    @Length(min = 6, max = 100, message = "{User.createUser.form.password.LengthRange}")
    @Email(message = "{User.createUser.form.email.pattern}")
    @UserEmailNotExists(message = "{User.createUser.form.email.Exists}")
    private String email;

    @NotBlank(message = "{User.createUser.form.fullName.NotBlank}")
    @Length(max = 50, message = "{User.createUser.form.fullName.MaxLength}")
    private String fullName;

    private LocalDateTime createAt = LocalDateTime.now();
}
