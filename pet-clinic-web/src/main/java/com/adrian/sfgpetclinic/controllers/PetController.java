package com.adrian.sfgpetclinic.controllers;

import com.adrian.sfgpetclinic.model.Owner;
import com.adrian.sfgpetclinic.model.Pet;
import com.adrian.sfgpetclinic.model.PetType;
import com.adrian.sfgpetclinic.services.OwnerService;
import com.adrian.sfgpetclinic.services.PetService;
import com.adrian.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final PetService petService;

    public PetController(OwnerService ownerService, PetTypeService petTypeService, PetService petService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String showCreationForm(Model model, Owner owner) {

        Pet pet = new Pet();
        owner.getPets().add(pet);

        model.addAttribute("pet", pet);

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet petToSave, BindingResult result, Model model) {

        if (StringUtils.hasLength(petToSave.getName()) && petToSave.isNew() &&
                owner.getPet(petToSave.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }

        owner.getPets().add(petToSave);
        petToSave.setOwner(owner);

        if (result.hasErrors()) {
            model.addAttribute("pet", petToSave);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }

        petService.save(petToSave);

        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String showUpdateForm(@PathVariable Long petId, Model model) {

        model.addAttribute("pet", petService.findById(petId));

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(Model model, @Valid Pet pet, BindingResult result, Owner owner) {

        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }

        owner.getPets().add(pet);
        petService.save(pet);

        return "redirect:/owners/" + owner.getId();
    }
}
