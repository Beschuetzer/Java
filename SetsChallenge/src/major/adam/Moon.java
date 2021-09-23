package major.adam;

public class Moon extends HeavenlyBody {
    private Planet hostPlanet;

    public Moon(String name, double orbitalPeriod, Planet planet) {
        this(name, orbitalPeriod, planet, CelestialBodyType.UNKNOWN);
    }

    public Moon(String name, double orbitalPeriod, Planet planet, CelestialBodyType bodyType) {
        super(name, orbitalPeriod, bodyType);
        this.hostPlanet = planet;
    }
}
