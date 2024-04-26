package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("pt" , "BR"));
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        SellerEntity seller = sellerDao.findById(2);
        System.out.println(seller);

        System.out.println("=== TEST 2: seller findByDepartment ===");
        DepartmentEntity de = new DepartmentEntity(1, null);
        List<SellerEntity> list = sellerDao.findByDepartment(de);
        list.forEach(System.out::println);
    }
}