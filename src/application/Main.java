package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.SellerEntity;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("pt" , "BR"));
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");

        SellerEntity seller = sellerDao.findById(2);

        System.out.println(seller);
    }
}