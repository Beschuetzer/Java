package major.adam;

public class Moon extends HeavenlyBody {
    private Planet hostPlanet;

    public Moon(String name, double orbitalPeriod) {
        this(name, orbitalPeriod, null);
    }

    public Moon(String name, double orbitalPeriod, Planet planet) {
        super(name, orbitalPeriod, CelestialBodyType.MOON);
        this.hostPlanet = planet;
    }
}
