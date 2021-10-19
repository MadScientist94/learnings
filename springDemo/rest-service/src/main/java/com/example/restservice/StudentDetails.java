package com.example.restservice;

import java.util.List;
import java.util.Map;

public interface StudentDetails {
String save(Student s);
Student read(String rollNo);

String update(String rollNo, Map<String, String> data);
String delete(String rollNo);
List<Student> search(Map<String, String> searchVariable);
}
