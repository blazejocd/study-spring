package spittr.data;

import org.springframework.stereotype.Component;

import spittr.Spitter;

@Component
public class Spitters implements SpitterRepository 
{

	@Override
	public Spitter save(Spitter spitter) {
		
		return null;
	}

	@Override
	public Spitter findByName(String name) {
		return new Spitter(24L,name, "mypass","Arkadiusz", "Kocik", "arek.kocik@ui.com");
	}

}
