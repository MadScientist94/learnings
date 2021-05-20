package com.oracle.jpa;

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
	//select
	@GetMapping("")
	List <Map<String,Object>> selectData(){
		return ss.dbSelect();
	}
	//select by rol no
	@GetMapping("{roll_no}")
	List <Map<String,Object>> selectData(@PathVariable Integer roll_no){
		return ss.dbSelectById(roll_no);
	}
	//select by where condition
	@PostMapping("where")
	List <Map<String,Object>>selectDataByWhere(@RequestBody Map<String,Map <String,Object>> rawData){
		return ss.dbSelectWithWhere(rawData.get("condition"));
	}
	//insert data
	@PostMapping("")
	String insertData(@RequestBody Map<String,Map <String,Object>> rawData){
		return ss.dbInsert(rawData.get("data"));
	}
	//update data by roll no
	@PutMapping("{roll_no}")
	String updateById(@PathVariable Integer roll_no, @RequestBody Map<String,Map <String,Object>> rawData) {
		return ss.dbUpdateById(rawData.get("data"), roll_no);
		
	}
	//update data by where condition
	@PutMapping("where")
	String updateByWhere( @RequestBody Map<String,Map <String,Object>> rawData) {
		return ss.dbUpdateWithWhere(rawData.get("data"), rawData.get("condition"));
		
	}
	//delete by roll no
	@DeleteMapping("{roll_no}")
	String deleteById(@PathVariable Integer roll_no) {
		return ss.dbDeleteById(roll_no);
	}
	@DeleteMapping("where")
	String deleteByWhere(@RequestBody Map<String,Map <String,Object>> rawData) {
		return ss.dbDeleteWithWhere(rawData.get("condition"));
	}
	


}
