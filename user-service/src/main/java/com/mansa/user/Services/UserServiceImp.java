package com.mansa.user.Services;

import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Entities.User;
import com.mansa.user.Exceptions.UserNotFoundException;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    @Override
    public UserDto add(UserDto userDto) {
        User user = UserMapper.userMapper.toEntity(userDto);
        user.setId(UUID.randomUUID().toString());
        user.setCreated(LocalDateTime.now());
        user.setEnabled(false);
        return UserMapper.userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = getUser(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setTel(userDto.getTel());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setEnabled(userDto.isEnabled());
        user.setCreated(userDto.getCreated());
        user.setUpdated(LocalDateTime.now());
        return UserMapper.userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public List<UserDto> all() {
        return UserMapper.userMapper.toDto(userRepository.findAll());
    }

    @Override
    public UserDto changeStatus(String id) {
        User user = getUser(id);
        user.setEnabled(!user.isEnabled());
        user.setUpdated(LocalDateTime.now());
        return UserMapper.userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public UserDto getById(String id) {
        return UserMapper.userMapper.toDto(
                getUser(id)
        );
    }

    private User getUser(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
    }
}
