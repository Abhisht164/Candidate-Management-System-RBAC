package com.learning.candidatemanagementsystem.mapper;

import com.learning.candidatemanagementsystem.dto.CandidateDto;
import com.learning.candidatemanagementsystem.dto.UserDto;
import com.learning.candidatemanagementsystem.dto.UserResponseDto;
import com.learning.candidatemanagementsystem.model.User;

import java.util.List;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static User mapToUser(UserDto userRequest, User user) {
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userRequest = new UserDto();
        userRequest.setUsername(user.getUsername());
        userRequest.setPassword(user.getPassword());
        return userRequest;
    }
    public static List<UserDto> mapUserDtoList(List<User> users) {
        return users.stream()
                .map(c -> new UserDto(c.getUsername(), c.getPassword()))
                .toList();
    }

    public static List<UserResponseDto> mapToUserResponseDtoList(List<User> users) {
        return users.stream()
                .map(c -> new UserResponseDto(c.getId(),c.getUsername(),c.getPassword()))
                .toList();
    }

    public static CandidateDto mapToCandidateDto(User user) {
        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setFullName(user.getUsername());
        candidateDto.setEmail(user.getPassword());
        return candidateDto;
    }

}
