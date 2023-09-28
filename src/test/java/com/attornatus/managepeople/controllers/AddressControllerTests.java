package com.attornatus.managepeople.controllers;

import com.attornatus.managepeople.Factory;
import com.attornatus.managepeople.dto.AddressDTO;
import com.attornatus.managepeople.services.AddressService;
import com.attornatus.managepeople.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
public class AddressControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService service;

    private AddressDTO addressDTO;
    private List<AddressDTO> list;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        addressDTO = Factory.createAddressDTO();
        list = Arrays.asList(addressDTO);
        existingId = 1L;
        nonExistingId = 99L;

        when(service.findByPeopleId(existingId)).thenReturn(list);
        when(service.findByPeopleId(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(service.insert(any())).thenReturn(addressDTO);
        doNothing().when(service).updateMainAddressByPeopleId(eq(existingId), any());
        doThrow(ResourceNotFoundException.class).when(service).updateMainAddressByPeopleId(eq(nonExistingId), any());
    }

    @Test
    public void findByPeopleIdShouldReturnList() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/address/{peopleId}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByPeopleIdShouldReturnNotFoundWhenPeopleIdDoesNotExist() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/address/{peopleId}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnAddressDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(addressDTO);

        ResultActions result =
                mockMvc.perform(post("/address")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.zipCode").exists());
        result.andExpect(jsonPath("$.street").exists());
        result.andExpect(jsonPath("$.number").exists());
        result.andExpect(jsonPath("$.city").exists());
        result.andExpect(jsonPath("$.main").exists());
    }

    @Test
    public void updateMainAddressByPeopleIdShouldReturnNoContentWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(addressDTO);

        ResultActions result =
                mockMvc.perform(put("/address/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void updateMainAddressByPeopleIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(addressDTO);

        ResultActions result =
                mockMvc.perform(put("/address/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
