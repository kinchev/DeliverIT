package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Shipment;
import com.telericacademy.web.deliverit.repositories.ShipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.telericacademy.web.deliverit.Helpers.createMockShipment;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceImplTests {
    @Mock
    ShipmentRepository mockShipmentRepository;

    @InjectMocks
    ShipmentServiceImpl service;

    @Test
    void getAll_should_callRepository() {
        Mockito.when(mockShipmentRepository.getAll(java.util.Optional.empty()))
                .thenReturn(new ArrayList<>());


        service.getAll(java.util.Optional.empty());


        Mockito.verify(mockShipmentRepository, Mockito.times(1)).getAll(Optional.empty());
    }

    @Test
    public void getById_should_returnShipment_when_matchExist() {

        Shipment mockShipment = createMockShipment();
        Mockito.when(mockShipmentRepository.getById(mockShipment.getId()))
                .thenReturn(mockShipment);

        Shipment result = service.getById(mockShipment.getId());


        Assertions.assertAll(
                () -> Assertions.assertEquals(mockShipment.getId(), result.getId()),
                () -> Assertions.assertEquals(mockShipment.getOriginWarehouseId(), result.getOriginWarehouseId()),
                () -> Assertions.assertEquals(mockShipment.getDestinationWarehouseId(), result.getDestinationWarehouseId()));

    }
//    }
//@Test
//    public void create_should_throw_when_shipmentWithSameNameExists(){
//
//        Shipment mockShipment =createMockShipment();
//
//        Mockito.when(mockShipmentRepository.);
//}

}
