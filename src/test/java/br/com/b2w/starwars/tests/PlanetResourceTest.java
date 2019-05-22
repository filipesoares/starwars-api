package br.com.b2w.starwars.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.b2w.starwars.repository.PlanetRepository;
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

	@Before
	public void begin() {
		List<Planet> planets = new ArrayList<Planet>();

		planets.add(new Planet("Alderaan", "Temperado", "Montanhoso"));
		planets.add(new Planet("Tatooine", "Árido", "Deserto"));
		planets.add(new Planet("Yavin", "Tropical", "Floresta"));
		planets.add(new Planet("Hoth", "Gelado", "Montanhoso"));
		planets.add(new Planet("Dagobah", "Escuro", "Floresta"));
		planets.add(new Planet("Terra", "Temperado", "Floresta"));

		planets.forEach(planet -> {
			repository.save(planet);
		});
	}

	@After
	public void end() {
		repository.deleteAll();
	}

	@Test
	public void create() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/" + resource).contentType(MediaType.APPLICATION_JSON).content(json(new Planet("Hoth", "Gelado", "Montanhoso"))))
			.andExpect(status().isCreated())
			.andExpect(content().string(notNullValue()))
			.andExpect(header().exists("Location"));

	}

	@Test
	public void list() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/" + resource).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(notNullValue()));

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "?nome=Alderaan").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Alderaan"));

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "?nome=Dagobah").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Dagobah"));
	}

	@Test
	public void fetch() throws Exception {

		Planet yavin = repository.findByNome("Yavin");

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/" + yavin.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Yavin"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.filmes").value(1));

		Planet dagobah = repository.findByNome("Dagobah");

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/" + dagobah.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Dagobah"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.filmes").value(3));

		Planet terra = repository.findByNome("Terra");

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/" + terra.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Terra"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.filmes").value(0));

	}

	@Test
	public void remove() throws Exception {

		Planet planet = repository.findByNome("Yavin");

		mvc.perform(MockMvcRequestBuilders.delete("/" + resource + "/" + planet.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().is(204))
			.andExpect(content().string(equalTo("")));
	}

	@Test
	public void addNullTest() throws Exception {

		List<Planet> planets = new ArrayList<Planet>();

		planets.add(new Planet(null, "Temperado", "Montanhoso"));
		planets.add(new Planet("Tatooine", null, "Deserto"));
		planets.add(new Planet("Yavin", "Tropical", null));

		planets.forEach(planet -> {
			try {
				mvc.perform(MockMvcRequestBuilders.put("/" + resource).contentType(MediaType.APPLICATION_JSON)
						.content(json(planet))).andExpect(status().is4xxClientError())
						.andExpect(content().string(notNullValue()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Test
	public void nonExistsTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/" + System.currentTimeMillis())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is(404));

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
