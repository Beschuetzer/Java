package major.adam;

import java.util.HashSet;

public class Planet extends HeavenlyBody {
    private final Star hostStar;
    private final HashSet<Moon> satellites;

    public Planet(String name, double orbitalPeriod) {
        this(name, orbitalPeriod, null);
    }

    public Planet(String name, double orbitalPeriod, Star hostStar) {
        super(name, orbitalPeriod, CelestialBodyType.PLANET);
        this.hostStar = hostStar;
        this.satellites = new HashSet<>();
    }

    @Override
    public boolean addSatellite(HeavenlyBody toAdd) {
        if (toAdd.getKey().getBodyType() == CelestialBodyType.MOON) {
           super.addSatellite(toAdd);
           return true;
        }
        return  false;
    }
}
