package application;


import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.SellerEntity;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("pt" , "BR"));
        SellerDao sellerDao = DaoFactory.createSellerDao();

        SellerEntity seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}