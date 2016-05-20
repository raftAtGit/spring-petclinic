package org.springframework.samples.petclinic.util;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory.Builder;

public class Mapper extends ConfigurableMapper {
	
	@Override
	protected void configure(MapperFactory factory) {
		//factory.getC
	}
	
	@Override
	protected void configureFactoryBuilder(Builder factoryBuilder) {
		factoryBuilder.mapNulls(false);
	}
}
