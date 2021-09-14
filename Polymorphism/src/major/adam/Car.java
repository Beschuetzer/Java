package major.adam;

public class Car {
    private boolean engine;
    private  int cylinders;
    private  String name;
    private int wheels;

    public Car(int cylinders, String name) {
        this.cylinders = cylinders;
        this.name = name;
        this.wheels = 4;
        this.engine = true;
    }

    public int getCylinders() {
        return cylinders;
    }

    public String getName() {
        return name;
    }

    public String startEngine() {
        return "Car => starting engine...";
    }

    public String accelerate() {
        return "Car => accelerating...";
    }

    public String brake() {
        return "Car => braking...";
    }
}
