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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    Set<Owner> owners;
    Owner owner;

    final Long ID = 1L;
    final String LAST_NAME = "last name";

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

        owner = Owner.builder().id(ID).lastName(LAST_NAME).build();

        owners = new HashSet<>();
        owners.add(owner);
    }

    @Test
    void findOwner() throws Exception {

        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processFindFormReturnMany() throws Exception {

        List<Owner> ownersList = new ArrayList<>();

        ownersList.add(Owner.builder().id(ID + 1).lastName(LAST_NAME).build());
        ownersList.add(Owner.builder().id(ID).lastName(LAST_NAME).build());

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownersList);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", ownersList));
    }

    @Test
    void processFindFormReturnOne() throws Exception {

        List<Owner> ownersList = new ArrayList<>();

        ownersList.add(Owner.builder().id(ID + 1).lastName(LAST_NAME).build());

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownersList);

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/2"));
    }

    @Test
    void showOwner() throws Exception {

        Owner owner = Owner.builder().id(ID).build();

        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1/"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", owner));
    }

    @Test
    void initCreationForm() throws Exception {

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {

        when(ownerService.save(any(Owner.class))).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(any(Owner.class));
    }

    @Test
    void showUpdateOwnerForm() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void processUpdateOwnerForm() throws Exception {

        when(ownerService.save(any(Owner.class))).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).save(any(Owner.class));
    }
}