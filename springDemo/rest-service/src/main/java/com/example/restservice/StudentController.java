package com.example.restservice;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/student")
public class StudentController {
	StudentDetailsImplementation obj=null;
	{
		if (obj==null)
			{obj= new StudentDetailsImplementation();
		System.out.println("obj created");
			}
	}
	//create student
	@RequestMapping(method = RequestMethod.GET)
    String get(@RequestParam(value = "rollNo") String rollNo,@RequestParam(value = "name") String name,@RequestParam(value = "department") String department,@RequestParam(value = "email") String email,
    		@RequestParam(value = "address") String address,@RequestParam(value = "phone") String phone,@RequestParam(value = "mark") String stringMark) {
			String [] mark=stringMark.split(",");
			Map<String,Integer> markMap=new TreeMap();
			for(String m:mark)
				markMap.put(m.split("=")[0], Integer.parseInt(m.split("=")[1]));
			return obj.save(new Student(rollNo,name,department,email,address,phone,markMap));
    	}
	//read student
	@RequestMapping(value = "/{rno}",method=RequestMethod.GET)
    Object postbyrno( @PathVariable(value="rno")String rollNo) {
    	Student s=obj.read(rollNo);
    	if (s!=null)
    		return s;
    	return "student with roll no "+rollNo+" is not available";
    }
//update student
	@RequestMapping(value = "/{rno}",method=RequestMethod.PUT)
    String updatebyrno( @PathVariable("rno")String rollNo, @RequestParam Map<String,String> updateData) {
    	String s=obj.update(rollNo,updateData);
    	if (s!=null)
    		return s;
    	return "student with roll no "+rollNo+" is not available";
    }
	//delete student
    @RequestMapping(value = "/{rno}",method=RequestMethod.DELETE)
    String deletebyrno( @PathVariable("rno")String rollNo) {
		return obj.delete(rollNo);
    }
    //search student
    @RequestMapping(value = "/search",method=RequestMethod.GET)
    Object search( @RequestParam Map<String,String> searchVariable) {
    	List<Student> res = obj.search(searchVariable);
    	if (!res.isEmpty())
    		return res;
		return "there is no specific data regarding to your search";
    }
    @RequestMapping(value = "/reg",method=RequestMethod.POST, produces="application/json")
    Object postCheck( @RequestBody Map searchVariable) {
    	
    	final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
    	final Student pojo = mapper.convertValue(searchVariable, Student.class);
    	System.out.println(pojo);
		obj.save(pojo);
    	return obj.studentsData;
    }
    
}
    
    @RestController
    @RequestMapping("/db")
    class db{
    	@Autowired
    	StudentDaoImp student;
    	//insert
    	@RequestMapping(value = "/reg",method=RequestMethod.POST)
    	Object insert(@RequestBody Map insertData) {
    		return student.dbInsert(insertData);    		
    	}
    	//update
    	@RequestMapping(value = "/update",method=RequestMethod.PUT)
    	Object update (@RequestBody Map<String,Map<String,Object>> insertData) {
  		return student.dbUpdate(insertData.get("data"),insertData.get("condition"));    		
    	}
    	//delete
    	@RequestMapping(value = "/delete",method=RequestMethod.DELETE)
    	Object delete (@RequestBody Map<String,Map<String,Object>> insertData) {
 		return student.dbDelete(insertData.get("condition"));    		
    	}
    	//select
    	@RequestMapping(value = "/select",method=RequestMethod.POST)
    	Object select (@RequestBody Map<String,Map<String,Object>> insertData) {
 		return student.dbSelect(insertData.get("condition"));    		
    	}
    }

    //	@RequestMapping(method = RequestMethod.DELETE)
//    Map delete(@RequestParam Map<String,String> allRequestParams) {
//        return allRequestParams; 
//    }
//    @RequestMapping(method = RequestMethod.POST)
//    Object post(    		@RequestParam(value = "rollNo") String rollNo
//) {Student s=obj.read(rollNo);
//    	if (s!=null)
//        return s;
//    	return "student with roll no "+rollNo+" is not available";
//    }
//    @RequestMapping(method = RequestMethod.PUT)
//    String put() {
//        return "Hello from put";
//    }
//    @RequestMapping(method = RequestMethod.PATCH)
//    String patch() {
//        return "Hello from patch";
//    }

        
    


//@RestController
//@RequestMapping("/student/data")
//class StudentControllerForManipulation{
//
//    @RequestMapping(method = RequestMethod.POST)
//    String post(@RequestParam(value = "rollNo") String rollNo) {
//        return "Hello from post";
//    }
//
	
//}
//
//@GetMapping("/person")
//public @ResponseBody ResponseEntity < String > getPerson() {
//    return new ResponseEntity < String > ("Response from GET", HttpStatus.OK);
//}
//@GetMapping("/person/{id}")
//public @ResponseBody ResponseEntity < String > getPersonById(@PathVariable String id) {
//    return new ResponseEntity < String > ("Response from GET with id " + id, HttpStatus.OK);
//}
//@PostMapping("/person")
//public @ResponseBody ResponseEntity < String > postPerson() {
//    return new ResponseEntity < String > ("Response from POST method", HttpStatus.OK);
//}
//@PutMapping("/person")
//public @ResponseBody ResponseEntity < String > putPerson() {
//    return new ResponseEntity < String > ("Response from PUT method", HttpStatus.OK);
//}
//@DeleteMapping("/person")
//public @ResponseBody ResponseEntity < String > deletePerson() {
//    return new ResponseEntity < String > ("Response from DELETE method", HttpStatus.OK);
//}
//@PatchMapping("/person")
//public @ResponseBody ResponseEntity < String > patchPerson() {
//    return new ResponseEntity < String > ("Response from PATCH method", HttpStatus.OK);
//}