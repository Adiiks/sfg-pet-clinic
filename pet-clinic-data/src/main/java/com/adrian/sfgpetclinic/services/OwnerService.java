package com.adrian.sfgpetclinic.services;

import com.adrian.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
}
