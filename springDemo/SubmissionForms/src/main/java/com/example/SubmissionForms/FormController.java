package com.example.SubmissionForms;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FormController {
	@Autowired
	CustomerRepo repo;
	
	@GetMapping("/")
	public String details() {
		return "edureka";
	}
	
	@RequestMapping("/details")
	public String details(Customers customers) {
		System.out.println(customers);
		repo.save(customers);
		return "edureka";
	}
	
	@RequestMapping("/getdetails")
	public String getDetails() {
		return "ViewCustomers";
	}

	@PostMapping("/getdetails")
	public ModelAndView viewDetails(@RequestParam(value="CustomerId") int cid) {
		ModelAndView mv = new ModelAndView("retrive");
		System.out.println(cid);
		Customers customers= repo.findById(cid).orElse(null);
		mv.addObject(customers);
		return mv;
		
	}
	@ResponseBody
	@RequestMapping("/customers")
	public List<Customers> getCustomers() {
		return repo.findAll();
	}
	@ResponseBody
	@RequestMapping("/customers/{cid}")
	public Optional<Customers> getCustomersById(@PathVariable("cid") int cid) {
		return repo.findById(cid);
	}
	
	/*
	@GetMapping("/edureka")
	public String edureka() {
		return "edureka";
	}
	@PostMapping("/details")
	public String viewDetails(@RequestParam("cid") String cid,
			@RequestParam("cname") String cname,
			@RequestParam("cemail") String cemail,
			ModelMap modelMap 
			) {
		
		modelMap.put("cid",cid);
		modelMap.put("cname",cname);
		modelMap.put("cemail",cemail);
		return "ViewCustomers";
		
	}
	*/

}
