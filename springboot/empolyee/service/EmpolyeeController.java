package com.springboot.empolyee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.empolyee.exception.ResourceNotFoundException;
import com.springboot.empolyee.model.Employee;
import com.springboot.empolyee.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmpolyeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	} 
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id) {
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not Exist with the id : " + id));
		
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException
						("Employee not Exist with the id : " + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee= employeeRepository.save(employee);
		
		return ResponseEntity.ok(updateEmployee);
	}
	
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException
						("Employee not Exist with the id : " + id));
		
		employeeRepository.delete(employee);
		
		Map<String,Boolean> response=new HashMap<>();
			
		response.put("Deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
		
	}
	
	
}
