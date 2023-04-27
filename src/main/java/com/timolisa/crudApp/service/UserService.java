package com.timolisa.crudApp.service;

import com.timolisa.crudApp.dto.ResponseDto;
import com.timolisa.crudApp.dto.UserDto;
import com.timolisa.crudApp.exception.BadCredentialsException;
import com.timolisa.crudApp.model.User;
import com.timolisa.crudApp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto saveUser(UserDto userDto) throws BadCredentialsException;
    ResponseDto edit(UserDto userDto) throws BadCredentialsException;
    ResponseDto deleteUser(Long userId);
    UserDto getUserByUsername(String username);
    UserDto getUserById(Long userId);
    Page<UserDto> getPageOfUsers(Pageable pageable);
}
