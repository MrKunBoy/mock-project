package com.VM.MockProject.Service.Interface;
import com.VM.MockProject.Entity.User;
import com.VM.MockProject.form.User.Admin.CreatingUserForAdminForm;
import com.VM.MockProject.form.User.Admin.UpdatingUserFormAdmin;
import com.VM.MockProject.form.User.Admin.UserFilterForm;
import com.VM.MockProject.form.User.User.CreatingUserRegistForm;
import com.VM.MockProject.form.User.User.UpdatingUserFormUser;
import com.VM.MockProject.form.User.User.UpdatingUserPasswordForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    public Page<User> getAllUsers(Pageable pageable, String search, UserFilterForm filterForm);

    public User getUserByID(int id);

    public User createUser(CreatingUserForAdminForm form);

    public User registUser(CreatingUserRegistForm form);

    public User updateUserByAdmin(Integer id, UpdatingUserFormAdmin form);

    public User updateUserByUser(Integer id, UpdatingUserFormUser form);

    public User updatePassword(Integer id, UpdatingUserPasswordForm form);

    public void deleteUsers(List<Integer> idList);

    public void deleteUser(Integer id);

    public boolean isUserExistsByUsername(String username);

    public boolean isUserExistsByEmail(String email);

    public boolean isUserExistsByPhone(String phone);

    public User getUserByUsername(String username);

    public boolean isUserExistsByID(Integer id);

    public int getCountIdForDelete(List<Integer> ids);

//    Page<User> getListUserByRoleId(Pageable pageable, Integer roleId);
}
