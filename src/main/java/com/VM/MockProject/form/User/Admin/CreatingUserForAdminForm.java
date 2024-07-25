package com.VM.MockProject.form.User.Admin;

import com.VM.MockProject.form.User.CreatingUserForm;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreatingUserForAdminForm extends CreatingUserForm {

    @NotEmpty(message = "{User.createUser.form.role.NotEmpty}")
    private List<Integer> roleIds;

    private String password = "123456"; // Set default password

}
