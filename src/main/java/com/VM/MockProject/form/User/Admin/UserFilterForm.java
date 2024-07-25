package com.VM.MockProject.form.User.Admin;

import com.VM.MockProject.enums.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFilterForm {
    private RoleName role;
}
