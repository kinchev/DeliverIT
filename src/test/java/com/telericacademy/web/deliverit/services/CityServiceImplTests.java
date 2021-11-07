package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.City;
import com.telericacademy.web.deliverit.repositories.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.telericacademy.web.deliverit.Helpers.*;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTests {

    @Mock
    CityRepository mockRepository;

    @InjectMocks
    CityServiceImpl service;

    @Test

    public void getAll_should_callRepository(){
        Mockito.when(mockRepository.getAll())
                .thenReturn(new ArrayList<>());


        service.getAll();

        Mockito.verify(mockRepository,Mockito.times(1))
                .getAll();
    }
@Test
public void getById_should_returnCity_when_matchExist(){

    City mockCity=createMockCity();
    Mockito.when(mockRepository.getById(mockCity.getId()))
            .thenReturn(mockCity);

    City result=service.getById(mockCity.getId());


    Assertions.assertAll(
            ()->Assertions.assertEquals(mockCity.getId(),result.getId()),
            () ->Assertions.assertEquals(mockCity.getCityName(),result.getCityName()),
            () ->Assertions.assertEquals(mockCity.getCountry(),result.getCountry())
    );
}
}
