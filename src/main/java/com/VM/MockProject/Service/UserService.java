package com.VM.MockProject.Service;


import com.VM.MockProject.Entity.Role;
import com.VM.MockProject.Entity.User;
import com.VM.MockProject.Repository.IRoleRepository;
import com.VM.MockProject.Repository.IUserRepository;
import com.VM.MockProject.Service.Interface.IUserService;
import com.VM.MockProject.Specification.User.UserSpecification;
import com.VM.MockProject.enums.RoleName;
import com.VM.MockProject.form.User.Admin.CreatingUserForAdminForm;
import com.VM.MockProject.form.User.Admin.UpdatingUserFormAdmin;
import com.VM.MockProject.form.User.Admin.UserFilterForm;
import com.VM.MockProject.form.User.User.CreatingUserRegistForm;
import com.VM.MockProject.form.User.User.UpdatingUserFormUser;
import com.VM.MockProject.form.User.User.UpdatingUserPasswordForm;
import com.VM.MockProject.utils.UniqueRandomStringGenerator;
import com.VM.MockProject.utils.UsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Page<User> getAllUsers(Pageable pageable, String search, UserFilterForm filterForm) {
        Specification<User> where = UserSpecification.buildWhere(search, filterForm);
   return userRepository.findAll(where, pageable);

    }

    @Override
    public User getUserByID(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User createUser(CreatingUserForAdminForm form) {
        User user = new User();

        String character = new UniqueRandomStringGenerator().uniqueRandomString(3);
        String username = UsernameUtil.standardizeUsername(form.getFullName()) + "_" + character;

        user.setUsername(username);
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFullName(form.getFullName());

        List<Role> roles = new ArrayList<>();
        for (Integer roleid : form.getRoleIds()) {
            Role role = roleRepository.findById(roleid)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + roleid));
            roles.add(role);
        }
        user.setRoles(roles);
        user.setCreatedAt(form.getCreateAt());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User registUser(CreatingUserRegistForm form) {
        User user = new User();

        String character = new UniqueRandomStringGenerator().uniqueRandomString(3);
        String username = UsernameUtil.standardizeUsername(form.getFullName()) + "_" + character;

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setEmail(form.getEmail());
        user.setFullName(form.getFullName());
        user.setPhone(form.getPhone());
        user.setAddress(form.getAddress());

        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName(RoleName.USER);
        roles.add(role);

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User updateUserByAdmin(Integer id, UpdatingUserFormAdmin form) {
        User user = userRepository.findById(id).get();
        if(user == null) {
            throw new RuntimeException("User not found");
        }
        if (userRepository.existsByPhone(form.getPhone()) && ((form.getPhone()) != (user.getPhone()))) {
            throw new RuntimeException("Phone number already exists");
        } else {
            user.setPhone(form.getPhone());
            user.setFullName(form.getFullName());
            user.setAddress(form.getAddress());

            List<Role> roles = new ArrayList<>();
            for (Integer roleid : form.getRoleIds()) {
                Role role = roleRepository.findById(roleid)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + roleid));
                roles.add(role);
            }
            user.setRoles(roles);
            user.setUpdatedAt(form.getUpdateAt());

            return userRepository.save(user);
        }
    }

    @Override
    public User updateUserByUser(Integer id, UpdatingUserFormUser form) {
        User user = userRepository.findById(id).get();
        if(user == null) {
            throw new RuntimeException("User not found");
        }
        if ((form.getPhone().equals(user.getPhone()))) {

            user.setFullName(form.getFullName());
            user.setAddress(form.getAddress());

            user.setUpdatedAt(form.getUpdateAt());
        } else {
            if(userRepository.existsByPhone(form.getPhone())){
                throw new RuntimeException("Phone number already exists");
            }
            user.setPhone(form.getPhone());
            user.setFullName(form.getFullName());
            user.setAddress(form.getAddress());
            user.setUpdatedAt(form.getUpdateAt());
        }

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(Integer id, UpdatingUserPasswordForm form) {
        User user = userRepository.findById(id).get();
        if(user == null) {
            throw new IllegalArgumentException("User Invalid");
        }

        String oldPassword = form.getOldPassword();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        user.setUpdatedAt(form.getUpdateAt());

        return userRepository.save(user);

    }

    @Override
    public void deleteUsers(List<Integer> idList) {
        List<User> users = userRepository.getListUserByListId(idList);
        for (User user : users) {
            for (Role role : new HashSet<>(user.getRoles())) {
                user.removeRole(role);
            }
        }
        userRepository.deleteAllById(idList);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Remove roles from the user
        for (Role role : new HashSet<>(user.getRoles())) {
            user.removeRole(role);
        }

        // Now delete the user
        userRepository.deleteById(id);
    }

    @Override
    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUserExistsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUserExistsByID(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public int getCountIdForDelete(List<Integer> ids) {
        return userRepository.getCountIdForDelete(ids);
    }

//    @Override
//    public Page<User> getListUserByRoleId(Pageable pageable, Integer roleId) {
//        if(roleId != null) {
//            return userRepository.getListUserByRoleId(pageable, roleId);
//        } else {
//            return userRepository.findAll(pageable);
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
            }
        }
        return new CustomUserDetails(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }
}
