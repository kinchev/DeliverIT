package com.telericacademy.web.deliverit;

import com.telericacademy.web.deliverit.models.*;
import com.telericacademy.web.deliverit.models.dto.ParcelDto;
import com.telericacademy.web.deliverit.models.dto.ShipmentDto;
import com.telericacademy.web.deliverit.models.dto.UserDto;

import java.time.LocalDate;
import java.util.Set;

public class Helpers {
    public static User createMockCustomer() {
        return createMockUser("customer");
    }

    public static User createMockEmployee() {
        return createMockUser("employee");
    }

    private static User createMockUser(String role) {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setEmail("mock@user.com");
        mockUser.setAddress(createMockAddress(1));
        mockUser.setRoles(Set.of(createMockRole(role)));
        return mockUser;
    }

    public static Address createMockAddress(int id) {
        var mockAddress = new Address();
        mockAddress.setId(id);
        mockAddress.setCity(createMockCity());
        mockAddress.setStreetName("1, mockStreetName");
        return mockAddress;
    }

    public static City createMockCity() {
        var mockCity = new City();
        mockCity.setId(1);
        mockCity.setCityName("MockCityName");
        mockCity.setCountry(createMockCountry());
        return mockCity;
    }

    public static Country createMockCountry() {
        var mockCountry = new Country();
        mockCountry.setId(1);
        mockCountry.setCountryName("MockCountryName");
        return mockCountry;
    }

    public static Role createMockRole(String role) {
        var mockRole = new Role();
        mockRole.setId(1);
        mockRole.setName(role);
        return mockRole;
    }

    public static UserDto createMockUserDto() {
        var mockUserDto = new UserDto();
        mockUserDto.setFirstName("TestFirstName");
        mockUserDto.setLastName("TestLastName");
        mockUserDto.setEmail("test@gmail.com");
        mockUserDto.setAddressId(1);
        return mockUserDto;
    }

    public static Category createMockCategory() {
        var mockCategory = new Category();
        mockCategory.setId(1);
        mockCategory.setName("MockCategoryName");
        return mockCategory;
    }

    public static Warehouse createMockOriginWarehouse() {
        var mockOriginWarehouse = new Warehouse();
        mockOriginWarehouse.setId(1);
        mockOriginWarehouse.setAddress(createMockAddress(2));
        return mockOriginWarehouse;
    }

    public static Warehouse createMockDestinationWarehouse() {
        var mockDestinationWarehouse = new Warehouse();
        mockDestinationWarehouse.setId(2);
        mockDestinationWarehouse.setAddress(createMockAddress(3));
        return mockDestinationWarehouse;
    }

    public static Parcel createParcel() {
        var mockParcel = new Parcel();
        mockParcel.setId(1);
        mockParcel.setUser(createMockCustomer());
        mockParcel.setOriginWarehouse(createMockOriginWarehouse());
        mockParcel.setDestinationWarehouse(createMockDestinationWarehouse());
        mockParcel.setCategory(createMockCategory());
        mockParcel.setWeight(5.5);
        mockParcel.setDeliverToUser(false);
        return mockParcel;
    }

    public static ParcelDto createMockParcelDto() {
        var mockParcelDto = new ParcelDto();
        mockParcelDto.setUserId(1);
        mockParcelDto.setOriginWarehouseId(2);
        mockParcelDto.setDestinationWarehouseId(3);
        mockParcelDto.setWeight(5.5);
        mockParcelDto.setDeliverToUser(false);
        return mockParcelDto;
    }

    public static Shipment createMockShipment() {
        var mockShipment = new Shipment();
        mockShipment.setId(1);
        mockShipment.setOriginWarehouse(createMockOriginWarehouse());
        mockShipment.setDestinationWarehouse(createMockDestinationWarehouse());
        mockShipment.setArrivalDate(LocalDate.MAX);
        mockShipment.setDepartureDate(LocalDate.MIN);
        return mockShipment;

    }

    public static ShipmentDto createMockShipmentDTO() {
        var mockShipmentDto = new ShipmentDto();
        mockShipmentDto.setOriginWarehouseId(1);
        mockShipmentDto.setDestinationWarehouseId(2);
        mockShipmentDto.setArrivalDate(LocalDate.of(2021, 1, 3));
        mockShipmentDto.setDepartureDate(LocalDate.of(2021, 2, 3));

        return mockShipmentDto;


    }
}
