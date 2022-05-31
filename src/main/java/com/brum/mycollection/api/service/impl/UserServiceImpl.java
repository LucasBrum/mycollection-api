package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.dto.UserDTO;
import com.brum.mycollection.api.entity.User;
import com.brum.mycollection.api.exception.UserException;
import com.brum.mycollection.api.repository.UserRepository;
import com.brum.mycollection.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public UserDTO create(UserDTO userDTO) {
        try {
            User user = this.mapper.map(userDTO, User.class);

            this.userRepository.save(user);
            userDTO = mapper.map(user, UserDTO.class);

            return userDTO;
        } catch (UserException uex) {
            throw uex;
        } catch (Exception e) {
            throw new UserException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        this.findById(id);
        try {

            userDTO.setId(id);
            User user = mapper.map(userDTO, User.class);
            this.userRepository.save(user);
            userDTO = mapper.map(user, UserDTO.class);
            return userDTO;
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO findById(Long id) {
        try {
            Optional<User> userOptional = this.userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserDTO userDTO = mapper.map(userOptional.get(), UserDTO.class);
                return userDTO;
            }

            throw new UserException("Usuário não encontrado.", HttpStatus.NOT_FOUND);

        } catch (UserException cex) {
            throw cex;
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO findByUsername(String username) {
        try{
            Optional<User> userOptional = this.userRepository.findByUsername(username);
            if(userOptional.isPresent()) {
                UserDTO userDTO = mapper.map(userOptional.get(), UserDTO.class);

                return userDTO;
            }
            throw new UserException("Usuário com o username não encontrado.", HttpStatus.NOT_FOUND);

        } catch (UserException cex) {
            throw cex;
        } catch (Exception e) {
            throw new UserException("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserDTO> list() {
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
