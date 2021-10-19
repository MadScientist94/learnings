package com.example.SubmissionForms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//public interface CustomerRepo extends CrudRepository<Customers,Integer> {
public interface CustomerRepo extends JpaRepository<Customers,Integer> {

}
