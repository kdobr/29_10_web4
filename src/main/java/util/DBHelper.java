package util;

import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import service.CarService;

import java.sql.Connection;
import java.sql.SQLException;


public class DBHelper {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }



    private static SessionFactory createSessionFactory() {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(DailyReport.class)
                .buildSessionFactory();
       return factory;
    }

}
