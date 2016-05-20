package org.springframework.samples.petclinic.model;

import java.io.Serializable;
import java.util.Map;

import org.springframework.samples.petclinic.util.AutoIdMap;

import raft.postvayler.Persistent;

/** 
 * 
 * @author Hakan Eryargi (r a f t)
 * */
@Persistent
public class PetClinic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final AutoIdMap<Vet> vets = new AutoIdMap.Impl<>();
	
	private final AutoIdMap<Owner> owners = new AutoIdMap.Impl<>();
	
	private final AutoIdMap<PetType> petTypes = new AutoIdMap.Impl<>();

	private final AutoIdMap<Pet> pets = new AutoIdMap.Impl<>();
	
	
	public Map<Integer, Vet> getVets() {
		return vets;
	}

	public AutoIdMap<Owner> getOwners() {
		return owners;
	}

	public Map<Integer, PetType> getPetTypes() {
		return petTypes;
	}
	
	public Map<Integer, Pet> getPets() {
		return pets;
	}
	
	
	
	
}
