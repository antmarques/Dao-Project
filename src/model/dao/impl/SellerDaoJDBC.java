package model.dao.impl;

import db.DB;
import db.DbExeption;
import model.dao.SellerDao;
import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(SellerEntity seller) {

    }

    @Override
    public void update(SellerEntity seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public SellerEntity findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.id_department = department.id "
                    + "WHERE seller.id = ?"
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                DepartmentEntity dep = new DepartmentEntity();
                dep.setId(rs.getInt("id_department"));
                dep.setName(rs.getString("DepName"));
                SellerEntity seller = new SellerEntity();
                seller.setId(rs.getInt("id"));
                seller.setName(rs.getString("name"));
                seller.setEmail(rs.getString("email"));
                seller.setBirthDate(rs.getDate("birthdate"));
                seller.setBaseSalary(rs.getDouble("basesalary"));
                seller.setDepartment(dep);

                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<SellerEntity> findAll() {
        return null;
    }
}
