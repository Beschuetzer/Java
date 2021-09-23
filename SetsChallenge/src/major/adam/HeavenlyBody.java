package major.adam;

import java.util.HashSet;
import java.util.Objects;

public class HeavenlyBody {
    private String name;
    private double orbitalPeriod;
    private HashSet<HeavenlyBody> satellites;
    private CelestialBodyType bodyType;

    public HeavenlyBody(String name, double orbitalPeriod) {
        this(name, orbitalPeriod, CelestialBodyType.UNKNOWN);
    }

    public HeavenlyBody(String name, double orbitalPeriod, CelestialBodyType bodyType) {
        this.name = name;
        this.bodyType = bodyType;
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = new HashSet<>();
    }

    public String getBodyType() {
        return bodyType.getName();
    }

    public String getName() {
        return name;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public boolean addSatellite(HeavenlyBody toAdd) {
        if (toAdd != null) {
//            HeavenlyBody temp = new HeavenlyBody();
//
//            this.satellites.add(new HeavenlyBody(toAdd.getName(), toAdd.orbitalPeriod));
            return  true;
        }
        return false;
    }

    public HashSet<HeavenlyBody> getSatellites() {
        return new HashSet<>(satellites);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        HeavenlyBody that = (HeavenlyBody) obj;

        if (that.name.equals(((HeavenlyBody) obj).getName())) {
            if (obj.getClass() == that.getClass()) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, orbitalPeriod, satellites);
    }

}
