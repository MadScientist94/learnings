package com.jo.exportxls;
mport java.io.IOException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class UserController {
 
   
     
    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<User> l= new ArrayList<User>();
         for (int i=1;i<5;i++) {
        	 User e = new User();
        	 e.setEmail("e"+i);
        	 e.setEnabled(true);
        	 e.setFullName("name"+i);
        	 e.setId(i);
        	 e.setRoles("role"+i);
        	 e.setPassword("pass"+i);
        	 
        	 l.add(e);
         }
       
         
        UserExcelExporter excelExporter = new UserExcelExporter(l);
         
        excelExporter.export(response);    
    }  
 
}
