package br.com.b2w.starwars.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.b2w.starwars.dao.PlanetRepository;
import br.com.b2w.starwars.model.Planet;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetResourceTest {

	private static final String resource = "planets";
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private PlanetRepository repository;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;	

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Test
	public void addTest() throws Exception {

		// Limpa a base de testes
		this.repository.deleteAll();

		List<Planet> planets = new ArrayList<Planet>();

		planets.add(new Planet("Alderaan", "Temperado", "Montanhoso"));
		planets.add(new Planet("Tatooine", "Árido", "Deserto"));
		planets.add(new Planet("Yavin", "Tropical", "Floresta"));
		planets.add(new Planet("Hoth", "Gelado", "Montanhoso"));
		planets.add(new Planet("Dagobah", "Escuro", "Floresta"));

		planets.forEach(planet -> {
			try {
				mvc.perform(MockMvcRequestBuilders.put("/" + resource).contentType(MediaType.APPLICATION_JSON)
						.content(json(planet))).andExpect(status().isCreated())
						.andExpect(content().string(notNullValue()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Test
	public void fetchTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/nome/Alderaan").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(302)).andExpect(content().string(notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Alderaan"));
		
	}
	
	@Test
	public void fetchByNameTest() throws Exception {

		Planet dagobah = repository.findByNome("Dagobah");
		
		assertNotNull(dagobah);
		
		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/" + dagobah.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(302)).andExpect(content().string(notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Dagobah"));
		
		
	}
	
	@Test
	public void nonExistsTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/036").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404));
		
		
	}

	@Test
	public void listTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(notNullValue()));
	}

	@Test
	public void removeTest() throws Exception {
		
		Planet planetToRemove = repository.findByNome("Yavin");

		mvc.perform(MockMvcRequestBuilders.delete("/" + resource + "/" + planetToRemove.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(equalTo("")));
	}
	
	private String json(Object o) throws IOException {
		try {
			MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
			this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
			return mockHttpOutputMessage.getBodyAsString();
		} catch (Exception e) {
			throw e;
		}
	}
	
}
