package com.markets.controller;

import com.markets.entity.EmployeeEntity;
import com.markets.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/getall")
    public List<EmployeeEntity> displayall(){
        return employeeService.getAllEmployees();
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody EmployeeEntity employee) {
        // save employee added
        EmployeeEntity employee1 = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(employee1, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{eid}")
    public ResponseEntity<EmployeeEntity> deleteEmployee(@PathVariable("eid") Integer eid){
        EmployeeEntity employee = employeeService.deleteEmployee(eid);
        return new ResponseEntity<>(employee, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping( "/getbyID/{eid}")
    public ResponseEntity<EmployeeEntity> searchEmployee(@PathVariable("eid") Integer eid){
        EmployeeEntity employee = employeeService.searchEmployee(eid);
        return new ResponseEntity<>(employee, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<EmployeeEntity> updateEmployee(@RequestBody EmployeeEntity employee){
        EmployeeEntity employee1 = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(employee1, new HttpHeaders(), HttpStatus.OK);
    }
}
