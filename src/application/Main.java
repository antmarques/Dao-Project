package application;

import db.DB;
import model.entities.DepartmentEntity;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DepartmentEntity de = new DepartmentEntity(1, "Antonio");
        DepartmentEntity de2 = new DepartmentEntity(1, "Antonio");

        System.out.println(de);
        System.out.println(de2);
        System.out.println(de.equals(de2));
        System.out.println(de.hashCode() + " - " + de2.hashCode());
    }
}