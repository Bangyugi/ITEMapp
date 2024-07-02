package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.ChangePasswordRequest;
import com.bangvan.EMwebapp.dto.request.RegisterRequest;
import com.bangvan.EMwebapp.dto.request.UpdateBasicInfoRequest;
import com.bangvan.EMwebapp.dto.request.UpdateContactInfoRequest;
import com.bangvan.EMwebapp.dto.response.UserListResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    void createUser(RegisterRequest registerRequest) throws NoSuchAlgorithmException;

    void updateUser(UpdateBasicInfoRequest updateBasicInfoRequest, Long id);

    void deleteUser(Long id);


    Long CountUser();

    List<UserListResponse> getAllUsers();

    UserResponse getUserByUsername(String username);

    void updateBasicUserInfo(UpdateBasicInfoRequest updateBasicInfoRequest);

    void updateContactInfo(UpdateContactInfoRequest updateContactInfoRequest);


    void changePassword(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException;

    boolean passwordNotMatches(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException;

    String getUserPassword(String username);

    boolean usernameExist(String username);

    boolean emailExist(String email);
}
