package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.DepartmentEntity;

import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("pt" , "BR"));
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        List<DepartmentEntity> list = departmentDao.findAll();
        list.forEach(System.out::println);
    }
}