package major.adam;

import java.util.HashSet;

public class Planet extends HeavenlyBody{
    private Star hostStar;
    private HashSet<Moon> satellites;

    public Planet(String name, double orbitalPeriod, Star hostStar) {
        this(name, orbitalPeriod, hostStar, CelestialBodyType.UNKNOWN);
    }

    public Planet(String name, double orbitalPeriod, Star hostStar, CelestialBodyType bodyType) {
        super(name, orbitalPeriod, bodyType);
        this.hostStar = hostStar;
        this.satellites = new HashSet<>();
    }
}
