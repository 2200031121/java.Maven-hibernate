package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\n1. Insert Department\n2. Delete Department\n3. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                if (choice == 1) {
                    // Insert operation
                    transaction = session.beginTransaction();
                    Department department = new Department();
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter Department Name: ");
                    department.setName(sc.nextLine());
                    System.out.print("Enter Location: ");
                    department.setLocation(sc.nextLine());
                    System.out.print("Enter HoD Name: ");
                    department.setHodName(sc.nextLine());
                    session.save(department);
                    transaction.commit();
                    System.out.println("Department inserted successfully.");
                } else if (choice == 2) {
                    // Delete operation
                    System.out.print("Enter Department ID to delete: ");
                    int departmentId = sc.nextInt();
                    transaction = session.beginTransaction();
                    String hql = "DELETE FROM Department WHERE departmentId = ?1";
                    session.createQuery(hql)
                           .setParameter(1, departmentId)
                           .executeUpdate();
                    transaction.commit();
                    System.out.println("Department deleted successfully.");
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
            sc.close();
        }
    }
}
