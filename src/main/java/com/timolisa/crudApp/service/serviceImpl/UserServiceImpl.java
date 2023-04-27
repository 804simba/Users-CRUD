package com.timolisa.crudApp.service.serviceImpl;

import com.timolisa.crudApp.dto.ResponseDto;
import com.timolisa.crudApp.dto.UserDto;
import com.timolisa.crudApp.exception.BadCredentialsException;
import com.timolisa.crudApp.model.User;
import com.timolisa.crudApp.repository.UserRepository;
import com.timolisa.crudApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDto saveUser(UserDto userDto) throws BadCredentialsException {
        if (validateInputs(userDto)) {
            User user = userDtoToUser(userDto);
            userRepository.save(user);
            return userToUserDto(user);
        } else {
            throw new BadCredentialsException("Invalid username or email");
        }
    }

    @Override
    public Page<UserDto> getPageOfUsers(Pageable pageable) {
        Page<User> pagesOfUsers = userRepository.findAll(pageable);
        if (pagesOfUsers.isEmpty()) {
            throw new NoSuchElementException("No users are available");
        }
        return pagesOfUsers.map(this::userToUserDto);
    }

    @Override
    public ResponseDto edit(UserDto userDto) throws BadCredentialsException {
        User user = userRepository.findByUsername(userDto.getUsername())
                        .orElseThrow(() -> new NoSuchElementException("User does not exist"));
        ResponseDto response = new ResponseDto();
        if (validateInputs(userDto)) {
            user.setUsername(user.getUsername());
            user.setEmail(userDto.getEmail());
            return ResponseDto.builder()
                    .message("Edit successful")
                    .build();
        } else {
            throw new BadCredentialsException("Invalid username or email");
        }
    }

    @Override
    public ResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id: %d does not exist", userId)));
        userRepository.delete(user);
        return ResponseDto.builder()
                .message("Delete successful")
                .build();
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User does not exist"));
        userRepository.delete(user);
        return userToUserDto(user);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id: %d does not exist", userId)));
        return userToUserDto(user);
    }
    private UserDto userToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();
    }
    private User userDtoToUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
    private boolean validateInputs(UserDto userDto) {
        boolean status = false;
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        if (!username.equals("") && !email.equals("") && !password.equals("")) {
            status = true;
        }
        return status;
    }
}
