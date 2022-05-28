package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.UserDTO;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Response<UserDTO>> create(@RequestBody UserDTO userDTO) {

        UserDTO userCreated = this.userService.create(userDTO);
        Response<UserDTO> response = new Response<>();
        response.setData(userCreated);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<UserDTO>>> list() {
        List<UserDTO> userDTOList = this.userService.list();

        Response<List<UserDTO>> response = new Response<>();
        response.setData(userDTOList);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> findById(@PathVariable Long id) {
        UserDTO userDTO = this.userService.findById(id);

        Response<UserDTO> response = new Response<>();
        response.setData(userDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO userDTOUpdated = this.userService.update(id, userDTO);

        Response<UserDTO> response = new Response<>();
        response.setData(userDTOUpdated);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {
        this.userService.delete(id);

        Response<Boolean> response = new Response<>();
        response.setData(Boolean.TRUE);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
