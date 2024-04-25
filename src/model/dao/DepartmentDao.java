package model.dao;

import model.entities.DepartmentEntity;

import java.util.List;

public interface DepartmentDao {

    void insert(DepartmentEntity seller);

    void update(DepartmentEntity seller);

    void deleteById(Integer id);

    DepartmentEntity findById(Integer id);

    List<DepartmentEntity> findAll();

}
