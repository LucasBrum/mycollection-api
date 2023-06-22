package com.brum.mycollection.api.mapper;

import com.brum.mycollection.api.domain.user.User;
import com.brum.mycollection.api.model.request.UserRequest;
import com.brum.mycollection.api.model.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public static UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
        return userResponse;
    }

    public static User toEntity(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .email(userRequest.email())
                .build();

        return user;
    }

    public static List<UserResponse> toResponseList(List<User> userList) {
        List<UserResponse> userResponseList = userList.stream().map(UserMapper::toResponse).toList();

        return userResponseList;
    }
}