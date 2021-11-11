package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.controllers.rest.AuthenticationHelper;
import com.telericacademy.web.deliverit.exceptions.InvalidUserInputException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.Warehouse;
import com.telericacademy.web.deliverit.repositories.contracts.ShipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.telericacademy.web.deliverit.Helpers.*;
import static org.mockito.internal.util.MockUtil.createMock;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceImplTests {
    @Mock
    ShipmentRepository mockShipmentRepository;
    AuthenticationHelper authenticationHelper;


    @InjectMocks
    ShipmentServiceImpl service;

    @Test
    void getAll_should_callRepository() {
        Mockito.when(mockShipmentRepository.getAll())
                .thenReturn(new ArrayList<>());

        User user=createMockEmployee();
        service.getAll(user);


        Mockito.verify(mockShipmentRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void getById_should_returnShipment_when_matchExist() {

        Shipment mockShipment = createMockShipment();
        Mockito.when(mockShipmentRepository.getById(mockShipment.getId()))
                .thenReturn(mockShipment);
        User user=createMockEmployee();
        Shipment result = service.getById(mockShipment.getId(),user);


        Assertions.assertAll(
                () -> Assertions.assertEquals(mockShipment.getId(), result.getId()),
                () -> Assertions.assertEquals(mockShipment.getOriginWarehouse(), result.getOriginWarehouse()),
                () -> Assertions.assertEquals(mockShipment.getDestinationWarehouse(), result.getDestinationWarehouse()));

    }
    @Test
    public void create_should_throwException_when_userIsNotEmployee() {
        // Arrange
        Shipment mockShipment = createMockShipment();
        User mockUser = createMockCustomer();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.create(mockShipment, mockUser));
    }
    @Test
    public void update_should_throwException_when_userIsNotEmployee() {
        // Arrange
        Shipment mockShipment = createMockShipment();
        User mockUser = createMockEmployee();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.update(mockShipment, mockUser));
    }
    @Test
    public void delete_should_throwException_when_userIsNotEmployee() {
        // Arrange
        Shipment mockShipment = createMockShipment();
        User mockUser = createMockEmployee();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.delete(mockShipment.getId(), mockUser));
    }
//@Test
//    public void a(){
//        Shipment mockShipment = createMockShipment();
//        User mockUser = createMockEmployee();
//        Parcel mockParcel =createParcel();
//    Assertions.assertThrows(UnauthorizedOperationException.class,
//            () -> service.delete(mockShipment.getId(), mockUser));
//    Assertions.assertThrows(InvalidUserInputException
//            .class,()->service.addParcelToShipment(mockShipment,mockParcel,mockUser));
//}

}
