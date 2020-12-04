package com.example.mongodb.controller;

import com.example.mongodb.model.Employee;
import com.example.mongodb.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {
    @Autowired
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeRepository employeeRepository;

    private static final int empid = 1;

    @Spy
    List<Employee> employees = new ArrayList<>();

    @Mock
    BindingResult result;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employees = getEmployeeDetails();
    }

    private List<Employee> getEmployeeDetails() {
        Employee emp1 = new Employee(1, "Aadhi", "aadhi@gmail.com", 200);
        Employee emp2 = new Employee(2, "Akil", "akil@gmail.com", 300);
        Employee emp3 = new Employee(3, "Krithick", "krithick@gmail.com", 500);
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        return employees;
    }

    @Test
    public void testGetAllEmployee() throws Exception {

        when(employeeRepository.findAll()).thenReturn(employees);
        ResponseEntity<List<Employee>> result = employeeController.getAllEmployees();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Aadhi", result.getBody().get(0).getName());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = new Employee(2, "Aadhi", "aadhi@gmail.com", 5000);
        when(employeeRepository.findById(empid)).thenReturn(Optional.of(employee));
        ResponseEntity<Employee> result = employeeController.getEmployeeById(empid);
        assertEquals("Aadhi", result.getBody().getName());
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee("Aadhi", "aadhi@gmail.com", 1000);
        when(employeeRepository.save(Mockito.any())).thenReturn(employee);
        ResponseEntity<Employee> result = employeeController.createEmployee(employee);
        assertEquals("Aadhi", result.getBody().getName());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee(2, "Aadhi", "aadhi@gmail.com", 1000);
        when(employeeRepository.findById(empid)).thenReturn(Optional.of(employee));
        Employee updateEmployee = new Employee("Akil", "aadhi@gmail.com", 500);
        when(employeeRepository.save((Mockito.any()))).thenReturn(updateEmployee);
        ResponseEntity<?> result = employeeController.updateEmployee(empid, updateEmployee);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Akil", ((Employee) result.getBody()).getName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee(2, "Aadhi", "aadhi@gmail.com", 1000);
        when(employeeRepository.findById(empid)).thenReturn(Optional.of(employee));
        ResponseEntity<?> result = employeeController.deleteEmployee(empid);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
