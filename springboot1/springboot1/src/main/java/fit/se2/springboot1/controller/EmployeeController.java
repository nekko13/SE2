package fit.se2.springboot1.controller;

import fit.se2.springboot1.model.Company;
import fit.se2.springboot1.model.Employee;
import fit.se2.springboot1.repository.CompanyRepository;
import fit.se2.springboot1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    @RequestMapping(value = "/list")
    public String getAllEmployee(@RequestParam(value = "company", required = false, defaultValue = "0") Long comId,
                                 @RequestParam(value = "sort", required = false, defaultValue = "0") int sortMode,
                                 Model model) {
        List<Employee> employees;
        List<Company> companies = companyRepository.findAll();

        Sort sort = Sort.unsorted(); // Default sorting (no sorting)

        // Determine sorting order
        if (sortMode == 0) {
            sort = Sort.by(Sort.Direction.ASC, "id"); // Default sort by ID
        } else if (sortMode == 1) {
            sort = Sort.by(Sort.Direction.DESC, "id");
        } else if (sortMode == 2) {
            sort = Sort.by(Sort.Direction.ASC, "name");
        } else if (sortMode == 3) {
            sort = Sort.by(Sort.Direction.DESC, "name");
        }

        // Filter by company if specified
        if (comId != 0) {
            Optional<Company> comp = companyRepository.findById(comId);
            if (comp.isPresent()) {
                employees = employeeRepository.findByCompany(comp.get(), sort);
            } else {
                employees = employeeRepository.findAll(sort);
            }
        } else {
            employees = employeeRepository.findAll(sort);
        }

        // Add attributes to the model for Thymeleaf
        model.addAttribute("employees", employees);
        model.addAttribute("companies", companies);
        model.addAttribute("comId", comId);
        model.addAttribute("sortMode", sortMode);

        return "employee/index";
    }
    @RequestMapping(value = "/detail/{id}")
    public String getEmployeeDetail(@PathVariable int id, Model model) {
        Employee employee = employeeRepository.getById((long) id);
        model.addAttribute("employee", employee);
        return "employee/detail";
    }
    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "employee/update";
    }
    @PostMapping(value = "/save")
    public String saveUpdate(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/";
    }
    @GetMapping(value = "/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        model.addAttribute("employee",employee);
        return "employee/add";
    }
    @RequestMapping(value = "/insert")
    public String insertEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/detail/" + employee.getId();
    }
    @GetMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id, Model model) {
        if (employeeRepository.existsById(id)) {
            Employee employee = employeeRepository.findById((long) id).get();
            employeeRepository.delete(employee);
        }
       return "redirect:/";
    }
}
