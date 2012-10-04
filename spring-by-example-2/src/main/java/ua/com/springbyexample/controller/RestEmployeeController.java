package ua.com.springbyexample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.service.EmployeeService;
import ua.com.springbyexample.service.PersistenceService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orezchykov
 * Date: 04.10.12
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "android")
public class RestEmployeeController {

    @Resource
    private PersistenceService<Employee, Long> employeeService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Employee> findAll() {
        return employeeService.find();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    @ResponseBody
    public Employee find(@PathVariable Long id) {
        Employee employee = employeeService.find(id);
        if (employee != null) {
            return employee;
        } else {
           throw new IllegalArgumentException("Wring employee id");
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long create(@RequestBody Employee employee) {
        employeeService.save(employee);
        return employee.getId();

    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Employee employee) {
        employeeService.update(employee);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        employeeService.delete(employeeService.find(id));
    }

    //Bulk operations

    @RequestMapping(method = RequestMethod.POST, value = "/bulk")
    @ResponseStatus(HttpStatus.OK)
    public void sync(@RequestBody List<Employee> employeeList) {
         for (Employee employee : employeeList) {
             if (employee.getId() == null) {
                 employeeService.save(employee);
             } else {
                 employeeService.update(employee);
             }
         }

    }

    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void badRequest() {

    }

//    @ExceptionHandler()
//    @ResponseStatus(HttpStatus.NOT_FOUND)


}
