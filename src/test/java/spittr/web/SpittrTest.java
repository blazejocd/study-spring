package spittr.web;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

import spittr.*;
import spittr.data.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

public class SpittrTest 
{	
	@Test
	public void shouldShowRecentSpittles() throws Exception
	{
		List<Spittle> expectedSpittles = createSpittles(20);
		SpittleRepository mockRepository = mock(SpittleRepository.class);
		when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);
		
		SpittleController controller = new SpittleController(mockRepository);
		MockMvc mockMvc = standaloneSetup(controller)
				.setSingleView(
					new InternalResourceView("/WEB-INF/views/spittle.jsp"))
				.build();
		mockMvc.perform(get("/spittles"))
			.andExpect(view().name("spittles"))
			.andExpect(model().attributeExists("spittleList"))
			.andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
	}
	
	@Test
	public void homeController() throws Exception
	{
		HomeController controller = new HomeController();
		MockMvc mockMvc = standaloneSetup(controller)
				.setSingleView(
						new InternalResourceView("WEB-INF/views/home.jsp"))
				.build();
		mockMvc.perform(get("/"))
			.andExpect(view().name("home"));
	}
	
	@Test
	public void shouldReturnOneSpittle() throws Exception
	{
		Spittle expectedSpittle = new Spittle(
				Long.valueOf(15), "Hejka, hejka! ;)", 
				new Date(1983, 11, 24),1525.25866, 1548.4889);
		
		SpittleRepository mockRepository = mock(SpittleRepository.class);
		when(mockRepository.findOne(Long.valueOf(15))).thenReturn(expectedSpittle);
		
		SpittleController controller = new SpittleController(mockRepository);
		
		MockMvc mockMvc = standaloneSetup(controller).setSingleView(
				new InternalResourceView("WEB-INF/views/spittle.jsp"))
				.build();
		mockMvc.perform(get("/spittles/show/15"))
			.andExpect(view().name("spittle"))
			.andExpect(model().attributeExists("spittle"))
			.andExpect(model().attribute("spittle", expectedSpittle))
			.andExpect(model().attribute("spittle", hasProperty("id", is(Long.valueOf(15)))));
	}
	
	@Test
	public void spitterController() throws Exception
	{
		SpitterRepository mockRepo = mock(SpitterRepository.class);
		SpitterController controller = new SpitterController(mockRepo);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/spitter/register"))
			.andExpect(view().name("registerForm"));
	}
	
	@Test
	public void shouldSaveSpitter() throws Exception
	{
		SpitterRepository mockRepository = mock(SpitterRepository.class);
		Spitter unsaved = new Spitter("blazejocd", "mypass","Błażej", "Kocik", "blazej@ui.pl");
		Spitter saved = new Spitter(24L,"blazejocd", "mypass","Błażej", "Kocik", "blazej@ui.pl");
		when(mockRepository.save(unsaved)).thenReturn(saved);
		
		SpitterController controller = new SpitterController(mockRepository);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(post("/spitter/register")
				.param("username", "blazejocd")
				.param("password", "mypass")
				.param("firstName", "Błażej")
				.param("lastName", "Kocik")
				.param("email", "blazej@ui.pl"))
			.andExpect(redirectedUrl("/spitter/" +saved.getUsername()));
		verify(mockRepository, atLeastOnce()).save(unsaved);
	}
	
	@Test
	public void shouldShowProfile() throws Exception
	{
		SpitterRepository mockRepo = mock(SpitterRepository.class);
		Spitter expectedSpitter = new Spitter(18L, "bolo1", "23245yge","Jerzy","Kochanowski","jerzyk@wp.pl");
		when(mockRepo.findByName("bolo1")).thenReturn(expectedSpitter);
		
		SpitterController controller = new SpitterController(mockRepo);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/spitter/bolo1"))
			.andExpect(model().attribute("spitter", expectedSpitter))
			.andExpect(view().name("profile"));
	}
	
	private List<Spittle> createSpittles(int count)
	{
		List<Spittle> list = new ArrayList<>();
		for(int i = 0; i<count; i++) {
			Spittle spittle = new Spittle("Message " +i, new Date());
			list.add(spittle);
		}
		return list;
	}
}
