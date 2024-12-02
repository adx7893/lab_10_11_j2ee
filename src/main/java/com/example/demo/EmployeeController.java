package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();
    private int currentId = 1;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("employees", employees);
        return "home";
    }

    @GetMapping("/addEmployee")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        employee.setId(currentId++);
        employees.add(employee);
        return "redirect:/";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employees.removeIf(employee -> employee.getId() == id);
        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployeeForm(@PathVariable int id, Model model) {
        Employee employeeToUpdate = employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("employee", employeeToUpdate);
        return "update-employee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute Employee employee) {
        employees.stream()
                .filter(e -> e.getId() == employee.getId())
                .findFirst()
                .ifPresent(e -> {
                    e.setName(employee.getName());
                    e.setDepartment(employee.getDepartment());
                    e.setEmail(employee.getEmail());
                    e.setSalary(employee.getSalary());
                });
        return "redirect:/";
    }
}