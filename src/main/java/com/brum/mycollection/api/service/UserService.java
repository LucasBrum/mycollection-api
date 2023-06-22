package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.UserDTO;
import com.brum.mycollection.api.model.request.UserRequest;
import com.brum.mycollection.api.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest userRequest);

    UserResponse update(Long id, UserRequest userRequest);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    List<UserResponse> listAll();

    void delete(Long id);
}
