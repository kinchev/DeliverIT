package com.telericacademy.web.deliverit.controllers.mvc;

import com.telericacademy.web.deliverit.mappers.UserMapper;
import com.telericacademy.web.deliverit.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMvcController {
    private  final UserService userService;
    private  final UserMapper userMapper;

@Autowired
    public UserMvcController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }



}


