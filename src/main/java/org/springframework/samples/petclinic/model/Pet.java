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
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.util.AutoIdMap;

import raft.postvayler.Persist;
import raft.postvayler.Persistent;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Hakan Eryargi (r a f t)
 */
@Persistent
public class Pet extends NamedEntity {
	
	private static final long serialVersionUID = 1L;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    private PetType type;

    private Owner owner;

    private final AutoIdMap<Visit> visits = new AutoIdMap.Impl<>();

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    @Persist
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PetType getType() {
        return this.type;
    }

    @Persist
    public void setType(PetType type) {
        this.type = type;
    }

    public Owner getOwner() {
        return this.owner;
    }

    @Persist
    protected void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Visit> getVisits() {
        List<Visit> sortedVisits = new ArrayList<>(visits.values());
        PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedVisits);
    }

    @Persist
    public void addVisit(Visit visit) {
    	visits.put(visit);
        visit.setPet(this);
    }

}
