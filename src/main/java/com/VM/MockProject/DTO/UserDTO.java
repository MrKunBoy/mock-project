package com.VM.MockProject.DTO;

import com.VM.MockProject.Entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {

    private Integer id;

    private String username;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private List<Role> roles;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}