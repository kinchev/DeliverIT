package com.telericacademy.web.deliverit.controllers;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.City;
import com.telericacademy.web.deliverit.services.CityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.telericacademy.web.deliverit.Helpers.createMockCity;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTests {

    @MockBean
    CityService mockCityService;

    @Autowired
    MockMvc mockMvc;

    @Test

    public void getById_should_return_statusOK_when_CityExists() throws Exception{

        City mockCity = createMockCity();
        Mockito.when(mockCityService.getById(1))
                .thenReturn(mockCity);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value(mockCity.getCityName()));

    }
@Test
public void getById_should_return_statusNotFound_when_cityDoesExist() throws Exception{

        Mockito.when(mockCityService.getById(Mockito.anyInt()))
                .thenThrow(EntityNotFoundException.class);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
}


}
