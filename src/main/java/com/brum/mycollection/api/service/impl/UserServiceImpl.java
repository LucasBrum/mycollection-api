package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.domain.user.User;
import com.brum.mycollection.api.exception.UserException;
import com.brum.mycollection.api.mapper.ItemMapper;
import com.brum.mycollection.api.mapper.UserMapper;
import com.brum.mycollection.api.model.request.UserRequest;
import com.brum.mycollection.api.model.response.UserResponse;
import com.brum.mycollection.api.repository.UserRepository;
import com.brum.mycollection.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.info("Creating a new user {}.", userRequest.username());
        try {
            User user = UserMapper.toEntity(userRequest);
            user.setPassword(encoder.encode(user.getPassword()));
            this.userRepository.save(user);

            UserResponse userResponse = UserMapper.toResponse(user);
            return userResponse;
        } catch (Exception e) {
            throw new UserException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("Updating User.");
        User user = UserMapper.toEntity(userRequest);
        this.findById(id);
        try {
            user.setId(id);
            this.userRepository.save(user);
            UserResponse userResponse = UserMapper.toResponse(user);
            return userResponse;
        } catch (Exception e) {
            throw new UserException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("Searching User by Id {}", id);
        try {
            Optional<User> userOptional = this.userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserResponse userResponse = UserMapper.toResponse(userOptional.get());
                return userResponse;
            }

            throw new UserException("User not found.", HttpStatus.NOT_FOUND);

        } catch (UserException cex) {
            throw cex;
        } catch (Exception e) {
            throw new UserException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse findByUsername(String username) {
        log.info("Searching by Username.");
        try{
            Optional<User> userOptional = this.userRepository.findByUsername(username);
            if(userOptional.isPresent()) {
                UserResponse userResponse = UserMapper.toResponse(userOptional.get());

                return userResponse;
            }
            throw new UserException("User not found.", HttpStatus.NOT_FOUND);

        } catch (UserException cex) {
            throw cex;
        } catch (Exception e) {
            throw new UserException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserResponse> listAll() {
        log.info("Listing All Users.");
        try {
            List<User> userList = this.userRepository.findAll();
            return UserMapper.toResponseList(userList);
        } catch (Exception e) {
            throw new UserException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting User by Id {}", id);
        try {
            this.findById(id);

            this.userRepository.deleteById(id);
        } catch (UserException ce) {
            throw new UserException("Internal Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
