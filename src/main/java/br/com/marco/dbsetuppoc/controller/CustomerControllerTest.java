package br.com.marco.dbsetuppoc.controller;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import com.ninja_squad.dbsetup.operation.Operation;

import br.com.marco.dbsetuppoc.dbsetup.CommonOperations;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DataSource dataSource;
	
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Before
	public void before() {
		
		Operation setup = 
				sequenceOf(
					CommonOperations.DATABASE_SETUP,
					insertInto("CUSTOMER")
						.columns("ID", "FIRST_NAME","LAST_NAME", "FAVORITE_BEVERAGE")
						.values(1, "Marco", "Prado", 1)
						.build()
				);
		
		DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), setup);
	
		dbSetupTracker.launchIfNecessary(dbSetup);
	}
	
	@Test
	public void mustListCustomers() throws Exception {
		dbSetupTracker.skipNextLaunch(); //Da skip no setup de banco do proximo teste, pois o corrente é read-only
		
		mockMvc.perform(get("/customers"))
		.andExpect(jsonPath("$.[0].id", equalTo(1)))
		.andExpect(jsonPath("$.[0].firstName", equalTo("Marco")))
		.andExpect(jsonPath("$.[0].lastName",  equalTo("Prado")))
		.andExpect(jsonPath("$.[0].favoriteBeverage.id", equalTo(1)))
		;
	}
	
	@Test
	public void mustCreateCustomer() throws Exception {
		String requestJson = "{\"firstName\":\"Fulano\", \"lastName\":\"Silva\",\"favoriteBeverage\": {\"id\":2}}";
		
		mockMvc.perform(post("/customers").content(requestJson).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print())
		.andExpect(jsonPath("$.id", equalTo(2)))
		.andExpect(jsonPath("$.firstName", equalTo("Fulano")))
		.andExpect(jsonPath("$.lastName",  equalTo("Silva")))
		.andExpect(jsonPath("$.favoriteBeverage.id", equalTo(2)))
		;
	}
}
