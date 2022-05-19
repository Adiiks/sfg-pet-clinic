package com.adrian.sfgpetclinic.repositories;

import com.adrian.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
