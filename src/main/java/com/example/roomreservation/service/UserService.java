package com.example.roomreservation.service;

import com.example.roomreservation.exception.user.UserNotFoundException;
import com.example.roomreservation.model.user.User;
import com.example.roomreservation.model.user.UserDTO;
import com.example.roomreservation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(UserDTO user){
        User createdUser= modelMapper.map(user, User.class);
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(createdUser);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public User updateUser(Long id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        modelMapper.map(userDTO, user);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String deleteUser(Long id){
        String message = String.format("The user with the id %s is deleted",id);
        userRepository.deleteById(id);
        return message;
    }
    public boolean findByUsername(String username) {
        Optional<User> myuser = userRepository.findByUsername(username);
        return myuser.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        // I am having only one role here.
        // In case I need more than one role :
        // I can modify it to have an authority list attribute instead of just role
        return new com.example.roomreservation.model.user.User(
                user.getUsername(),
                user.getPassword(),
                user.getRole());
    }
}
