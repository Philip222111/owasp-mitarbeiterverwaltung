package ch.bbw.owasp;

import ch.bbw.owasp.model.AppUser;
import ch.bbw.owasp.model.Employee;
import ch.bbw.owasp.repository.AppUserRepository;
import ch.bbw.owasp.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OwaspApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwaspApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(EmployeeRepository employeeRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Employee adminEmployee = employeeRepository.save(new Employee("Anna", "Admin", "IT Security", "anna.admin@example.com"));
            Employee userEmployee = employeeRepository.save(new Employee("Max", "User", "Entwicklung", "max.user@example.com"));
            employeeRepository.save(new Employee("Laura", "Meier", "HR", "laura.meier@example.com"));

            appUserRepository.save(new AppUser("admin", passwordEncoder.encode("Admin123"), "ROLE_ADMIN", adminEmployee.getId()));
            appUserRepository.save(new AppUser("user", passwordEncoder.encode("User1234"), "ROLE_USER", userEmployee.getId()));
        };
    }
}
