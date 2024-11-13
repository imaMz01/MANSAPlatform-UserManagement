package com.mansa.user.ServiceTest;

import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Entities.User;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Repositories.UserRepository;
import com.mansa.user.Services.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @BeforeEach
    void setUp() {

        reset(userRepository, userMapper, passwordEncoder);
    }

//    @Test
//    void getUsers(){
//        User user1 = new User("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "$2a$10$06JeHEW9JHc6r6a9Gt8/gebCHgIyZG0QsCx9jZN1e4UvmdeOw33Ta", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
//        User user2 = new User("318dd87f-6071-4eac-b088-15ec57795a4d","Ahmed", "Saadi", "0612457896", "Ahmed@gmail.com", "$2a$10$mAI4iTnTGGeLKGBGif6p6uLGZu1oIiYwItljfALiZoA0IcDn6FoP2", "17, quartier makka, Casablanca", false, false, LocalDateTime.now(), null);
//        when(userRepository.findAll()).thenReturn(List.of(user1,user2));
//        var list = userServiceImp.all();
//        assertThat(list).isNotNull();
//        assertThat(list.size()).isEqualTo(2);
//    }
//
//    @Test
//    void getUserById(){
//        User user1 = new User("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "$2a$10$06JeHEW9JHc6r6a9Gt8/gebCHgIyZG0QsCx9jZN1e4UvmdeOw33Ta", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
//        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
//        var user = userServiceImp.getById(user1.getId());
//        assertThat(user).isNotNull();
//        assertThat(user.getId()).isEqualTo(user1.getId());
//        assertThat(user.getEmail()).isEqualTo(user1.getEmail());
//        verify(userRepository,times(1)).findById(user1.getId());
//    }
//
//    @Test
//    void update(){
////        User old = new User("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "$2a$10$06JeHEW9JHc6r6a9Gt8/gebCHgIyZG0QsCx9jZN1e4UvmdeOw33Ta", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
////        User updated = new User("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "$2a$10$06JeHEW9JHc6r6a9Gt8/gebCHgIyZG0QsCx9jZN1e4UvmdeOw33Ta", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
////        when(userRepository.findById(old.getId())).thenReturn(Optional.of(old));
////        when(userRepository.save(updated)).thenReturn(updated);
////        var user = userServiceImp.update(userMapper.toDto(updated));
////        assertThat(user.getId()).isEqualTo(updated.getId());
////        assertThat(user.getEmail()).isEqualTo(updated.getEmail());
////        verify(userRepository,times(1)).save(updated);
//            // Création de l'utilisateur initial (avant mise à jour)
//        User old = new User("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "123", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
//        User updated = new User("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "new-password", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
//        UserDto userDto = new UserDto("8f0f8e24-0dd8-495e-a97d-4fe689af5934", "Salma", "Alami", "0612457896", "Salouma@gmail.com", "new-password", "17, quartier makka, Casablanca", true, true, LocalDateTime.now(), null);
//        when(passwordEncoder.encode(any(String.class))).thenReturn("encoded-password");
//        when(userMapper.toDto(updated)).thenReturn(userDto);
//        when(userRepository.findById(old.getId())).thenReturn(Optional.of(old));
//        when(userRepository.save(any(User.class))).thenReturn(updated);
//        UserDto result = userServiceImp.update(userDto);
//        assertThat(result.getId()).isEqualTo(updated.getId());
//        assertThat(result.getEmail()).isEqualTo(updated.getEmail());
//        verify(userRepository, times(1)).save(any(User.class));
//
//    }
}
