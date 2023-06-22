package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.UserDTO;
import com.brum.mycollection.api.domain.user.User;
import com.brum.mycollection.api.exception.UserException;
import com.brum.mycollection.api.mapper.UserMapper;
import com.brum.mycollection.api.model.request.UserRequest;
import com.brum.mycollection.api.model.response.UserResponse;
import com.brum.mycollection.api.repository.UserRepository;
import com.brum.mycollection.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
        this.encoder = encoder;
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        try {
            User user = UserMapper.toEntity(userRequest);
            user.setPassword(encoder.encode(user.getPassword()));
            this.userRepository.save(user);

            UserResponse userResponse = UserMapper.toResponse(user);
            return userResponse;
        } catch (Exception e) {
            throw new UserException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        User user = UserMapper.toEntity(userRequest);
        this.findById(id);
        try {
            user.setId(id);
            this.userRepository.save(user);
            UserResponse userResponse = UserMapper.toResponse(user);
            return userResponse;
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse findById(Long id) {
        try {
            Optional<User> userOptional = this.userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserResponse userResponse = UserMapper.toResponse(userOptional.get());
                return userResponse;
            }

            throw new UserException("Usuário não encontrado.", HttpStatus.NOT_FOUND);

        } catch (UserException cex) {
            throw cex;
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse findByUsername(String username) {
        try{
            Optional<User> userOptional = this.userRepository.findByUsername(username);
            if(userOptional.isPresent()) {
                UserResponse userResponse = UserMapper.toResponse(userOptional.get());

                return userResponse;
            }
            throw new UserException("Usuário não encontrado.", HttpStatus.NOT_FOUND);

        } catch (UserException cex) {
            throw cex;
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserResponse> list() {
        try {
            List<User> users = this.userRepository.findAll();
            return this.mapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            this.findById(id);

            this.userRepository.deleteById(id);
        } catch (UserException ce) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
