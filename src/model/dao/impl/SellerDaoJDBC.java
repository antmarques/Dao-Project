package model.dao.impl;

import db.DB;
import db.DbExeption;
import db.DbIntegrityException;
import model.dao.SellerDao;
import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(SellerEntity seller) {
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(
                    "INSERT INTO seller "
                    + "(name, email, birthdate, basesalary, id_department) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());

            var rownsAffected = ps.executeUpdate();

            if (rownsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                    conn.commit();
                }
                DB.closeResultSet(rs);
            } else {
                conn.rollback();
                throw new DbExeption("Unexpected error! No rowns affected!");
            }
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(SellerEntity seller) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "UPDATE seller "
                    + "SET name = ?, email = ?, birthdate = ?, basesalary = ?, id_department = ? "
                    + "WHERE id = ?;", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            ps.setInt(6, seller.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(
              "DELETE FROM seller "
              + "WHERE id = ?;"
            );
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }

            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            throw new DbIntegrityException("SQL Integrity Error: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
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
                DepartmentEntity dep = instantiateDepartment(rs);
                return instantiateSeller(rs, dep);
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.id_department = department.id "
                            + "ORDER BY name;"
            );
            rs = ps.executeQuery();

            List<SellerEntity> list = new ArrayList<>();
            while (rs.next()) {
                DepartmentEntity dep = instantiateDepartment(rs);
                SellerEntity seller = instantiateSeller(rs, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<SellerEntity> findByDepartment(DepartmentEntity department) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.id_department = department.id "
                            + "WHERE id_department = ? ORDER BY name;"
            );
            ps.setInt(1, department.getId());
            rs = ps.executeQuery();

            /*Quero utilizar o mesmo obj department, por isso instanciei um map e busco no banco o department e salvo no map,
            se não existir um obj no map eu instancio um department e reutilizo ele se caso o resultado da query foi mais do que 1 coluna.*/
            List<SellerEntity> list = new ArrayList<>();
            Map<Integer, DepartmentEntity> map = new HashMap<>();

            while (rs.next()) {
                DepartmentEntity dep = map.get(rs.getInt("id_department"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("id_department"), dep);
                }
                SellerEntity seller = instantiateSeller(rs, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbExeption("SQL Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private SellerEntity instantiateSeller(ResultSet rs, DepartmentEntity dep) throws SQLException{
        SellerEntity seller = new SellerEntity();
        seller.setId(rs.getInt("id"));
        seller.setName(rs.getString("name"));
        seller.setEmail(rs.getString("email"));
        seller.setBirthDate(rs.getDate("birthdate"));
        seller.setBaseSalary(rs.getDouble("basesalary"));
        seller.setDepartment(dep);

        return seller;
    }


    private DepartmentEntity instantiateDepartment(ResultSet rs) throws SQLException{
        DepartmentEntity dep = new DepartmentEntity();
        dep.setId(rs.getInt("id_department"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
