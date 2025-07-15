package com.bugrahan.springboot.cruddemo.dao;

import com.bugrahan.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    // DEFİNE FİELD FOR ENTİTYMANAGER
    private EntityManager entityManager;


    // SET UP CONSTRUCTOR İNJECTİON
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {

        // CREATE A QUERY
        TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee",Employee.class);

        // EXECUTE QUEERY AND GETT RESULT LİST
        List<Employee> employees = theQuery.getResultList();

        // RETURN THE RESULTS     " return theQuery.getResultList(); "

        return employees;

    }



    @Override
    public Employee findById(int theId) {
        // GET EMPLOYEE
        Employee theEmployee = entityManager.find(Employee.class,theId);
        return theEmployee;
    }



    @Override
    public Employee save(Employee theEmployee) {

        // SAVE EMPLOYEE
        Employee dbEmployee = entityManager.merge(theEmployee);

        return dbEmployee;
    }



    @Override
    public void deleteById(int theId) {

        // FİND EMPLOYEE BY İD
        Employee theEmployee = entityManager.find(Employee.class,theId);

        entityManager.remove(theEmployee);

    }


}

































