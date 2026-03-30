package Cars;

public abstract class Car {
    private final String brand;
    private final String model;
    private final int year;
    private String color;
    private final boolean isAutomatic;
    private final double engineVolume;

    public Car(String brand, String model, int year, String color, boolean isAutomatic, double engineVolume) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isAutomatic = isAutomatic;
        this.engineVolume = engineVolume;
    }

    public String getInfo() {
        return brand + " " + model + ", " + year + ", " + color +
                ", " + (isAutomatic ? "Автомат" : "Механика") +
                ", " + engineVolume + "L";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }
}
