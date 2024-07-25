package com.VM.MockProject.form.User.User;

import com.VM.MockProject.form.User.CreatingUserForm;
import com.VM.MockProject.validation.user.UserPhoneNotExists;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CreatingUserRegistForm extends CreatingUserForm {
    @NotBlank(message = "{User.createUser.form.password.NotBlank}")
    @Length(min = 6, max = 20, message = "{User.createUser.form.password.LengthRange}")
    private String password;

    @NotBlank(message = "{User.createUser.form.phone.NotBlank}")
    @Length(min = 10, max = 15, message = "{User.createUser.form.phone.LengthRange}")
    @UserPhoneNotExists(message = "User.createUser.form.phone.Exists")
    private String phone;

    @NotBlank(message = "{User.createUser.form.address.NotBlank}")
    @Length(max = 250, message = "{User.createUser.form.address.LengthRange}")
    private String address;


}
