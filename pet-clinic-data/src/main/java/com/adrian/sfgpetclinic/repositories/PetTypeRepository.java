package com.adrian.sfgpetclinic.repositories;

import com.adrian.sfgpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

    Optional<PetType> findByName(String name);
}
