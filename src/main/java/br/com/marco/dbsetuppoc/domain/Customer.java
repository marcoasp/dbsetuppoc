package br.com.marco.dbsetuppoc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	private String firstName;

	private String lastName;

	@ManyToOne
	@JoinColumn(name = "FAVORITE_BEVERAGE", nullable = false)
	private Beverage favoriteBeverage;
	
	private Customer() {}

	public Customer(String firstName, String lastName, Beverage favoriteBeverage) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.favoriteBeverage = favoriteBeverage;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Beverage getFavoriteBeverage() {
		return favoriteBeverage;
	}
}
