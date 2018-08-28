package br.com.marco.dbsetuppoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marco.dbsetuppoc.domain.Customer;
import br.com.marco.dbsetuppoc.domain.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;
	
	@GetMapping
	public List<Customer> listAll() {
		return repository.findAll();
	}
	
	@PostMapping
	public Customer create(@RequestBody Customer customer) {
		return repository.save(customer);
	}
}
