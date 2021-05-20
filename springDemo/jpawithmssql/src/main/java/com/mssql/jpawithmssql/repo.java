package com.mssql.jpawithmssql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
@Controller
public interface repo extends CrudRepository<tbl_department, Integer> {

}
