package com.markets.controller;

import com.markets.entity.EmployeeEntity;
import com.markets.service.EmployeeService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private EmployeeService employeeService;
//    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RateLimiter(name = "getMessageRateLimit", fallbackMethod = "getMessageFallBack")
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

    @RequestMapping("/service-instances/{MarketsApplication}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String MarketsApplication) {
        return this.discoveryClient.getInstances(MarketsApplication);
    }

    @ExceptionHandler({ RequestNotPermitted.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<String> getMessageFallBack(RequestNotPermitted exception) {

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests : No further request will be accepted. Please try after sometime");
    }
}
