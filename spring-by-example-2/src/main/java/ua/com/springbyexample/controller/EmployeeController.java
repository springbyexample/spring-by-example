package ua.com.springbyexample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.service.PersistenceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee/")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Resource
	private PersistenceService<Employee, Long> employeeService;

	public void setEmployeeService(PersistenceService<Employee, Long> employeeService) {
		this.employeeService = employeeService;
	}

	/*
	 * Commented for JMS example Works ONLY with spring-by-example-3 where
	 * message broker is started in embedded mode
	 */
	// @Resource
	// private ActivityLogger logger;

	@RequestMapping(method = RequestMethod.GET, value = { "list", "/" })
	public String listEmployees(Model model) {
		logger.debug("Received request to list persons");

        List<Employee> employeeList = employeeService.find();

        logger.debug("Person Listing count = " + employeeList.size());

        model.addAttribute("employeeList", employeeList);
		return "listEmployees";

	}

	@RequestMapping(method = RequestMethod.GET, value = "edit")
	public String editEmployeeParam(@RequestParam(value = "id", required = false) Long id, Model model) {
		return editEmployeeInternal(id, model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "edit/{id}")
	public String editEmployeePath(@PathVariable Long id, Model model) {
		return editEmployeeInternal(id, model);

	}

	private String editEmployeeInternal(Long id, Model model) {
		logger.debug("Received request to edit person id : " + id);
		Employee employee;
		if (id == null) {
			employee = new Employee();
		} else {
			employee = employeeService.find(id);
			if (employee == null) {
				return "redirect:/employee/edit";
			}
		}
		model.addAttribute("employee", employee);
		return "editEmployee";
	}

	@RequestMapping(method = RequestMethod.POST, value = { "edit", "edit/{id}" })
	public String savePersonParam(@Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
		logger.debug("Received postback on person " + employee);
		if (bindingResult.hasErrors()) {
			return "editEmployee";
		} else {
			if (employee.getId() != null) {
				employeeService.update(employee);
			} else {
				employeeService.save(employee);
			}
			return "redirect:list";
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "delete")
	public String deleteEmployeeParam(@RequestParam(value = "id", required = false) Long id) {
		deleteEmployeeInternal(id);
		return "redirect:list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "delete/{id}")
	public String deleteEmployeePath(@PathVariable Long id) {
		deleteEmployeeInternal(id);
		return "redirect:/employee/list";

	}

	private void deleteEmployeeInternal(Long id) {
		logger.debug("Received request to delete person id : " + id);
		Employee employee = employeeService.find(id);
		if (employee != null) {
			employeeService.delete(employee);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "rest/view/{id}.json",
            produces = "application/json")
	@ResponseBody
	public Employee viewJson(@PathVariable Long id) {
		logger.debug("Received request to view JSON");
		Employee employee = employeeService.find(id);
		return employee;
	}

	@RequestMapping(method = RequestMethod.GET, value = "rest/view/{id}.xml",
            produces = "application/xml")
	@ResponseBody
	public Employee viewXml(@PathVariable Long id) {
		logger.debug("Received request to view XML");
		Employee employee = employeeService.find(id);
		return employee;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public String errorHandle() {
		logger.error("Someone tried to remove me or Eugene");
		return "dataIntegrity";
	}

}
