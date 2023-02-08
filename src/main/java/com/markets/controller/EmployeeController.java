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
    public String saveEmployee(@RequestBody EmployeeEntity employee) {
        // save employee added
        employeeService.saveEmployee(employee);
        return "Employee added";
    }

//    @DeleteMapping("/delete")
//    public void deleteEmployee(@RequestBody EmployeeEntity employee){
//        employeeService.deleteEmploye(employee);
//    }
    @DeleteMapping("/delete/{eid}")
    public String deleteEmployee(@PathVariable("eid") Integer eid){
        employeeService.deleteEmploye(eid);
        return "Delete is working";
    }

    @GetMapping( "/getbyID/{eid}")
    public ResponseEntity<EmployeeEntity> searchEmployee(@PathVariable("eid") Integer eid){
        EmployeeEntity employee = employeeService.searchEmployee(eid);
        return new ResponseEntity<EmployeeEntity>(employee,new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/updateEmployee")
    public String updateEmployee(@RequestBody EmployeeEntity employee){
        employeeService.updateEmployee(employee);
        return "Object updated Successfully";
    }
}
