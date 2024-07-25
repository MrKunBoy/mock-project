package com.VM.MockProject.form.User.Admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DeleteUserForm {
    private List<Integer> ids;
}
