package com.adrian.sfgpetclinic.formatters;

import com.adrian.sfgpetclinic.model.PetType;
import com.adrian.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String name, Locale locale) throws ParseException {

        PetType petTypeFound = petTypeService.findByName(name);

        if (petTypeFound != null) {
            return petTypeFound;
        } else {
            throw new ParseException("type not found: " + name, 0);
        }
    }
}
