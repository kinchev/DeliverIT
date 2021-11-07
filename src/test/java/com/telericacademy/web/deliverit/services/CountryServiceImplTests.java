package com.telericacademy.web.deliverit.services;

import com.telericacademy.web.deliverit.models.Country;
import com.telericacademy.web.deliverit.repositories.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.telericacademy.web.deliverit.Helpers.createMockCountry;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTests {

    @Mock
    CountryRepository mockRepository;

    @InjectMocks
    CountryServiceImpl service;


    @Test
    public void getAll_should_callRepository() {
        Mockito.when(mockRepository.getAll())
                .thenReturn(new ArrayList<>());


        service.getAll();

        Mockito.verify(mockRepository, Mockito.times(1)).getAll();

    }

    @Test
    public void getById_should_returnCountry_when_matchExist() {
        Country mockCountry = createMockCountry();
        Mockito.when(mockRepository.getById(mockCountry.getId()))
                .thenReturn(mockCountry);

        Country result = service.getById(mockCountry.getId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockCountry.getId(), result.getId()),
                () -> Assertions.assertEquals(mockCountry.getCountryName(), result.getCountryName())
        );


    }


}
