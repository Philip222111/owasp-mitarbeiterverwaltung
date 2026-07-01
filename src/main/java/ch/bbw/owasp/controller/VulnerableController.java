package ch.bbw.owasp.controller;

import ch.bbw.owasp.model.Employee;
import ch.bbw.owasp.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VulnerableController {
    private final EmployeeRepository employeeRepository;

    public VulnerableController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/vulnerable/employees/{id}")
    public String vulnerableEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "vulnerable";
    }
}
