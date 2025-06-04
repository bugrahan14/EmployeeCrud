package com.bugrahan.springboot.cruddemo.rest;

import com.bugrahan.springboot.cruddemo.dao.EmployeeDAO;
import com.bugrahan.springboot.cruddemo.entity.Employee;
import com.bugrahan.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    //  ADD MAPPİNG FOT GET /employees({employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){

        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee==null){
            throw new RuntimeException("YANLIŞ İD -- EMPLOYEE İD NOT FOUND " + employeeId);

        }
        return theEmployee;
    }


     // ADD MAPPİNG FOR "POST" /employess - add new employee
    // @PostMapping
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){

        // ALSO JUST İN CASE THEY PASS AN İD İN JSON .... SET İD TO 0

        // THİS İS TO FORCE A SAVE OF NEW İTEM ..... İNSTEAD FOR UPDATE

        theEmployee.setId(0);

        Employee dbEmployee = employeeService.save(theEmployee);
        return  dbEmployee;

        //return employeeService.save(theEmployee);
    }


    // ADD MAPPİNG FOR PUT /employees - UPDATE EXİSTİNG EMPLOYEE
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {

        // Check if there is an employee with this ID in the database
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null) {
            throw new RuntimeException("Employee ID bulunamadı: " + employeeId);
        }

        // delete
        employeeService.deleteById(employeeId);

        return "Silinen çalışanın ID'si(ID of deleted employee): " + employeeId;
    }







    }

































