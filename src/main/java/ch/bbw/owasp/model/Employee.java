package ch.bbw.owasp.model;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String department;
    private String email;

    public Employee() {
    }

    public Employee(String firstname, String lastname, String department, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }
}
