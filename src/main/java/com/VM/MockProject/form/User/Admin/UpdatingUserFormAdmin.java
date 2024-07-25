package com.VM.MockProject.form.User.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdatingUserFormAdmin {

    @NotBlank(message = "User.updatingUser.form.fullName.NotBlank")
    private String fullName;


    private String phone;

    private String address;

    @NotEmpty(message = "{User.updateUser.form.role.NotEmpty}")
    private List<Integer> roleIds;

    private LocalDateTime updateAt = LocalDateTime.now();

}
