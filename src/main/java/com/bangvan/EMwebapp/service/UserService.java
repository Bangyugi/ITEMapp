package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.ChangePasswordRequest;
import com.bangvan.EMwebapp.dto.request.RegisterRequest;
import com.bangvan.EMwebapp.dto.request.UpdateBasicInfoRequest;
import com.bangvan.EMwebapp.dto.request.UpdateContactInfoRequest;
import com.bangvan.EMwebapp.dto.response.UserListResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    void createUser(RegisterRequest registerRequest) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    void updateUser(UpdateBasicInfoRequest updateBasicInfoRequest, Long id);

    void deleteUser(Long id);


    Long CountUser();

    List<UserListResponse> getAllUsers();

    UserResponse getUserByUsername(String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    void updateBasicUserInfo(UpdateBasicInfoRequest updateBasicInfoRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    void updateContactInfo(UpdateContactInfoRequest updateContactInfoRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


    void changePassword(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException;

    boolean passwordNotMatches(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException;

    String getUserPassword(String username);

    boolean usernameExist(String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    boolean emailExist(String email) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
