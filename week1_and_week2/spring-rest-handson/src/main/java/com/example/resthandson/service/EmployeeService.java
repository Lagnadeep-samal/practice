package com.example.resthandson.service;

import com.example.resthandson.exception.EmployeeNotFoundException;
import com.example.resthandson.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final Map<Long, Employee> employeeStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Employee> getAllEmployees() {
        return employeeStore.values().stream().collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = employeeStore.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }
        return employee;
    }

    public Employee createEmployee(Employee employee) {
        long newId = idCounter.incrementAndGet();
        employee.setId(newId);
        employeeStore.put(newId, employee);
        return employee;
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeStore.get(id);
        if (existing == null) {
            throw new EmployeeNotFoundException(id);
        }

        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());

        return existing;
    }

    public void deleteEmployee(Long id) {
        Employee existing = employeeStore.get(id);
        if (existing == null) {
            throw new EmployeeNotFoundException(id);
        }
        employeeStore.remove(id);
    }
}