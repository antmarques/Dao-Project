package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.DepartmentEntity;

import java.util.List;
import java.util.Locale;

public class Main2 {
    public static void main(String[] args) {
        //Test DepartmentDao!

        Locale.setDefault(Locale.of("pt" , "BR"));
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department findById ===");
        DepartmentEntity department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("=== TEST 2: department findAll ===");
        List<DepartmentEntity>list = departmentDao.findAll();
        list.forEach(System.out::println);

        System.out.println("=== TEST 4: department Insert ===");
        DepartmentEntity newDepartment = new DepartmentEntity(null, "Music");
        departmentDao.insert(newDepartment);

        System.out.println("=== TEST 5: department Update ===");
        department = departmentDao.findById(1);
        department.setName("Marta Wayne");
        departmentDao.update(department);
    }
}
