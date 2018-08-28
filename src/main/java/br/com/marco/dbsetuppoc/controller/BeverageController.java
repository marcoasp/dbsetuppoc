package br.com.marco.dbsetuppoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marco.dbsetuppoc.domain.Beverage;
import br.com.marco.dbsetuppoc.domain.BeverageRepository;

@RestController
@RequestMapping("/beverages")
public class BeverageController {
	
	@Autowired
	private BeverageRepository repository;
	
	@GetMapping
	public List<Beverage> getAll() {
		return repository.findAll();
	}
	
	@PostMapping
	public Beverage create(@RequestBody Beverage newBeverage) {
		return repository.save(newBeverage);
	}
}
