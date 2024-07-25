package com.VM.MockProject.form.User.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdatingUserPasswordForm {

    @NotBlank(message = "{User.update.form.password.NotBlank}")
    @Length(min = 6, max = 20, message = "{User.update.form.password.LengthRange}")
    private String oldPassword;
    private String newPassword;

    private LocalDateTime updateAt = LocalDateTime.now();
}
