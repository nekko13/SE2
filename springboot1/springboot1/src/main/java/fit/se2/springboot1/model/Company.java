package fit.se2.springboot1.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private String name;
    private String image;
    private String address;
    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Employee> getEmployees() {
        return (Set<Employee>) employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
