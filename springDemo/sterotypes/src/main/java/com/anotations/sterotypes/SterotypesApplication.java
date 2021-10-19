package com.anotations.sterotypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SterotypesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SterotypesApplication.class, args);
	}

}



@RestController("controller")
@RequestMapping("/db")
class Controller{
	@Autowired
	Service servic;
	@RequestMapping(value = "/{rno}",method=RequestMethod.GET)
    String postbyrno( @PathVariable(value="rno")String rollNo) {
		System.out.println(rollNo);
		
		return servic.returnString(rollNo);
		
	}
}

@Component("service")
class Service{
	String returnString(String roll) {
		System.out.println("service");
		String res=roll+" got the ideology";
		return res ;
		
	}
}