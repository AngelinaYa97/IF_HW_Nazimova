import Cars.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();

        cars.add(new Kia("Cerato", 2015, "red", true, 2.0));
        cars.add(new Toyota("Corolla", 2004, "black", false, 1.6));
        cars.add(new Porshe("911", 2013, "green", false, 1.3));
        cars.add(new BMW("X5", 2018, "black", true, 3.0));
        cars.add(new Audi("A4", 2002, "green", true, 2.0));
        cars.add(new Mitsubishi("Outlander", 2015, "white", true, 2.4));
        cars.add(new BMW("M3", 2020, "green", true, 3.0));
        cars.add(new Lada("Vesta", 2007, "gray", false, 1.6));
        cars.add(new Honda("Accord", 2004, "green", true, 2.0));
        cars.add(new Porshe("Panamera", 2015, "yellow", false, 1.6));

        checkYear(cars);
        changeColor(cars);
        showAutomatic(cars);
    }

    public static void checkYear(List<Car> cars) {
        for (Car car : cars) {
            if (car.getYear() > 2006) {
                System.out.println(car.getInfo());
            } else {
                System.out.println("Устаревший авто");
            }
        }
    }

    public static void changeColor(List<Car> cars) {
        System.out.println("===Меняем цвет у машин===");
        for (Car car : cars) {
            if (car.getColor().equalsIgnoreCase("green")) {
                car.setColor("red");
                System.out.println("Изменили цвет у авто: " + car.getInfo());
            }
        }
    }

    public static void showAutomatic(List<Car> cars) {
        System.out.println("===Выводим машины на автомате===");
        for (Car car : cars) {
            if (car.getInfo().contains("Автомат")) {
                System.out.println(car.getInfo());
            }
        }
    }
}

