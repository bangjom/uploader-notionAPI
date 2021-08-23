package com.windfally.uploader.user.service;

import com.windfally.uploader.upload.dto.UserDto;
import com.windfally.uploader.user.entity.Role;
import com.windfally.uploader.user.mapper.UserMapper;
import com.windfally.uploader.user.repository.UserRepository;
import com.windfally.uploader.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        return userRepository.findByEmailAndAuthorities_AuthorityName(email,Role.ADMIN.getKey())
                .map(user -> createUser(user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }

    public List<UserDto> getUsersByPeriod(LocalDateTime start, LocalDateTime end){
        return userRepository.findByCreatedDateBetween(start,end).stream()
                .map(UserMapper.INSTANCE::toUserDto)
                .collect(Collectors.toList());
    }
}
