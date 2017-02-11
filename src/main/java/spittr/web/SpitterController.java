package spittr.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.IOException;

import spittr.Spitter;
import spittr.data.*;
import spittr.exceptions.SpitterNotFoundException;

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
			Errors errors, 
			RedirectAttributes model) throws IOException
	{
		if (errors.hasErrors()) {
			return "registerForm";
		}
		if (null != this.repo.save(spitter)) 
		{
			saveFile(spitter.getProfilePicture(), spitter.getUsername());
			model.addFlashAttribute(spitter);
			
			return "redirect:/spitter/" +spitter.getUsername();
		}
		else {
			return "registerForm";
		}
	}
	
	@RequestMapping(value="/{userName}", method=GET)
	public String showUser(
			@PathVariable String userName, Model model)
	{
		if (!model.containsAttribute("spitter")) {
			Spitter spitter = this.repo.findByUsername(userName);
			if (spitter == null) {
				throw new SpitterNotFoundException();
			}
			model.addAttribute(spitter);
		}
		return "profile/profile";
	}
	
	private void saveFile(
			MultipartFile file, 
			String fileName) throws IOException
	{
		file.transferTo(new File(fileName +".jpg"));
	}
}
