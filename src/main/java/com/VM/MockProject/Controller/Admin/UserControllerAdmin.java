package com.VM.MockProject.Controller.Admin;

import com.VM.MockProject.DTO.UserDTO;
import com.VM.MockProject.Entity.Role;
import com.VM.MockProject.Entity.User;
import com.VM.MockProject.Service.Interface.IUserService;
import com.VM.MockProject.form.MessageResponse;
import com.VM.MockProject.form.User.Admin.CreatingUserForAdminForm;
import com.VM.MockProject.form.User.Admin.DeleteUserForm;
import com.VM.MockProject.form.User.Admin.UpdatingUserFormAdmin;
import com.VM.MockProject.form.User.Admin.UserFilterForm;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "api/v1/admin/users")
@Validated
//@PreAuthorize("isAuthenticated()")
public class UserControllerAdmin {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUserService userService;



    @GetMapping
    public Page<UserDTO> getAllUsers(Pageable pageable, @RequestParam(value = "search", required = false) String search, UserFilterForm filterForm){
        Page<User> entityPages = userService.getAllUsers(pageable, search, filterForm);

        // convert entities --> dtos
        List<UserDTO> userListDto = new ArrayList<>();
        for (User userDB : entityPages) {
            UserDTO userDto = new UserDTO();
            userDto.setId(userDB.getId());
            userDto.setEmail(userDB.getEmail());
            userDto.setUsername(userDB.getUsername());
            userDto.setFullName(userDB.getFullName());
            userDto.setPhone(userDB.getPhone());
            userDto.setAddress(userDB.getAddress());
            userDto.setRoles(userDB.getRoles().stream().map(role->{
                Role role1 = new Role();
                role1.setRoleId(role.getRoleId());
                role1.setName(role.getName());
                return role1;
            }).collect(Collectors.toList()));
            userDto.add(linkTo(methodOn(UserControllerAdmin.class).getAllUsers(pageable, search, filterForm)).withSelfRel());

            userListDto.add(userDto);
        }

        List<UserDTO> dtos = modelMapper.map(userListDto, new TypeToken<List<UserDTO>>() {}.getType());

        Page<UserDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return dtoPages;

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable(name = "id") Integer id) {
        User entity = userService.getUserByID(id);

        List<Role> roles = new ArrayList<>();
        for (Role role1 : entity.getRoles()) {
            Role role = new Role();
            role.setRoleId(role1.getRoleId());
            role.setName(role1.getName());

            roles.add(role);
        }

        UserDTO userDto = new UserDTO();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setUsername(entity.getUsername());
        userDto.setFullName(entity.getFullName());
        userDto.setPhone(entity.getPhone());
        userDto.setAddress(entity.getAddress());
        userDto.setRoles(roles);

        userDto.add(linkTo(methodOn(UserControllerAdmin.class).getUserByID(id)).withSelfRel());

        return ResponseEntity.ok(userDto);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreatingUserForAdminForm userForm) {

        User userDB = userService.createUser(userForm);
        // Convert
        UserDTO userDto = new UserDTO();
        userDto.setId(userDB.getId());
        userDto.setUsername(userDB.getUsername());
        userDto.setFullName(userDB.getFullName());
        userDto.setEmail(userDB.getEmail());
        userDto.setPhone(userDB.getPhone());
        userDto.setAddress(userDB.getAddress());
        userDto.setRoles( userDB.getRoles().stream().map(role->{
            Role role1 = new Role();
            role1.setRoleId(role.getRoleId());
            role1.setName(role.getName());
            return role1;
        }).collect(Collectors.toList()));

        userDto.setCreatedAt(userDB.getCreatedAt());
        userDto.add(linkTo(methodOn(UserControllerAdmin.class).getUserByID(userDB.getId())).withSelfRel());
        return ResponseEntity.ok(userDto);

    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id,
                                                           @RequestBody UpdatingUserFormAdmin userForm) {
        try {
            User userDB = userService.getUserByID(id);
            if (userDB == null) {
                throw new BadRequestException("Id user is null");
            }

            User userUpdate = userService.updateUserByAdmin(id, userForm);

            // Convert
            UserDTO userDto = new UserDTO();
            userDto.setId(userDB.getId());
            userDto.setUsername(userDB.getUsername());
            userDto.setFullName(userDB.getFullName());
            userDto.setEmail(userDB.getEmail());
            userDto.setPhone(userDB.getPhone());
            userDto.setAddress(userDB.getAddress());
            userDto.setRoles( userDB.getRoles().stream().map(role->{
                Role role1 = new Role();
                role1.setRoleId(role.getRoleId());
                role1.setName(role.getName());
                return role1;
            }).collect(Collectors.toList()));

            userDto.setUpdatedAt(userDB.getUpdatedAt());
            userDto.add(linkTo(methodOn(UserControllerAdmin.class).getUserByID(userUpdate.getId())).withSelfRel());
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable(name = "id") Integer id) {
        try {
            User userDele = userService.getUserByID(id);
            if(userDele == null){
                throw new BadRequestException("Id user is null");
            }

            // Convert
            UserDTO userDto = new UserDTO();
            userDto.setId(userDele.getId());
            userDto.setUsername(userDele.getUsername());
            userDto.setFullName(userDele.getFullName());
            userDto.setEmail(userDele.getEmail());
            userDto.setPhone(userDele.getPhone());
            userDto.setAddress(userDele.getAddress());
            userDto.setRoles( userDele.getRoles().stream().map(role->{
                Role role1 = new Role();
                role1.setRoleId(role.getRoleId());
                role1.setName(role.getName());
                return role1;
            }).collect(Collectors.toList()));

            userService.deleteUser(id);

            userDto.add(linkTo(methodOn(UserControllerAdmin.class).getUserByID(userDele.getId())).withSelfRel());
            return ResponseEntity.ok(userDto);

        } catch (Exception e) {
            throw new RuntimeException("Error deleting user");
        }
    }

    @DeleteMapping()
    public MessageResponse deleteUsers(@RequestBody @Valid DeleteUserForm form) {
        userService.deleteUsers(form.getIds());
        return new MessageResponse("Delete list user successfully");
    }


}
