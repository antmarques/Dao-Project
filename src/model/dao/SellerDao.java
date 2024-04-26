package model.dao;

import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.util.List;

public interface SellerDao {

    void insert(SellerEntity seller);

    void update(SellerEntity seller);

    void deleteById(Integer id);

    SellerEntity findById(Integer id);

    List<SellerEntity> findAll();

    List<SellerEntity> findByDepartment(DepartmentEntity department);

}
