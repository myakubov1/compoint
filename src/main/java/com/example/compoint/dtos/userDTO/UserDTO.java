package com.example.compoint.dtos.userDTO;

import com.example.compoint.dtos.RoleResponse;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<RoleResponse> roles;
    private UserInfoDTO userInfo;
}
