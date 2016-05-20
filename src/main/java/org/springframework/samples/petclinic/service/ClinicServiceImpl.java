/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetClinic;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.util.Mapper;
import org.springframework.stereotype.Service;

import raft.postvayler.spring.EnablePostvayler;

/**
 * <p>Mostly used as a facade for all Petclinic controllers</p>
 * 
 * <p>Adapted to use Postvayler. Removed all previous repositories and @Transactional and @Cacheable annotations. 
 * There is no need for caching since all data is in memory in Postvayler. Rollback support is configured at 
 * {@link EnablePostvayler} annotation.</p> 
 *
 * @author Michael Isvy
 * @author Hakan Eryargi (r a f t)
 */
@Service
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	private PetClinic petClinic;
	
	@Autowired
	private Mapper mapper;
	
    @Override
    public Collection<PetType> findPetTypes() {
        return petClinic.getPetTypes().values();
    }

    @Override
    public Owner findOwnerById(int id) {
        return petClinic.getOwners().get(id);
    }

    @Override
    public Collection<Owner> findOwnerByLastName(String lastName) {
    	return petClinic.getOwners().values().stream()
    			.filter(x -> lastName.equalsIgnoreCase(x.getLastName()))
				.collect(Collectors.toList());
    }

    @Override
    public void saveOwner(Owner owner) {
    	if (owner.isNew()) {
    		petClinic.getOwners().put(owner);
    	} else {
    		mapper.map(owner, petClinic.getOwners().get(owner.getId()));
    	}
    }


    @Override
    public void saveVisit(Visit visit) {
        visitRepository.save(visit);
    }


    @Override
    public Pet findPetById(int id) {
        return petClinic.getPets().get(id);
    }

    @Override
    public void savePet(Pet pet) {
        petRepository.save(pet);
    }

    @Override
    public Collection<Vet> findVets() {
        return petClinic.getVets().values();
    }


}
