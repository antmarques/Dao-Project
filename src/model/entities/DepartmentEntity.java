package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class DepartmentEntity implements Serializable { //Serializable é para se quisermos que esse objeto seja salvo em arquivos e outra estruturas.

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    public DepartmentEntity (){}

    public DepartmentEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
