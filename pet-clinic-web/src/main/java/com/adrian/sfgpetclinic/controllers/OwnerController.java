package com.adrian.sfgpetclinic.controllers;

import com.adrian.sfgpetclinic.model.Owner;
import com.adrian.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final static String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwner(Model model) {

        model.addAttribute("owner", new Owner());

        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {

        ModelAndView mav = new ModelAndView("owners/ownerDetails");

        Owner owner = ownerService.findById(ownerId);
        mav.addObject(owner);

        return mav;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> owners = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        if (owners.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }
        else if (owners.size() == 1) {
            // 1 owner found
            owner = owners.iterator().next();
            return "redirect:/owners/" + owner.getId();
        }
        else {
            // multiple owners found
            model.addAttribute("selections", owners);

            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String showCreationForm(Model model) {

        model.addAttribute("owner", new Owner());

        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {

        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }

        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String showUpdateOwnerForm(@PathVariable Long ownerId, Model model) {

        model.addAttribute("owner", ownerService.findById(ownerId));

        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@PathVariable Long ownerId, @Valid Owner owner, BindingResult result) {

        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }

        owner.setId(ownerId);
        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }
}
