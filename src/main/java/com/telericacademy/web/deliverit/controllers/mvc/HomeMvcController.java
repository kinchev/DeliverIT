package com.telericacademy.web.deliverit.controllers.mvc;

import com.telericacademy.web.deliverit.controllers.rest.UserController;
import com.telericacademy.web.deliverit.services.contracts.ShipmentService;
import com.telericacademy.web.deliverit.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller()
@RequestMapping("/")
public class HomeMvcController {
    private final UserService userService;
    private final UserController userController;
    private final ShipmentService shipService;
@Autowired
    public HomeMvcController(UserService userService, UserController userController, ShipmentService shipService) {
        this.userService = userService;
        this.userController = userController;
        this.shipService = shipService;
    }


    @GetMapping
    public String showCustomer(Model model) {
        model.addAttribute("count", userController.countCustomers());

        return "index";
    }
}
