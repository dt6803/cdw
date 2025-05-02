package com.cdw_ticket.authentication_service.service.imp;

import com.cdw_ticket.authentication_service.client.ProfileClient;
import com.cdw_ticket.authentication_service.dto.request.RegisterRequest;
import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRoleRequest;
import com.cdw_ticket.authentication_service.dto.response.UserResponse;
import com.cdw_ticket.authentication_service.entity.Role;
import com.cdw_ticket.authentication_service.entity.User;
import com.cdw_ticket.authentication_service.enums.RoleEnum;
import com.cdw_ticket.authentication_service.exception.AppException;
import com.cdw_ticket.authentication_service.exception.ErrorCode;
import com.cdw_ticket.authentication_service.mapper.ProfileMapper;
import com.cdw_ticket.authentication_service.mapper.UserMapper;
import com.cdw_ticket.authentication_service.repository.RoleRepository;
import com.cdw_ticket.authentication_service.repository.UserRepository;
import com.cdw_ticket.authentication_service.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    ProfileMapper profileMapper;
    @Override
    public UserResponse create(RegisterRequest request) {
        var user = userMapper.toUser(request);
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByName(RoleEnum.USER.toString()).ifPresent(roles::add);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        var profileRequest = profileMapper.toProfile(request);
        profileRequest.setUserId(user.getId());
        var profileResponse = profileClient.createProfile(profileRequest);
        log.info("Profile created: {}", profileResponse.toString());

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<UserResponse> list = new ArrayList<>();
        userRepository.findAll().stream()
                .forEach(u -> list.add(userMapper.toUserResponse(u)));
        return list;
    }

    @Override
    public UserResponse getById(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse update(String id, UserUpdateRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(String id, UserUpdateRoleRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        Set<Role> roles = new HashSet<>();
        request.getRoles().forEach(r -> roles.add(roleRepository.findByName(r)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED))));
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException());
        return user;
    }

    @Override
    public Set<String> getRolesById(String id) {
        return userRepository.getRolesById(id);
    }
}
