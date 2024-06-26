package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        //Test Crud SellerDao!

        Locale.setDefault(Locale.of("pt" , "BR"));
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        SellerEntity seller = sellerDao.findById(2);
        System.out.println(seller);

        System.out.println("=== TEST 2: seller findByDepartment ===");
        DepartmentEntity de = new DepartmentEntity(1, null);
        List<SellerEntity> list = sellerDao.findByDepartment(de);
        list.forEach(System.out::println);

        System.out.println("=== TEST 3: seller findAll ===");
        list = sellerDao.findAll();
        list.forEach(System.out::println);

        System.out.println("=== TEST 4: seller Insert ===");
        SellerEntity newSeller = new SellerEntity(null, "Greg", "greg@gmail.com", new Date(), 4000.0, de);
        sellerDao.insert(newSeller);

        System.out.println("=== TEST 5: seller Update ===");
        seller = sellerDao.findById(1);
        seller.setName("Marta Wayne");
        seller.setEmail("marta@gamil.com");
        sellerDao.update(seller);
    }
}