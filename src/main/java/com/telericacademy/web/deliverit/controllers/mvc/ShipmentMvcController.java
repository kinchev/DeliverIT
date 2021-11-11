package com.telericacademy.web.deliverit.controllers.mvc;

import com.telericacademy.web.deliverit.mappers.ShipmentMapper;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.services.contracts.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shipments")
public class ShipmentMvcController {

    private final ShipmentService service;
    private final ShipmentMapper shipmentMapper;
   @Autowired
    public ShipmentMvcController(ShipmentService service, ShipmentMapper shipmentMapper) {
        this.service = service;
        this.shipmentMapper = shipmentMapper;
    }

    @GetMapping("/{id}")
    public String showSingleShipment(@PathVariable int id, Model model, User user) {
        Shipment shipment = service.getById(id,user);
        model.addAttribute("shipment", shipment);
        return "shipment";
    }

}
