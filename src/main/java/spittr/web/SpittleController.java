package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spittr.Spittle;
import spittr.data.*;


import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Date;

import org.springframework.beans.factory.annotation.*;

@Controller
@RequestMapping("/spittles")
public class SpittleController 
{
	private SpittleRepository spittleRepository;
	
	@Autowired
	public SpittleController(SpittleRepository sr) {
		this.spittleRepository = sr;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String spittles(Model model)
	{
		model.addAttribute(this.spittleRepository.findSpittles(Long.MAX_VALUE, 20));
		return "spittles";
	}
	
	@RequestMapping(value="/show/{spittleId}", method=RequestMethod.GET)
	public String spittle(
			@PathVariable("spittleId") long spittleId, Model model)
	{
		model.addAttribute(this.spittleRepository.findOne(spittleId));
		return "spittle";
	}
}
