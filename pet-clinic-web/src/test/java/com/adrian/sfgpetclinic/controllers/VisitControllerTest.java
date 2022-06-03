package com.adrian.sfgpetclinic.controllers;

import com.adrian.sfgpetclinic.model.Pet;
import com.adrian.sfgpetclinic.model.Visit;
import com.adrian.sfgpetclinic.services.PetService;
import com.adrian.sfgpetclinic.services.springdatajpa.VisitService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    Pet pet;
    Set<Visit> visits = new HashSet<>();

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        Pet pet = Pet.builder().id(1L).visits(visits).build();

        when(petService.findById(anyLong())).thenReturn(pet);
    }

    @Test
    void showNewVisitForm() throws Exception {

        mockMvc.perform(get("/owners/*/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet", "visit"));
    }

    @Test
    void processNewVisitForm() throws Exception {

        mockMvc.perform(post("/owners/1/pets/2/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }
}