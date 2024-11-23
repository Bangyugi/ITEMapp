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
import com.bangvan.EMwebapp.util.AESutil;
import org.bouncycastle.util.encoders.Hex;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
    public void createUser(RegisterRequest registerRequest) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        User user = modelMapper.map(registerRequest, User.class);
        Role role = roleRepository.findByRole("ROLE_ADMIN");
        System.out.println(role);
        user.setPassword(getPasswordEncoder(registerRequest.getPassword()));
        user.setEnabled(true);
        user.addRole(role);

        user.setEmail(AESutil.encrypt(user.getEmail(),AESutil.generateKey(),AESutil.generateIv()));

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
    public UserResponse getUserByUsername(String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user =  userRepository.findAllByUsername(username);
        user.setEmail(AESutil.decrypt(user.getEmail(), AESutil.generateKey(), AESutil.generateIv()));
        if (user.getLastName() != null)
            user.setLastName(AESutil.decrypt(user.getLastName(), AESutil.generateKey(), AESutil.generateIv()));
        if (user.getFirstName() != null)
            user.setFirstName(AESutil.decrypt(user.getFirstName(), AESutil.generateKey(), AESutil.generateIv()));
        if (user.getNationality() != null)
            user.setNationality(AESutil.decrypt(user.getNationality(), AESutil.generateKey(), AESutil.generateIv()));
        if (user.getGender() != null)
            user.setGender(AESutil.decrypt(user.getGender(), AESutil.generateKey(), AESutil.generateIv()));
        if(user.getFacebook()!=null)
            user.setFacebook(AESutil.decrypt(user.getFacebook(), AESutil.generateKey(), AESutil.generateIv()));
        if(user.getLinkedIn()!=null)
            user.setLinkedIn(AESutil.decrypt(user.getLinkedIn(), AESutil.generateKey(), AESutil.generateIv()));
        if(user.getPhone()!=null)
            user.setPhone(AESutil.decrypt(user.getPhone(), AESutil.generateKey(), AESutil.generateIv()));
        for (var company:user.getCompanies())
        {
            company.setName(AESutil.decrypt(company.getName(),AESutil.generateKey(),AESutil.generateIv()));
            company.setAddress(AESutil.decrypt(company.getAddress(),AESutil.generateKey(),AESutil.generateIv()));
            company.setPhoneNumber(AESutil.decrypt(company.getPhoneNumber(),AESutil.generateKey(),AESutil.generateIv()));
            company.setWebsite(AESutil.decrypt(company.getWebsite(),AESutil.generateKey(),AESutil.generateIv()));
            company.setVATnumber(AESutil.decrypt(company.getVATnumber(),AESutil.generateKey(),AESutil.generateIv()));
            company.setRegisterNumber(AESutil.decrypt(company.getRegisterNumber(),AESutil.generateKey(),AESutil.generateIv()));
            company.setEmail(AESutil.decrypt(company.getEmail(),AESutil.generateKey(),AESutil.generateIv()));
        }
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    @Override
    public void updateBasicUserInfo(UpdateBasicInfoRequest updateBasicInfoRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        updateBasicInfoRequest.setFirstName(AESutil.encrypt(updateBasicInfoRequest.getFirstName(),AESutil.generateKey(),AESutil.generateIv()));
        updateBasicInfoRequest.setLastName(AESutil.encrypt(updateBasicInfoRequest.getLastName(),AESutil.generateKey(),AESutil.generateIv()));
        updateBasicInfoRequest.setGender(AESutil.encrypt(updateBasicInfoRequest.getGender(),AESutil.generateKey(),AESutil.generateIv()));
        updateBasicInfoRequest.setNationality(AESutil.encrypt(updateBasicInfoRequest.getNationality(),AESutil.generateKey(),AESutil.generateIv()));

        User user = userRepository.findById(updateBasicInfoRequest.getId()).orElseThrow(()-> new RuntimeException("User Not Found"));
        modelMapper.map(updateBasicInfoRequest,user);
        userRepository.save(user);
    }

    @Override
    public  void updateContactInfo(UpdateContactInfoRequest updateContactInfoRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        updateContactInfoRequest.setEmail(AESutil.encrypt(updateContactInfoRequest.getEmail(),AESutil.generateKey(),AESutil.generateIv()));
        updateContactInfoRequest.setFacebook(AESutil.encrypt(updateContactInfoRequest.getFacebook(),AESutil.generateKey(),AESutil.generateIv()));
        updateContactInfoRequest.setPhone(AESutil.encrypt(updateContactInfoRequest.getPhone(),AESutil.generateKey(),AESutil.generateIv()));
        updateContactInfoRequest.setLinkedIn(AESutil.encrypt(updateContactInfoRequest.getLinkedIn(),AESutil.generateKey(),AESutil.generateIv()));
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
    public boolean usernameExist(String username) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean emailExist(String email) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        email = AESutil.encrypt(email,AESutil.generateKey(),AESutil.generateIv());
        User user = userRepository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }
}
