package com.learning.candidatemanagementsystem.service;
import com.learning.candidatemanagementsystem.dto.UserDto;
import com.learning.candidatemanagementsystem.dto.UserResponseDto;
import jakarta.validation.Valid;

import java.util.List;


public interface IAdminUserService {

    /**
     * Returns a list of all users in the system.
     *
     * @return a list of all users in the system.
     */
    List<UserResponseDto> fetchAllUsers();

    /**
     * Creates a new user with the given data.
     *
     * @param user the data of the user to be created.
     * @return
     */
    boolean createUser(UserDto user);

    /**
     * Updates the user with the given id.
     *
     * @param userId the id of the user to be updated.
     */
    boolean updateUser(@Valid Long userId, UserDto user) ;
}
