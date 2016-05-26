package org.springframework.samples.petclinic.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.time.format.DateTimeFormat;
import org.springframework.samples.petclinic.util.AutoIdMap;
import org.springframework.samples.petclinic.util.Mapper;
import org.springframework.util.Assert;

import raft.postvayler.Persist;
import raft.postvayler.Persistent;

/** 
 * 
 * @author Hakan Eryargi (r a f t)
 * */
@Persistent
public class PetClinic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO make this autowired
	private transient Mapper mapper = new Mapper();
	
	private final AutoIdMap<Vet> vets = new AutoIdMap.Impl<>();
	
	private final AutoIdMap<Owner> owners = new AutoIdMap.Impl<>();
	
	private final AutoIdMap<PetType> petTypes = new AutoIdMap.Impl<>();

	private final AutoIdMap<Pet> pets = new AutoIdMap.Impl<>();
	
	private final AutoIdMap<Specialty> specialties = new AutoIdMap.Impl<>();
	
	private boolean populated = false;
	
	public PetClinic() {} 
	
	public Map<Integer, Vet> getVets() {
		return Collections.unmodifiableMap(vets);
	}

	public Map<Integer, Owner> getOwners() {
		return Collections.unmodifiableMap(owners);
	}

	@Persist
	public void saveOwner(Owner owner) {
    	if (owner.isNew()) {
    		owners.put(owner);
    	} else {
    		mapper.map(owner, owners.get(owner.getId())); 
    	}
	}
	
	@Persist
	public void savePet(Pet pet) {
    	if (pet.isNew()) {
    		pets.put(pet);
    	} else {
    		mapper.map(pet, pets.get(pet.getId())); 
    	}
	}
	
	@Persist
	public void saveVisit(Visit visit) {
		Assert.isTrue(pets.get(visit.getPet().getId()) == visit.getPet(), "Visit's Pet is not attached to root");
		// simply attach visit to pet
		visit.getPet().addVisit(visit);
	}

	
	public Map<Integer, PetType> getPetTypes() {
		return Collections.unmodifiableMap(petTypes);
	}
	
	public Map<Integer, Pet> getPets() {
		return Collections.unmodifiableMap(pets);
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public boolean isPopulated() {
		return populated;
	}

	/**
	 *  populate PetClinic with some initial/sample data. It"s important that this method should be called from outside 
	 * (i.e. not in constructor) since otherwise {@link Persistent} objects created in this method will be outside of control of Postvayler.
	 * One will get an exception when such an object is attempted to be modified via a {@link Persist} method.   
	*/
	@Persist
	public void populate() {
		specialties.put(new Specialty("radiology"));
		specialties.put(new Specialty("surgery"));
		specialties.put(new Specialty("dentistry"));
		
		vets.put(createVet("James", "Carter"));
		vets.put(createVet("Helen", "Leary", "radiology"));
		vets.put(createVet("Linda", "Douglas", "surgery", "dentistry"));
		vets.put(createVet("Rafael", "Ortega", "surgery"));
		vets.put(createVet("Henry", "Stevens", "radiology"));
		vets.put(createVet("Sharon", "Jenkins"));

		petTypes.put(new PetType("cat"));
		petTypes.put(new PetType("dog"));
		petTypes.put(new PetType("lizard"));
		petTypes.put(new PetType("snake"));
		petTypes.put(new PetType("bird"));
		petTypes.put(new PetType("hamster"));
		
		pets.put(createPet("Leo", "cat", "2010-09-07"));
		pets.put(createPet("Basil", "hamster", "2012-08-06"));
		pets.put(createPet("Rosy", "dog", "2011-04-17"));
		pets.put(createPet("Jewel", "dog", "2010-03-07"));
		pets.put(createPet("Iggy", "lizard", "2010-11-30"));
		pets.put(createPet("George", "snake", "2010-01-20"));
		pets.put(createPet("Samantha", "cat", "2012-09-04"));
		pets.put(createPet("Max", "cat", "2012-09-04"));
		pets.put(createPet("Lucky", "bird", "2011-08-06"));
		pets.put(createPet("Mulligan", "dog", "2007-02-24"));
		pets.put(createPet("Freddy", "bird", "2010-03-09"));
		pets.put(createPet("Lucky", "dog", "2010-06-24"));
		pets.put(createPet("Sly", "cat", "2012-06-08"));
		
		
		owners.put(createOwner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023", "Leo"));
		owners.put(createOwner("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749", "Basil"));
		owners.put(createOwner("Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763", "Jewel", "Rosy"));
		owners.put(createOwner("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198", "Iggy"));
		owners.put(createOwner("Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765", "George"));
		owners.put(createOwner("Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654", "Max", "Samantha"));
		owners.put(createOwner("Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387", "Lucky"));
		owners.put(createOwner("Maria", "Escobito", "345 Maple St.", "Madison", "6085557683", "Mulligan"));
		owners.put(createOwner("David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435", "Freddy"));
		owners.put(createOwner("Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487", "Lucky", "Sly"));
		
		createVisit("Samantha", "2013-01-01", "rabies shot");
		createVisit("Max", "2013-01-02", "rabies shot");
		createVisit("Max", "2013-01-03", "neutered");
		createVisit("Samantha", "2013-01-04", "spayed");
		
		populated = true;
	}
	
	private Vet createVet(String firstName, String lastName, String... specialtyNames) {
		Vet vet = new Vet();
		
		vet.setFirstName(firstName);
		vet.setLastName(lastName);
		
		for (String specialty : specialtyNames) {
			vet.addSpecialty(specialties.values().stream()
					.filter(x -> x.getName().equals(specialty))
					.findAny()
					.orElseThrow(() -> new NoSuchElementException(specialty)));
		}
		
		return vet;
	}
	
	private Owner createOwner(String firstName, String lastName, String address, String city, String telephone, String... petNames) {
		Owner owner = new Owner();
		
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setAddress(address);
		owner.setCity(city);
		owner.setTelephone(telephone);
		
		for (String pet : petNames) {
			owner.addPet(pets.values().stream()
					.filter(x -> x.getName().equals(pet))
					.findAny()
					.orElseThrow(() -> new NoSuchElementException(pet)));
		}
		
		return owner;
	}
	
	private Pet createPet(String name, String type, String birthDate) {
		Pet pet = new Pet();
		
		pet.setName(name);
		pet.setBirthDate(DateTimeFormat.forPattern("yyyy-mm-dd").parseLocalDate(birthDate));
		
		pet.setType(petTypes.values().stream()
				.filter(x -> x.getName().equals(type))
				.findAny()
				.orElseThrow(() -> new NoSuchElementException(type)));
		
		return pet;
	}
	
	private void createVisit(String petName, String date, String description) {
		Pet pet = pets.values().stream()
			.filter(x -> x.getName().equals(petName))
			.findAny()
			.orElseThrow(() -> new NoSuchElementException(petName));
	
		Visit visit = new Visit();
		visit.setDate(DateTimeFormat.forPattern("yyyy-mm-dd").parseLocalDate(date));
		visit.setDescription(description);
		
		pet.addVisit(visit);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.mapper = new Mapper(); 
	}

	
}
