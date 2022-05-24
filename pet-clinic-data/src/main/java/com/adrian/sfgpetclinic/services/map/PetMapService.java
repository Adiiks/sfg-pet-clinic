package com.adrian.sfgpetclinic.services.map;

import com.adrian.sfgpetclinic.model.Pet;
import com.adrian.sfgpetclinic.services.PetService;
import com.adrian.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {

    private final PetTypeService petTypeService;

    public PetMapService(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet object) {
        super.delete(object);
    }

    @Override
    public Pet save(Pet object) {
        if (object.getPetType() != null) {
            if (object.getPetType().getId() == null) {
                object.setPetType(petTypeService.save(object.getPetType()));
            }
        } else {
            throw new RuntimeException("Pet type is required !");
        }

        return super.save(object);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
