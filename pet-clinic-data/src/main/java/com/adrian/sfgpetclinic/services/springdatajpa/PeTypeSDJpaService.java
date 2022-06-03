package com.adrian.sfgpetclinic.services.springdatajpa;

import com.adrian.sfgpetclinic.model.PetType;
import com.adrian.sfgpetclinic.repositories.PetTypeRepository;
import com.adrian.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PeTypeSDJpaService implements PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public PeTypeSDJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {

        Set<PetType> petTypes = new HashSet<>();

        petTypeRepository.findAll().forEach(petTypes::add);

        return petTypes;
    }

    @Override
    public PetType findById(Long id) {
        return petTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PetType save(PetType object) {
        return petTypeRepository.save(object);
    }

    @Override
    public void delete(PetType object) {
        petTypeRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        petTypeRepository.deleteById(id);
    }

    @Override
    public PetType findByName(String name) {
        return petTypeRepository.findByName(name).orElse(null);
    }
}
