package com.learning.candidatemanagementsystem.security.service;

import com.learning.candidatemanagementsystem.model.User;
import com.learning.candidatemanagementsystem.model.UserRole;
import com.learning.candidatemanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getUserRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName())) // prefix is important!
                .collect(Collectors.toSet());
    }
}
