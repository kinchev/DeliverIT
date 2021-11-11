package com.telericacademy.web.deliverit.controllers.mvc;

import com.telericacademy.web.deliverit.controllers.rest.AuthenticationHelper;
import com.telericacademy.web.deliverit.exceptions.AuthenticationFailureException;
import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.mappers.UserMapper;
import com.telericacademy.web.deliverit.models.dto.LoginDto;
import com.telericacademy.web.deliverit.models.dto.RegisterDto;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationHelper authHelper;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationHelper authHelper, UserMapper userMapper) {
        this.userService = userService;
        this.authHelper = authHelper;
        this.userMapper = userMapper;
    }


//да Покаже login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new LoginDto());
        return "login";
    }
//грижи се да се login in...HttpSession
    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto login, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            authHelper.verifyAuthentication(login.getEmail(), login.getPassword());
            session.setAttribute("currentUser", login.getEmail());
            return "redirect:/";
        } catch (AuthenticationFailureException e) {
            bindingResult.rejectValue("email", "auth_error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("register") RegisterDto register,
                                 BindingResult bindingResult,
                                 HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!register.getPassword().equals(register.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password_error", "Password confirmation should match password.");
            return "register";
        }

        try {
            User user = userMapper.fromDto(register);
            userService.create(user);
            return "redirect:/auth/login";
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("email", "username_error", e.getMessage());
            return "register";
        }
    }
}
