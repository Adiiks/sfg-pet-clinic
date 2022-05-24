package com.adrian.sfgpetclinic.services.springdatajpa;

import com.adrian.sfgpetclinic.model.Owner;
import com.adrian.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @InjectMocks
    OwnerSDJpaService ownerService;

    @Mock
    OwnerRepository ownerRepository;

    final Long ID = 1L;
    final String LAST_NAME = "Smith";

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {

        Set<Owner> ownersToReturn = new HashSet<>();
        ownersToReturn.add(Owner.builder().id(ID).build());

        when(ownerRepository.findAll()).thenReturn(ownersToReturn);

        Set<Owner> owners = ownerService.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    void findById() {

        Owner ownerToReturn = Owner.builder().id(ID).build();

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(ownerToReturn));

        Owner owner = ownerService.findById(ID);

        assertEquals(ID, owner.getId());
    }

    @Test
    void findByIdNotFound() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerService.findById(ID);

        assertNull(owner);
    }

    @Test
    void save() {

        Owner ownerToSave = Owner.builder().id(ID).build();

        when(ownerRepository.save(any(Owner.class))).thenReturn(ownerToSave);

        Owner savedOwner = ownerService.save(ownerToSave);

        assertNotNull(savedOwner);
    }

    @Test
    void delete() {

        Owner ownerToDelete = Owner.builder().id(ID).build();

        ownerService.delete(ownerToDelete);

        verify(ownerRepository, times(1)).delete(any(Owner.class));
    }

    @Test
    void deleteById() {

        ownerService.deleteById(ID);

        verify(ownerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void findByLastName() {

        Owner returnOwner = Owner.builder().id(ID).lastName(LAST_NAME).build();

        when(ownerRepository.findByLastName(anyString())).thenReturn(returnOwner);

        Owner smith = ownerService.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository, times(1)).findByLastName(anyString());
    }
}