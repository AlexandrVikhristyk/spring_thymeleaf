package com.example.demo.service;

import com.example.demo.entity.CustomUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public boolean saveUser(CustomUser user){
        if (userRepository.existsByEmail(user.getEmail()))
            return false;

        Set<Role> roles =  new HashSet<>();
        roles.add(roleRepository.findByRole("ROLE_USER"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void updateUser(String email){
        CustomUser user = userRepository.findByEmail(email);

        user.setEmail(email);

        userRepository.save(user);
    }
}
