package ch.bbw.owasp.controller;

import ch.bbw.owasp.model.Employee;
import ch.bbw.owasp.repository.EmployeeRepository;
import ch.bbw.owasp.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public String employees(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails user, Model model) {
        boolean admin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (admin) {
            model.addAttribute("employees", employeeRepository.findAll());
        } else {
            model.addAttribute("employees", List.of(employeeRepository.findById(user.getEmployeeId()).orElseThrow()));
        }

        return "employees";
    }

    @GetMapping("/employees/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.employeeId")
    public String employeeDetail(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails user, Model model) {
        logger.info("EMPLOYEE_ACCESS user={} employeeId={}", user.getUsername(), id);
        Employee employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "employee-detail";
    }

    @PostMapping("/admin/employees/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployee(@PathVariable Long id) {
        logger.warn("ADMIN_DELETE_EMPLOYEE employeeId={}", id);
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }
}
