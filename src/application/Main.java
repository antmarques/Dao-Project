package application;

import db.DB;
import model.entities.DepartmentEntity;
import model.entities.SellerEntity;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        DepartmentEntity de = new DepartmentEntity(1, "Hardware");
        SellerEntity se = new SellerEntity(1, "Antonio", "antonio@email.com", new Date(), 3000.0, de);
        DepartmentEntity de2 = new DepartmentEntity(1, "Hardware");
        SellerEntity se2 = new SellerEntity(1, "Antonio", "antonio@email.com", new Date(), 3000.0, de2);


        System.out.println(se.equals(se2));
        System.out.println(se.hashCode());
        System.out.println(se2.hashCode());

    }
}