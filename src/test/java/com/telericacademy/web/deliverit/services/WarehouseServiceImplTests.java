package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.exceptions.DuplicateEntityException;
import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.exceptions.UnauthorizedOperationException;
import com.telericacademy.web.deliverit.models.User;
import com.telericacademy.web.deliverit.models.Warehouse;
import com.telericacademy.web.deliverit.repositories.WarehouseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.telericacademy.web.deliverit.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceImplTests {

    @Mock
    WarehouseRepository mockRepository;

    @InjectMocks
    WarehouseServiceImpl service;

    @Test
    void getAll_should_callRepository() {
        // Arrange
        Mockito.when(mockRepository.getAll())
                .thenReturn(new ArrayList<>());

        // Act
        service.getAll();

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .getAll();
    }

    @Test
    public void getById_should_returnWarehouse_when_matchExist() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        Mockito.when(mockRepository.getById(mockWarehouse.getId()))
                .thenReturn(mockWarehouse);
        // Act
        Warehouse result = service.getById(mockWarehouse.getId());

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(mockWarehouse.getId(), result.getId()),
                () -> Assertions.assertEquals(mockWarehouse.getAddress(), result.getAddress())
        );
    }

    @Test
    public void create_should_throwException_when_userIsNotEmployee() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockCustomer();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.create(mockWarehouse, mockUser));
    }

    @Test
    public void create_should_throw_when_warehouseWithSameAddressExists() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockEmployee();

        Mockito.when(mockRepository.getByAddressId(mockWarehouse.getAddress().getId()))
                .thenReturn(mockWarehouse);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockWarehouse, mockUser));
    }

    @Test
    public void create_should_callRepository_when_warehouseWithSameAddressDoesNotExist() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockEmployee();

        Mockito.when(mockRepository.getByAddressId(mockWarehouse.getAddress().getId()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.create(mockWarehouse, mockUser);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(mockWarehouse);
    }

    @Test
    public void update_should_throwException_when_userIsNotEmployee() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockCustomer();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.update(mockWarehouse, mockUser));
    }


    @Test
    void update_should_callRepository_when_warehouseWithSameAddressDoesNotExist() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockEmployee();

        Mockito.when(mockRepository.getByAddressId(mockWarehouse.getAddress().getId()))
                .thenReturn(mockWarehouse);

        Mockito.when(mockRepository.getByAddressId(mockWarehouse.getAddress().getId()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.update(mockWarehouse, mockUser);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(mockWarehouse);
    }

    @Test
    public void delete_should_throwException_when_userIsNotEmployee() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockCustomer();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.delete(mockWarehouse.getId(), mockUser));
    }

    @Test
    public void delete_should_callRepository_when_userIsEmployee() {
        // Arrange
        Warehouse mockWarehouse = createMockOriginWarehouse();
        User mockUser = createMockEmployee();

        // Act
        service.delete(mockWarehouse.getId(), mockUser);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .delete(mockWarehouse.getId());
    }


}
