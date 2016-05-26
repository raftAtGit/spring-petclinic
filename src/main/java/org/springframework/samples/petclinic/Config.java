package org.springframework.samples.petclinic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.util.Mapper;

import raft.postvayler.spring.EnablePostvayler;

@Configuration
@EnablePostvayler(rootClass="org.springframework.samples.petclinic.model.PetClinic")
public class Config {
	
	@Bean
	public Mapper mapper() {
		return new Mapper();
	}

}
