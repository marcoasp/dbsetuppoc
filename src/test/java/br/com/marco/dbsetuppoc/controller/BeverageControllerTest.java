package br.com.marco.dbsetuppoc.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;

import br.com.marco.dbsetuppoc.dbsetup.CommonOperations;
import br.com.marco.dbsetuppoc.domain.Temperature;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BeverageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DataSource dataSource;
	
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Before
	public void before() {
		DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), 
				CommonOperations.DATABASE_SETUP);
		
		dbSetupTracker.launchIfNecessary(dbSetup);
	}
	
	@Test
	public void mustListAllBeverages() throws Exception {
		dbSetupTracker.skipNextLaunch(); //Da skip no setup de banco do proximo teste, pois o corrente é read-only
		
		mockMvc.perform(get("/beverages"))
		.andExpect(jsonPath("$.[*]", hasSize(2)))
		.andExpect(jsonPath("$.[0].name", equalTo("Espresso")))
		.andExpect(jsonPath("$.[0].temperature", equalTo(Temperature.HOT.toString())))
		.andExpect(jsonPath("$.[1].name", equalTo("Cold Brew")))
		.andExpect(jsonPath("$.[1].temperature", equalTo(Temperature.COLD.toString())))
		;
	}
	
	@Test
	public void mustInsertNewBeverage() throws Exception {
		String newBeverage = "{\"name\":\"Mocha\",\"temperature\":\"HOT\"}";
		
		mockMvc.perform(post("/beverages").content(newBeverage).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id", equalTo(3)))
		.andExpect(jsonPath("$.name", equalTo("Mocha")))
		.andExpect(jsonPath("$.temperature", equalTo(Temperature.HOT.toString())))
		;
	}
}
