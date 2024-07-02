package com.bangvan.EMwebapp.service;

import com.bangvan.EMwebapp.dto.request.ChangePasswordRequest;
import com.bangvan.EMwebapp.dto.request.RegisterRequest;
import com.bangvan.EMwebapp.dto.request.UpdateBasicInfoRequest;
import com.bangvan.EMwebapp.dto.request.UpdateContactInfoRequest;
import com.bangvan.EMwebapp.dto.response.UserListResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.entity.Role;
import com.bangvan.EMwebapp.entity.User;
import com.bangvan.EMwebapp.repository.RoleRepository;
import com.bangvan.EMwebapp.repository.UserRepository;
import org.bouncycastle.util.encoders.Hex;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  ModelMapper modelMapper;

    public String getPasswordEncoder(String originalPass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                originalPass.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    @Override
    public void createUser(RegisterRequest registerRequest) throws NoSuchAlgorithmException {
        User user = modelMapper.map(registerRequest, User.class);
        Role role = roleRepository.findByRole("ROLE_ADMIN");
        System.out.println(role);
        user.setPassword(getPasswordEncoder(registerRequest.getPassword()));
        user.setEnabled(true);
        user.addRole(role);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateBasicInfoRequest updateBasicInfoRequest, Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User Not Found"));
        modelMapper.map(updateBasicInfoRequest,user);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public Long CountUser(){
        return userRepository.count();
    }

    @Override
    public List<UserListResponse> getAllUsers() {
        List<User> users= userRepository.findAll();
        List<UserListResponse> userListResponse = new ArrayList<>();
        for (User user : users) {
            UserListResponse userListResponseModel = new UserListResponse();
            modelMapper.map(user,userListResponseModel);
            userListResponseModel.setName(user.getFirstName()+" "+user.getLastName());
            userListResponse.add(userListResponseModel);
        }
        return userListResponse;
    }

    @Override
    public UserResponse getUserByUsername(String username){
        User user =  userRepository.findAllByUsername(username);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        return userResponse;
    }

    @Override
    public void updateBasicUserInfo(UpdateBasicInfoRequest updateBasicInfoRequest){
        User user = userRepository.findById(updateBasicInfoRequest.getId()).orElseThrow(()-> new RuntimeException("User Not Found"));
        modelMapper.map(updateBasicInfoRequest,user);
        userRepository.save(user);
    }

    @Override
    public  void updateContactInfo(UpdateContactInfoRequest updateContactInfoRequest){
        User user = userRepository.findById(updateContactInfoRequest.getId()).orElseThrow(()-> new RuntimeException("User Not Found"));
        modelMapper.map(updateContactInfoRequest,user);
        userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException {
        User user = userRepository.findById(changePasswordRequest.getId()).orElseThrow(() ->new RuntimeException("User Not Found"));
        user.setPassword(getPasswordEncoder(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean passwordNotMatches(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException {
        User user = userRepository.findById(changePasswordRequest.getId()).orElseThrow(()-> new RuntimeException("User Not Found"));
        if (!getPasswordEncoder(changePasswordRequest.getOldPassword()).equals(user.getPassword()))
        {
            return true;
        }
        return false;
    }

    @Override
    public String getUserPassword(String username)
    {
        User user = userRepository.findByUsername(username);
        return user.getPassword();
    }
    @Override
    public boolean usernameExist(String username){
        User user = userRepository.findByUsername(username);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean emailExist(String email)
    {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }
}
