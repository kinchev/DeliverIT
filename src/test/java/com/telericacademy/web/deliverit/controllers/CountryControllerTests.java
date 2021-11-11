package com.telericacademy.web.deliverit.controllers;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Country;
import com.telericacademy.web.deliverit.services.contracts.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.telericacademy.web.deliverit.Helpers.createMockCountry;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTests {

    @MockBean
    CountryService mockCountryService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getById_should_return_statusNotFound_when_countryDoesntExist() throws Exception {

        Mockito.when(mockCountryService.getById(Mockito.anyInt()))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform((MockMvcRequestBuilders.get("/api/countries/{id}",1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

@Test
    public void getById_should_return_statusOk_when_countryExist() throws Exception {
        Country mockCountry=createMockCountry();
        Mockito.when(mockCountryService.getById(1))
                .thenReturn(mockCountry);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/countries/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countryName").value(mockCountry.getCountryName()));
}


}
