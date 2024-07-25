package com.VM.MockProject.form.User.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdatingUserFormUser {
    @NotBlank(message = "User.updatingUser.form.fullName.NotBlank")
    private String fullName;

    @NotBlank(message = "User.updatingUser.form.phone.NotBlank")
    @Length(min = 10, max = 15, message = "{User.updatingUser.form.phone.LengthRange}")
    private String phone;

    @NotBlank(message = "User.updatingUser.form.address.NotBlank")
    private String address;

    private LocalDateTime updateAt = LocalDateTime.now();
}
