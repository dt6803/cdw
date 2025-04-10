package com.cdw_ticket.authentication_service.service.imp;

import com.cdw_ticket.authentication_service.dto.request.UserCreationRequest;
import com.cdw_ticket.authentication_service.dto.request.UserUpdateRequest;
import com.cdw_ticket.authentication_service.dto.response.UserResponse;
import com.cdw_ticket.authentication_service.exception.AppException;
import com.cdw_ticket.authentication_service.exception.ErrorCode;
import com.cdw_ticket.authentication_service.mapper.UserMapper;
import com.cdw_ticket.authentication_service.repository.UserRepository;
import com.cdw_ticket.authentication_service.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public UserResponse create(UserCreationRequest request) {
        var user = userMapper.toUser(request);
        user = userRepository.save(user);
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
    public void delete(String id) {
        userRepository.deleteById(id);
    }


}
