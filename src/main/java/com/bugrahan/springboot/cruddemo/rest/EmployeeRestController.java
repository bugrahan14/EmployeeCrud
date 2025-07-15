package com.bugrahan.springboot.cruddemo.rest;

import com.bugrahan.springboot.cruddemo.dao.EmployeeDAO;
import com.bugrahan.springboot.cruddemo.entity.Employee;
import com.bugrahan.springboot.cruddemo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper){
        this.employeeService=employeeService;
        this.objectMapper=objectMapper;
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

    // ADD MAPPİNG FOR PATCH /employee/{empolyeeid}  - PATCH EMPLOYEE ..... PARTİAL UPDATE

    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId ,
                                  @RequestBody Map<String , Object> patchPayload){

        Employee tempEmployee = employeeService.findById(employeeId);

        // THROW EXEPTİON İF NULL
        if (tempEmployee == null){
            throw new RuntimeException("EMPLOYEE İD NOT FOUN----->İD BULUNAMADI" + employeeId);
        }

        // THROW EXEPTİON İF REQUEST BODY CONTAİND "İD" KEY
        if ( patchPayload.containsKey("id")) {
            throw new RuntimeException("EMPLOYEE İD NOT ALOOWED İN REQUEST BODY - " + employeeId);
        }
        
        Employee patchEmployee = apply(patchPayload , tempEmployee);

        Employee dbEmployee = employeeService.save(patchEmployee);

        return dbEmployee;




    }

    private Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {

        // CONVERT EMPLOYEE OBJECT TO A JSON OBJECT NODE
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee , ObjectNode.class);

        // CONVERT THE patchPayload MAP TO A JSON OBJECT NODE
        ObjectNode patchNode = objectMapper.convertValue(patchPayload , ObjectNode.class);

        // MERGE THE PATCH UPDATES İNTO THE EMPLOYEE NODE
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }


}

































