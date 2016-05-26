package org.springframework.samples.petclinic.util;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory.Builder;

import org.joda.time.LocalDate;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;

public class Mapper extends ConfigurableMapper {
	
	@Override
	protected void configure(MapperFactory factory) {
		
		// do not descend into fields of these classes but simply pass them as they are
		factory.getConverterFactory().registerConverter(new PassThroughConverter(
				LocalDate.class,
				PetType.class
		));
		
		// prevent Orika to descend into collections 
		factory.classMap(Owner.class, Owner.class)
			.exclude("pets")
			.byDefault()
			.register();
		
		factory.classMap(Vet.class, Vet.class)
			.exclude("specialties")
			.byDefault()
			.register();
		
		factory.classMap(Pet.class, Pet.class)
			.exclude("visits")
			.byDefault()
			.register();
	}
	
	@Override
	protected void configureFactoryBuilder(Builder factoryBuilder) {
		//factoryBuilder.mapNulls(false);
	}
}
