package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Objects;

public class SellerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String email;

    private Date birthDate;

    private Double baseSalary;

    private DepartmentEntity department;

    public SellerEntity (){}

    public SellerEntity(Integer id, String name, String email, Date birthDate, Double baseSalary, DepartmentEntity department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerEntity that = (SellerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(department.getId(), that.department.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, department.getId());
    }

    @Override
    public String toString() {
        return "Seller data:\n" + id + " - " + name + " - " + email + "\n" +
                DateFormat.getDateInstance(DateFormat.SHORT).format(birthDate) + " - " + NumberFormat.getCurrencyInstance().format(baseSalary)  + "\n"
                + "Department: " + department;
    }
}
