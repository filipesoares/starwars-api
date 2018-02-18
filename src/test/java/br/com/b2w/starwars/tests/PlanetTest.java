package br.com.b2w.starwars.tests;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.b2w.starwars.model.Planet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testA() {
		
		Planet p = new Planet(null, "Temperado", "Montanhoso");

		Set<ConstraintViolation<Planet>> constraintViolations = validator.validate(p);

		assertEquals(1, constraintViolations.size());
		assertEquals("Informe o nome do planeta", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void testB() {
		
		Planet p = new Planet("Yani", null, "Montanhoso");

		Set<ConstraintViolation<Planet>> constraintViolations = validator.validate(p);

		assertEquals(1, constraintViolations.size());
		assertEquals("Informe o clima do planeta", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void testC() {
		
		Planet p = new Planet("Yani", "Temperado", null);

		Set<ConstraintViolation<Planet>> constraintViolations = validator.validate(p);

		assertEquals(1, constraintViolations.size());
		assertEquals("Informe o terreno do planeta", constraintViolations.iterator().next().getMessage());
	}

}
