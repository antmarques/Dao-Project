package application;


import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.util.Date;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("pt" , "BR"));
        SellerEntity se = new SellerEntity(1, "name", "email", new Date(), 3000.0, new DepartmentEntity());
        System.out.println(se);
    }
}