package com.VM.MockProject.Controller.User;

import com.VM.MockProject.DTO.UserDTO;
import com.VM.MockProject.Entity.Role;
import com.VM.MockProject.Entity.User;
import com.VM.MockProject.Service.Interface.IUserService;
import com.VM.MockProject.form.User.User.CreatingUserRegistForm;
import com.VM.MockProject.form.User.User.UpdatingUserFormUser;
import com.VM.MockProject.form.User.User.UpdatingUserPasswordForm;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "api/v1/users")
@Validated
@CrossOrigin
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUserService userService;

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
        userDto.setCreatedAt(entity.getCreatedAt());
        userDto.setUpdatedAt(entity.getUpdatedAt());

        userDto.add(linkTo(methodOn(UserController.class).getUserByID(id)).withSelfRel());

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody CreatingUserRegistForm form) {
        User registeredUser = userService.registUser(form);
        // Convert
        UserDTO userDto = new UserDTO();
        userDto.setId(registeredUser.getId());
        userDto.setUsername(registeredUser.getUsername());
        userDto.setFullName(registeredUser.getFullName());
        userDto.setEmail(registeredUser.getEmail());
        userDto.setPhone(registeredUser.getPhone());
        userDto.setAddress(registeredUser.getAddress());
        userDto.setRoles( registeredUser.getRoles().stream().map(role->{
            Role role1 = new Role();
            role1.setRoleId(role.getRoleId());
            role1.setName(role.getName());
            return role1;
        }).collect(Collectors.toList()));

        userDto.setCreatedAt(registeredUser.getCreatedAt());
        userDto.add(linkTo(methodOn(UserController.class).getUserByID(registeredUser.getId())).withSelfRel());
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdatingUserFormUser updateUserRequest) {

        try {
            User userDB = userService.getUserByID(id);
            if (userDB == null) {
                throw new BadRequestException("Id user is null");
            }

            User userUpdate = userService.updateUserByUser(id, updateUserRequest);

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
            userDto.add(linkTo(methodOn(UserController.class).getUserByID(userUpdate.getId())).withSelfRel());
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Integer id, @RequestBody UpdatingUserPasswordForm updatePasswordRequest) {

        try {
            User userDB = userService.getUserByID(id);
            if (userDB == null) {
                throw new BadRequestException("Id user is null");
            }

            User userUpdate = userService.updatePassword(id, updatePasswordRequest);

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
            userDto.add(linkTo(methodOn(UserController.class).getUserByID(userUpdate.getId())).withSelfRel());
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
