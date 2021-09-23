package major.adam;

public class Star extends HeavenlyBody {
    public Star(String name, double orbitalPeriod) {
        super(name, orbitalPeriod);
    }

    public Star(String name, double orbitalPeriod, CelestialBodyType bodyType) {
        super(name, orbitalPeriod, bodyType);
    }
}
