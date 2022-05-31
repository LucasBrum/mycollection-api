package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO create(UserDTO userDTO);

    UserDTO update(Long id, UserDTO userDTO);

    UserDTO findById(Long id);

    UserDTO findByUsername(String username);

    List<UserDTO> list();

    void delete(Long id);
}
