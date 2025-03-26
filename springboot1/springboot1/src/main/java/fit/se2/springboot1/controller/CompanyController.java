package fit.se2.springboot1.controller;

import fit.se2.springboot1.model.Company;
import fit.se2.springboot1.model.Employee;
import fit.se2.springboot1.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;
    @RequestMapping(value = "/list")
    public String getAllEmployees(Model model) {
        List<Company> company = companyRepository.findAll();
        model.addAttribute("companies", company);
        return "company/index";
    }
    @RequestMapping(value = "/{id}")
    public String getCompanyByID(@PathVariable(value = "id") long id, Model model) {
        Company company = companyRepository.getById(id);
        model.addAttribute("employees", company.getEmployees());
        model.addAttribute("company", company);
        return "company/detail";
    }
    @RequestMapping(value = "/detail/{id}")
        public String getCompanyDetail(@PathVariable long id, Model model) {
        Company company = companyRepository.getById((long)id);
        model.addAttribute("company", company);
        return "company/detail";
    }
    @GetMapping(value = "/update/{id}")
    public String updateCompany(@PathVariable(value = "id") long id, Model model) {
        Company company = companyRepository.getById((long)id);
        model.addAttribute("company", company);
        return "company/update";
    }
    @PostMapping(value = "/save")
    public String saveCompany(Company company) {
        companyRepository.save(company);
        return "redirect:/company/" + company.getId();
    }
    @GetMapping(value = "/add")
    public String addCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "company/add";
    }
    @RequestMapping(value = "/insert")
    public String insertCompany(Company company) {
        companyRepository.save(company);
        return "redirect:/company/" + company.getId();
    }
    @GetMapping(value = "/delete/{id}")
    public String deleteCompany(@PathVariable(value = "id") long id, Model model) {
        if(companyRepository.existsById(id)) {
            Company company = companyRepository.findById((long) id).get();
            companyRepository.delete(company);
        }
        return "redirect:/company/";
    }
}
