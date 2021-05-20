package com.mssql.jpawithmssql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Cont {
	
	@Autowired
	repo r;
@GetMapping("/")
Object work() {
//	r.save(new tbl_department(4,"fARM","JOTHI"));
//	r.deleteById(5);
//	r.findAll();
	return r.findAll();
	
}
}
