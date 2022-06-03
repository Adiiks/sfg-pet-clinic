package com.adrian.sfgpetclinic.services;

import com.adrian.sfgpetclinic.model.PetType;

public interface PetTypeService extends CrudService<PetType, Long> {

    PetType findByName(String name);
}
