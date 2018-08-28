package br.com.marco.dbsetuppoc.dbsetup;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;

import br.com.marco.dbsetuppoc.domain.Temperature;

public class CommonOperations {

	public static final Operation DELETE_ALL = 
			deleteAllFrom("CUSTOMER", "BEVERAGE");
	
	public static final Operation INSERT_BEVERAGES = 
			insertInto("BEVERAGE")
				.withGeneratedValue("ID", ValueGenerators.sequence().startingAt(1).incrementingBy(1))
				.columns("NAME", "TEMPERATURE")
				.values("Espresso", Temperature.HOT)
				.values("Cold Brew", Temperature.COLD)
				.build();
	
	public static final Operation DATABASE_SETUP = 
			sequenceOf(DELETE_ALL, INSERT_BEVERAGES);
}
