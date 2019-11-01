package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public long addCar(Car car) throws HibernateException {
        try {
            session.beginTransaction();
            Car carFound = findCar(car.getBrand(), car.getModel(), car.getLicensePlate());
            if (carFound != null) return 0;
            Long id = (Long) session.save(car);
            session.getTransaction().commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return 0;
        } finally {
            session.close();
        }
    }

    public List<Car> getCarsByBrand(String brand) {
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Car where brand = :paramName";
        Query query = session.createQuery(hql);
        query.setParameter("paramName", brand);
        List<Car> listCarByBrand = query.getResultList();
        transaction.commit();
        session.close();
        return listCarByBrand;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> allCars = session.createQuery("FROM Car").getResultList();
        transaction.commit();
        session.close();
        return allCars;
    }

    public Car findCar(String brand, String model, String plate) {
        String hql = "FROM Car where brand = :brand and model = :model and licensePlate =:plate";
        Query query = session.createQuery(hql);
        query.setParameter("brand", brand).setParameter("model", model).setParameter("plate", plate);
        Car carFound = (Car) (query.getResultList().size() > 0 ? query.getResultList().get(0) : null);
        return carFound;
    }

    public long buyCar(String brand, String model, String plate) {

        Transaction transaction = session.beginTransaction();
        Car carToBuy = findCar(brand, model, plate);
        if (carToBuy != null) {
            session.delete(carToBuy);
            transaction.commit();
            session.close();
            return carToBuy.getPrice();
        } else return 0;

    }
}
