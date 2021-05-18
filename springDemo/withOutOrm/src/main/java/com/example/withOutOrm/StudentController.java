package com.example.withOutOrm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController("/")
public class StudentController {
	@Autowired
	StudentService ss;
	@GetMapping("")
	List <Map<String,Object>> selectData(){
		return ss.dbSelect();
	}
	@GetMapping("{roll_no}")
	List <Map<String,Object>> selectData(@PathVariable String roll_no){
		return ss.dbSelectById(roll_no);
	}
	@PostMapping("")
	String insertData(@RequestBody Map<String,Map <String,Object>> rawData){
		return ss.dbInsert(rawData.get("data"));
	}
	@PutMapping("{roll_no}")
	String updateById(@PathVariable String roll_no, @RequestBody Map<String,Map <String,Object>> rawData) {
		return ss.dbUpdateById(rawData.get("data"), roll_no);
		
	}
	@DeleteMapping("{roll_no)")
	String deleteById(@PathVariable String roll_no) {
		return ss.dbDeleteById(roll_no);
	}
	


}
