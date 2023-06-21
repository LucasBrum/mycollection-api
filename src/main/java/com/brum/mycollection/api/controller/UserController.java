package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.dto.UserDTO;
import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.model.request.UserRequest;
import com.brum.mycollection.api.model.response.UserResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Response<UserResponse>> create(@RequestBody UserRequest userRequest) {
        UserResponse userCreated = this.userService.create(userRequest);
        Response<UserResponse> response = new Response<>();
        response.setData(userCreated);
        response.setStatusCode(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<UserResponse>>> list() {
        List<UserResponse> userResponseList = this.userService.list();

        Response<List<UserResponse>> response = new Response<>();
        response.setData(userResponseList);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserResponse>> findById(@PathVariable Long id) {
        UserResponse userResponse = this.userService.findById(id);

        Response<UserResponse> response = new Response<>();
        response.setData(userResponse);
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserResponse>> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse userUpdated = this.userService.update(id, userRequest);

        Response<UserResponse> response = new Response<>();
        response.setData(userUpdated);
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

    @GetMapping("validate-password")
    public ResponseEntity<Response<Boolean>> validatePassword(@RequestParam String username, @RequestParam String password) {
        UserResponse user = userService.findByUsername(username);
        Response<Boolean> response = new Response<>();
        if (user == null) {
            response.setData(false);
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        boolean valid = encoder.matches(password, user.password());
        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        response.setData(valid);
        response.setStatusCode(status.value());

        return new ResponseEntity<>(response, status);
    }
}
