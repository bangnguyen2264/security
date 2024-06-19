package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String fullname;
    private String username;
    private String role;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .fullname(user.getLastname()+" "+user.getFirstname())
                .username(user.getUsername())
                .role(user.getRole().getName())
                .build();
    }
}
