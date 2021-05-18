package com.example.withOutOrm;

import java.util.List;
import java.util.Map;

public interface DaoInterface {
	String dbEdit(String sql);

	List <Map<String,Object>> dbSelect(String sql) ;
}
