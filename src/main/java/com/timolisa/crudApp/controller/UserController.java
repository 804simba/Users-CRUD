package com.timolisa.crudApp.controller;

import com.timolisa.crudApp.dto.ResponseDto;
import com.timolisa.crudApp.dto.UserDto;
import com.timolisa.crudApp.exception.BadCredentialsException;
import com.timolisa.crudApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    @GetMapping("/user/{id}")
    public ResponseEntity<?> fetchUser(@PathVariable("id") Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<?> fetchUserByUsername(@PathVariable("username") String username) {
        UserDto userDto = userService.getUserByUsername(username.toLowerCase());

        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/users")
    public ResponseEntity<?> fetchAllUsers(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "4") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserDto> users =  userService.getPageOfUsers(pageable);
            return new ResponseEntity<>(users, HttpStatus.FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/new")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws BadCredentialsException {
        UserDto savedUser = userService.saveUser(userDto);
        return ResponseEntity.created(URI.create("/user/new" + userDto.getUsername())).body(savedUser);
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDto userDto,
                                      @PathVariable("id") Long userId) throws BadCredentialsException {
        ResponseDto response = userService.edit(userId, userDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        ResponseDto response = userService.deleteUser(userId);
        return ResponseEntity.ok().body(response);
    }
}
