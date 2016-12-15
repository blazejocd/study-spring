package spittr.web;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.IOException;

import spittr.Spitter;
import spittr.data.*;

@Controller
@RequestMapping("/spitter")
public class SpitterController 
{
	private SpitterRepository repo;
	
	@Autowired
	public SpitterController(SpitterRepository sr)
	{
		this.repo = sr; 
	}
	
	@RequestMapping(value="/register", method=GET)
	public String showRegistrationForm(Model model) {
		model.addAttribute(new Spitter());
		return "registerForm";
	}
	
	@RequestMapping(value="/register", method=POST)
	public String saveSpitter(
			@RequestPart("profilePicture") byte[] profilePicture,
			@Valid Spitter spitter,
			Errors errors) throws IOException
	{
		if (errors.hasErrors()) {
			return "registerForm";
		}
		repo.save(spitter);
		saveFile(spitter.getProfilePicture(), spitter.getUsername());
		
		return "redirect:/spitter/" +spitter.getUsername();
	}
	
	@RequestMapping(value="/{userName}", method=GET)
	public String showUser(
			@PathVariable String userName, Model model)
	{
		Spitter spitter = this.repo.findByName(userName);
		model.addAttribute(spitter);
		return "profile";
	}
	
	private void saveFile(
			MultipartFile file, 
			String fileName) throws IOException
	{
		file.transferTo(new File(fileName +".jpg"));
	}
}
