package com.example.restservice;

import java.util.Map;

public interface StudentDao {
Object dbInsert(Map<String, Object> data);
String dbUpdate(Map<String, Object> data,Map<String, Object> condition);
String dbDelete(Map<String, Object> condition);
Object dbSelect(Map<String, Object> condition);

}
