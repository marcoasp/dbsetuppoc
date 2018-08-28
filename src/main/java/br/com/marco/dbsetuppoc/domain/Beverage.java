package br.com.marco.dbsetuppoc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beverage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Temperature temperature;

	private Beverage() {}
	
	public Beverage(String name, Temperature temperature) {
		this.name = name;
		this.temperature = temperature;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Temperature getTemperature() {
		return temperature;
	}

}
