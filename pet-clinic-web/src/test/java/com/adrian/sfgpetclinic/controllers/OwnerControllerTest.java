package com.adrian.sfgpetclinic.controllers;

import com.adrian.sfgpetclinic.model.Owner;
import com.adrian.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    Set<Owner> owners;

    final Long ID = 1L;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

        owners = new HashSet<>();
        owners.add(Owner.builder().id(ID).build());
    }

    @Test
    void listOwners() throws Exception {

        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(1)));

    }

    @Test
    void listOwnersByIndex() throws Exception {

        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(1)));

    }

    @Test
    void findOwner() throws Exception {

        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notImplemented"));
    }
}