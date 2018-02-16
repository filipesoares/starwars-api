package br.com.b2w.starwars.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.JsonPath;

import br.com.b2w.starwars.model.Planet;
import br.com.b2w.starwars.rest.PlanetResource;
import br.com.b2w.starwars.service.PlanetService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// @WebMvcTest(PlanetResource.class)
public class PlanetResourceTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	Planet planet;
	
	// @Autowired
	// @MockBean
	// PlanetService service;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	private static final String resource = "planets";
	/*
	private static String authorization;
	private static final String username = "admin";
	private static final String password = "123456";
	private static final String resource = "card";

	private static final LocalDateTime now = LocalDateTime.now();
	*/
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
	
	@Before
	public void setUp() throws Exception {
		/*
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
		authorization = "Basic " + new String(encodedAuth);
		*/		
	}
	
	/*
	@Test
	public void addTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.put("/" + resource).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(new Card("Card " + Math.random(), true, CreditCardNumberGenerator.generate("5453", 16), 3, now.plusYears(10), 25,
						new BigDecimal(3000), 1, 15, null)))
				.header("Authorization", authorization)).andExpect(status().isCreated())
				.andExpect(content().string(equalTo("")));
		
		account.setId(1);

		mvc.perform(MockMvcRequestBuilders.put("/" + resource).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(new Card("Card " + Math.random(), true, CreditCardNumberGenerator.generate("5453", 16), 3, now.plusYears(10), 25,
						new BigDecimal(3000), 1, 15, account)))
				.header("Authorization", authorization)).andExpect(status().isCreated())
				.andExpect(content().string(equalTo("")));
	}
	*/
	@Test
	public void fetchTest() throws Exception {
		
		String jsonContent = "{\"id\":2,\"nome\":\"Alderaan\",\"clima\":\"Temperado\",\"terreno\":\"Montanhoso\",\"filmes\":2}";
		
		mvc.perform(MockMvcRequestBuilders
				.get("/" + resource + "/2/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(302))
				.andExpect(content().string(notNullValue()))
				.andExpect(content().string(jsonContent))
				;
	}
	/*
	@Test
	public void fetchTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/" + resource + "/" + 1).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", authorization)).andExpect(status().isOk())
				.andExpect(content().string(notNullValue()));
	}

	@Test
	public void updateTest() throws Exception {

		card = service.find(5);

		card.setEnabled(true);
		card.setNumber(CreditCardNumberGenerator.generate("5453", 16));

		mvc.perform(MockMvcRequestBuilders.post("/" + resource + "/" + 2).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json(card)).header("Authorization", authorization))
				.andExpect(status().isOk()).andExpect(content().string(notNullValue()));
	}
	*/
	/*
	 * @Test public void removeTest() throws Exception {
	 * 
	 * mvc.perform(MockMvcRequestBuilders.delete("/" + resource + "/" + 3)
	 * .accept(MediaType.APPLICATION_JSON) .contentType(MediaType.APPLICATION_JSON)
	 * .content(json(company)) .header("Authorization", authorization))
	 * .andExpect(status().isOk()) .andExpect(content().string(equalTo(""))); }
	 */
	protected String json(Object o) throws IOException {
		try {
			MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
			this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
			return mockHttpOutputMessage.getBodyAsString();
		} catch (Exception e) {
			throw e;
		}
	}

}
