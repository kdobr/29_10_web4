package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean addCar(String brand, String model, String plate, Long price) {
        if (getCarsByBrand(brand).size() > 10) {
            return false;
        }
        Car car = new Car(brand, model, plate, price);
        long addedId = new CarDao(sessionFactory.openSession()).addCar(car);
        if (addedId==0) return false;
        return true;
    }

    public List<Car> getCarsByBrand(String brand) {
        return new CarDao(sessionFactory.openSession()).getCarsByBrand(brand);
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public long buyCar(String brand, String model, String plate) {
        return new CarDao(sessionFactory.openSession()).buyCar(brand, model, plate);
    }
}
