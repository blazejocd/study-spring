package spittr.data;

import spittr.Spitter;;


public interface SpitterRepository 
{	
	Spitter save(Spitter spitter);
	
	Spitter findByUsername(String name);
	
	Spitter findSpitterById(Long id);
	
	Spitter[] findAllSpitters();
}
