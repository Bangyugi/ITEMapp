package com.bangvan.EMwebapp.controller;

import com.bangvan.EMwebapp.dto.request.ChangePasswordRequest;
import com.bangvan.EMwebapp.dto.request.RegisterRequest;
import com.bangvan.EMwebapp.dto.request.UpdateBasicInfoRequest;
import com.bangvan.EMwebapp.dto.request.UpdateContactInfoRequest;
import com.bangvan.EMwebapp.dto.response.UserListResponse;
import com.bangvan.EMwebapp.dto.response.UserResponse;
import com.bangvan.EMwebapp.repository.UserRepository;
import com.bangvan.EMwebapp.service.UserService;
import com.bangvan.EMwebapp.util.EmailUtil;
import com.bangvan.EMwebapp.util.OTPutil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OTPutil otpUtil;

    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private HttpSession httpSession;


    @InitBinder
    public void innitBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }
    @GetMapping("/register")
    public String register(Model model,  @ModelAttribute RegisterRequest userRequest) {
        model.addAttribute("userRequest", userRequest);
        return "register";
    }

    @GetMapping("/email-verify")
    public String verify(Model model,HttpSession httpSession) {
        RegisterRequest userRequest = (RegisterRequest) httpSession.getAttribute("userRequest");
        if (userRequest == null) {
            return "redirect:/register";
        }
        model.addAttribute("userRequest", userRequest);
        return "email-verify";
    }


    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute("userRequest")  RegisterRequest userRequest, BindingResult bindingResult)
            throws NoSuchAlgorithmException, MessagingException {
        if (userService.usernameExist(userRequest.getUsername())) {
            System.out.println("Username already exists");
            bindingResult.addError(new FieldError("userRequest", "username", "Username already exists"));
        } else if (userService.emailExist(userRequest.getEmail())) {
            System.out.println("Email already exists");
            bindingResult.addError(new FieldError("userRequest", "email", "Email already exists"));
        } else if (userRequest.getPassword() != null && userRequest.getRepeatPassword() != null) {
            if (!userRequest.getPassword().equals(userRequest.getRepeatPassword())) {
                System.out.println("Password do not match");
                bindingResult.addError(new FieldError("userRequest", "repeatPassword", "Password does not match"));
            }
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        String otp = otpUtil.generateOTP();
        // Save the OTP and userRequest to session or temporary storage
        httpSession.setAttribute("userRequest", userRequest);
        httpSession.setAttribute("sessionOtp", otp);
        // Send OTP to the user's email (implementation not shown)
        emailUtil.sendEmail(userRequest.getEmail(),otp);


        // Redirect to email verification page
        return "email-verify";

    }

    @PostMapping("/email-verify")
    public String verifyOTP(@ModelAttribute("userRequest") RegisterRequest userRequest, BindingResult bindingResult, HttpSession httpSession) throws NoSuchAlgorithmException {
        String sessionOTP = (String) httpSession.getAttribute("sessionOtp");
        if(userRequest.getOtp()!=null && sessionOTP!=null) {
            if(!userRequest.getOtp().equals(sessionOTP)){
                    bindingResult.addError(new FieldError("userRequest", "otp", "Invalid OTP"));
            }
        }
        if (bindingResult.hasErrors()) {
            return "email-verify";
        }


        userService.createUser(userRequest);
        httpSession.removeAttribute("userRequest");
        httpSession.removeAttribute("sessionOtp");
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", userResponse);

        UpdateBasicInfoRequest updateBasicInfoRequest = new UpdateBasicInfoRequest();
        modelMapper.map(userResponse, updateBasicInfoRequest);
        model.addAttribute("basicInfo", updateBasicInfoRequest);

        UpdateContactInfoRequest updateContactInfoRequest =new UpdateContactInfoRequest();
        modelMapper.map(userResponse, updateContactInfoRequest);
        model.addAttribute("contactInfo", updateContactInfoRequest);
        return "profile";
    }

    @GetMapping("/profile-setting")
    public String profileSetting(Model model,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);
        UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());
        UpdateBasicInfoRequest updateBasicInfoRequest = new UpdateBasicInfoRequest();
        modelMapper.map(userResponse, updateBasicInfoRequest);
        model.addAttribute("basicInfo", updateBasicInfoRequest);

        UpdateContactInfoRequest updateContactInfoRequest =new UpdateContactInfoRequest();
        modelMapper.map(userResponse, updateContactInfoRequest);
        model.addAttribute("contactInfo", updateContactInfoRequest);

        ChangePasswordRequest cpwRequest = new ChangePasswordRequest();
        modelMapper.map(userResponse, cpwRequest);
        model.addAttribute("changePasswordRequest", cpwRequest);

        return "profile-setting";
    }

    @PostMapping("/profile-basic-update")
    public String updateProfile(Model model,Authentication authentication, @ModelAttribute("basicInfo") UpdateBasicInfoRequest updateBasicInfoRequest ) {
        userService.updateBasicUserInfo(updateBasicInfoRequest);
        return "redirect:/profile";

    }

    @PostMapping("/profile-contact-update")
    public String updateContact(Model model,Authentication authentication, @ModelAttribute("contactInfo") UpdateContactInfoRequest updateContactInfoRequest ) {
        userService.updateContactInfo(updateContactInfoRequest);
        return "redirect:/profile";
    }

    @PostMapping("/profile-change-password")
    public String upadtePasssword(Model model,Authentication authentication,  @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest, BindingResult bindingResult) throws NoSuchAlgorithmException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userService.passwordNotMatches(changePasswordRequest))
        {
            bindingResult.addError(new FieldError("changePasswordRequest", "oldPassword", "Wrong password"));
        }
        else if (changePasswordRequest.getNewPassword()!=null && changePasswordRequest.getConfirmPassword()!=null) {
            if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                bindingResult.addError(new FieldError("changePasswordRequest", "confirmPassword", "Password does not match"));
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDetails);
            UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());
            UpdateBasicInfoRequest updateBasicInfoRequest = new UpdateBasicInfoRequest();
            modelMapper.map(userResponse, updateBasicInfoRequest);
            model.addAttribute("basicInfo", updateBasicInfoRequest);

            UpdateContactInfoRequest updateContactInfoRequest =new UpdateContactInfoRequest();
            modelMapper.map(userResponse, updateContactInfoRequest);
            model.addAttribute("contactInfo", updateContactInfoRequest);
            return "profile-setting";
        }
        userService.changePassword(changePasswordRequest);
        return "redirect:/profile";
    }

    @GetMapping("/profile-document")
    public String profileDocument(Model model,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", userResponse);
        return "profile-document";
    }
    @GetMapping("/profile-timeoff")
    public String profileTimeoff(Model model,Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponse userResponse = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", userResponse);
        return "profile-timeoff";
    }

}
