package model.dao.impl;

import db.DB;
import db.DbExeption;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(DepartmentEntity department) {
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(
                    "INSERT INTO department (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, department.getName());

            var rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    department.setId(rs.getInt(1));
                    conn.commit();
                    System.out.println("Id created: " + department.getId());
                } else {
                    conn.rollback();
                    throw new DbExeption("Unexpected error! No rowns affected!");
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DbIntegrityException("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(DepartmentEntity department) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "UPDATE department SET name = ? WHERE id = ?;", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());
            ps.executeUpdate();
            System.out.println("Department updated");
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(
                    "DELETE FROM department WHERE id = ?;"
            );
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected <= 0) {
                conn.rollback();
                throw new DbExeption("Id is null");
            }
            conn.commit();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            throw new DbIntegrityException("SQL Integrity Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public DepartmentEntity findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT department.* FROM department WHERE department.id = ?"
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return instantiateDepartment(rs);
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
    public List<DepartmentEntity> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT department.* FROM department ORDER BY name;"
            );
            rs = ps.executeQuery();

            List<DepartmentEntity> list = new ArrayList<>();
            while (rs.next()) {
                DepartmentEntity dep = instantiateDepartment(rs);
                list.add(dep);
            }
            return list;
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private DepartmentEntity instantiateDepartment(ResultSet rs) throws SQLException {
        DepartmentEntity dep = new DepartmentEntity();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("name"));
        return dep;
    }
}
