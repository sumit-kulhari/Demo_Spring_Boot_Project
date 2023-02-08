package com.markets.service;

import com.markets.dao.EmployeeDao;
import com.markets.entity.EmployeeEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDao employeeDao;

    @Autowired
    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public void saveEmployee(EmployeeEntity employee) {
        boolean exists = employeeDao.existsById(employee.getEid());
        if(!exists){
            employeeDao.save(employee);
        }
        else {
            System.out.println("Record already present");
        }
    }

    @Override
    public void deleteEmploye(Integer eid) {
        boolean exists = employeeDao.existsById(eid);
        if(!exists){
            throw new IllegalStateException("employee with Id "+eid+" does not exists");
        }
        employeeDao.deleteById(eid);
        System.out.println("Deleted Successfully");
    }

    @Override
    public EmployeeEntity searchEmployee(Integer eid) {
        Optional<EmployeeEntity> employeeEntityOptional = employeeDao.findById(eid);
        if(employeeEntityOptional.isPresent()){
            return employeeEntityOptional.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Record Not Found");
        }
    }

    @Transactional
    @Override
    public EmployeeEntity updateEmployee(EmployeeEntity employee) {
        EmployeeEntity employee1 = employeeDao.findById(
                employee.getEid()).orElseThrow(()->
                new IllegalStateException("Employee with ID "+employee.getEid()+" Does not exist"));
        employee1.setFname(employee.getFname());
        employee1.setSalary(employee.getSalary());
        return employee1;
    }


}
